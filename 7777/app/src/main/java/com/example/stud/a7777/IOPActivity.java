package com.example.stud.a7777;

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class IOPActivity extends AppCompatActivity {

    private static final boolean DEBUG = false;
    private static final String ACTION_USB_PERMISSION = "com.mti.rfid.minime.USB_PERMISSION"; //Need to check if applicable
    private static final int PID = 49193;
    private static final int VID = 4901;
    final Context context = this;

    private UsbCommunication mUsbCommunication = UsbCommunication.newInstance();
    private UsbManager mManager;
    private MtiCmd mMtiCmd;
    private PendingIntent mPermissionIntent;
    private SharedPreferences mSharedpref;
    Vibrator vibrator = null;

    private boolean deviceConnected = false;
    private boolean bSavedInst = false;
    private boolean startScan = false;

    private boolean playSound = false;

    public static ArrayList<String> tagList = new ArrayList<String>();

    public ArrayList<DataPoint> dataList = new ArrayList<DataPoint>();

    //System constants
    private final int READ_TAG_TIMES = 40;
    private final String TARGET_TAG_ID = "4C4C";

    private final int DATA_SAMPLED_ONCE = 5;
    private final int READ_RETRY = 3;

    //UI Define
    private TextView deviceConnectStatusTextView, readValueTextView, messageTextView;
    private Button runButton;

    //Timer object
    Timer timer;
    TimerTask scanTask;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iop);

        deviceConnectStatusTextView = (TextView) findViewById(R.id.textView_connectstatus);
        readValueTextView = (TextView) findViewById(R.id.textView_readvalue);
        messageTextView = (TextView) findViewById(R.id.textView_display_msg);
        runButton = (Button) findViewById(R.id.button_iop_run);

        mManager = (UsbManager)getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Create an intent which will interrupt the thread
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);					// will intercept by system
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_USB_PERMISSION);
        registerReceiver(usbReceiver, filter);

        mSharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(deviceConnected == false) {
                    Toast.makeText(context, "IOP Reader not connected", Toast.LENGTH_LONG).show();
                    return;
                }

                long[] pattern_i = { 0, 100, 0 };
                vibrator.vibrate(pattern_i, -1);

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        runButton.post(new Runnable() {
//                                public void run() {
//                                    runButton.setEnabled(false);
//                                }
//                            });
//                        messageTextView.post(new Runnable() {
//                            public void run() {
//                                messageTextView.setText("Please hold the phone steady.");
//                                messageTextView.setTextColor(Color.BLUE);
//                            }
//                        });
//                        }
//                    }).start();




                int retryCount = 0;
                int takeCount = 0;
                int oneProgressLength = 100 / DATA_SAMPLED_ONCE;
                boolean dataComplete = true;

                int[] dataArray = new int[DATA_SAMPLED_ONCE];




                while(takeCount < DATA_SAMPLED_ONCE) {


                    if(retryCount < READ_RETRY) {
                        int readValue = getTagValue(TARGET_TAG_ID);
                        sleep(600);

                        if(readValue != -1) {
                            //data is received correctly

                            dataArray[takeCount] = readValue;

                            retryCount = 0;
                            takeCount++;

//                            readValueTextView.setText(readValue + "");
//                            readValueTextView.setTextColor(Color.argb(255,2,136,209));

                            long[] pattern = { 0, 100, 0 };
                            vibrator.vibrate(pattern, -1);


                        }else {
                            //data is not received
                            retryCount++;
//                            messageTextView.setText("IOP Not detect, please make sure the correct posture.");
//                            messageTextView.setTextColor(Color.RED);

                            long[] pattern = { 0, 500, 0};
                            vibrator.vibrate(pattern, -1);

                        }
                    }else {

//                        messageTextView.setText("Click Retry.");
//                        messageTextView.setTextColor(Color.RED);
                        dataComplete = false;
                        takeCount = DATA_SAMPLED_ONCE + 1;

                        //@Todo: retry not enabled

                    }

                }


                if(dataComplete) {

                    int sum = 0;
                    for(int i = 0;i < DATA_SAMPLED_ONCE; i++) {
                        sum += dataArray[i];
                    }

                    int dataAverage = sum / DATA_SAMPLED_ONCE;
                    readValueTextView.setText(dataAverage + ".");
                    readValueTextView.setTextColor(Color.GREEN);

                    if(playSound) {
                        MediaPlayer mp = MediaPlayer.create(context, R.raw.ok);
                        mp.start();
                    }


                    //Show the dialog
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_scan);
                    dialog.setTitle("Data Complete");

                    TextView valueTV = (TextView) dialog.findViewById(R.id.textView_dialog_value);
                    TextView messageTV = (TextView) dialog.findViewById(R.id.textView_dialog_message);
                    Button saveButton = (Button) dialog.findViewById(R.id.button_dialog_save);
                    Button cancelButton = (Button) dialog.findViewById(R.id.button_dialog_cancel);

                    valueTV.setText(dataAverage + "");
                    messageTV.setText("Data Is Retrieved");

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();



                }else {

                    readValueTextView.setText("00.");
                    readValueTextView.setTextColor(Color.argb(255,2,136,209));

                    if(playSound) {
                        MediaPlayer mp = MediaPlayer.create(context, R.raw.fail);
                        mp.start();
                    }


                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_scan);
                    dialog.setTitle("IOP Error");

                    TextView valueTV = (TextView) dialog.findViewById(R.id.textView_dialog_value);
                    TextView messageTV = (TextView) dialog.findViewById(R.id.textView_dialog_message);
                    Button saveButton = (Button) dialog.findViewById(R.id.button_dialog_save);
                    Button cancelButton = (Button) dialog.findViewById(R.id.button_dialog_cancel);

                    valueTV.setText("---");
                    valueTV.setTextColor(Color.RED);
                    messageTV.setText("IOP Data Load Unsuccessful.");
                    messageTV.setTextColor(Color.RED);

                    saveButton.setEnabled(false);
                    cancelButton.setText("Cancel");

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }



//                runButton.setEnabled(true);



            }
        });

        runButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplication(), "Scanning Tags", Toast.LENGTH_SHORT);

                doInventory();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        HashMap<String, UsbDevice> deviceList = mManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while(deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            if (device.getProductId() == PID && device.getVendorId() == VID) {
                if(mManager.hasPermission(device))
                    mManager.requestPermission(device, mPermissionIntent);
                break;
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if(deviceConnected == true) {
            mUsbCommunication.setUsbInterface(null, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(usbReceiver);
    }

    private void setDeviceConnectedState(boolean state) {
        //Change the reader connected status
        if(state) {
            deviceConnectStatusTextView.setText("Reader Connected");
            deviceConnectStatusTextView.setTextColor(Color.BLACK);
            deviceConnected = true;
        } else {
            deviceConnectStatusTextView.setText("Reader Disconnected");
            deviceConnectStatusTextView.setTextColor(Color.RED);
            deviceConnected = false;
        }
    }


    private void setPowerLevel() {
        MtiCmd mMtiCmd = new CMD_AntPortOp.RFID_AntennaPortSetPowerLevel(mUsbCommunication);
        CMD_AntPortOp.RFID_AntennaPortSetPowerLevel finalCmd = (CMD_AntPortOp.RFID_AntennaPortSetPowerLevel) mMtiCmd;

        finalCmd.setCmd((byte)18);
    }

    private void setPowerState() {
        MtiCmd mMtiCmd = new CMD_PwrMgt.RFID_PowerEnterPowerState(mUsbCommunication);
        CMD_PwrMgt.RFID_PowerEnterPowerState finalCmd = (CMD_PwrMgt.RFID_PowerEnterPowerState) mMtiCmd;

        if(mSharedpref.getBoolean("cfg_sleep_mode", false)) {
            finalCmd.setCmd(CMD_PwrMgt.PowerState.Sleep);
            sleep(200);
        }
    }

    private void getReaderSn(boolean bState) {
        MtiCmd mMtiCmd;

        if(bState) {
            byte[] bSN = new byte[16];

            for (int i = -1; i < 16; i++) {
                mMtiCmd = new CMD_FwAccess.RFID_MacReadOemData(mUsbCommunication);
                CMD_FwAccess.RFID_MacReadOemData finalCmd = (CMD_FwAccess.RFID_MacReadOemData) mMtiCmd;
                if(finalCmd.setCmd(i + 0x50))
                    if(i >= 0)
                        bSN[i] = finalCmd.getData();
            }
//            mSharedpref.edit().putString("about_reader_sn_sum", EncodingUtils.getAsciiString(bSN)).commit();
            mSharedpref.edit().putString("about_reader_sn_sum", "dep").commit();

        } else
            mSharedpref.edit().putString("about_reader_sn_sum", "n/a").commit();

    }


    private void sleep(int millisecond) {
        try{
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {}
    }



    // Broadcast receiver for USB Reader Device
    BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(DEBUG) Toast.makeText(context, "Broadcast Receiver", Toast.LENGTH_SHORT).show();

            if(UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {					// will intercept by system
                if(DEBUG) Toast.makeText(context, "USB Attached", Toast.LENGTH_SHORT).show();
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                mUsbCommunication.setUsbInterface(mManager, device);
                setDeviceConnectedState(true);

            } else if(UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                if(DEBUG) Toast.makeText(context, "USB Detached", Toast.LENGTH_SHORT).show();
                mUsbCommunication.setUsbInterface(null, null);
                setDeviceConnectedState(false);
//				getReaderSn(false);

            } else if(ACTION_USB_PERMISSION.equals(action)) { //Turn off on 5/6
                if(DEBUG) Toast.makeText(context, "USB Permission", Toast.LENGTH_SHORT).show();
                Log.d(UsbCommunication.TAG, "permission");
                synchronized(this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if(intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        mUsbCommunication.setUsbInterface(mManager, device);
                        setDeviceConnectedState(true);
                        if(bSavedInst)
                            getReaderSn(true);
                        setPowerLevel();
                        setPowerState();

                    } else {
                        finish();
                    }
                }
            }
        }
    };

    private int getTagValue(String tagId) {

        //Get sensor data, if data received, return value; then return -1
        if(selectTag(tagId) == true) {
            Random rand = new Random();
            return rand.nextInt(100);
        }else {
            return -1;
        }
    }
    // Select a specific Tag
    private boolean selectTag(String tagId) {  //Click on shown tag for details
        int intCount = 0;
        boolean bStatus = false;

        if(deviceConnected) {
            mMtiCmd = new CMD_Iso18k6cTagAccess.RFID_18K6CTagSelect(mUsbCommunication);
            CMD_Iso18k6cTagAccess.RFID_18K6CTagSelect finalCmd = (CMD_Iso18k6cTagAccess.RFID_18K6CTagSelect) mMtiCmd;

            do {
                bStatus = finalCmd.setCmd(finalCmd.byteCmd(tagId.replace(" ", "")));
                intCount++;
            } while (!bStatus && intCount < READ_TAG_TIMES);

            if(!bStatus)
//                Toast.makeText(this, "The Tag is not available, please try again.", Toast.LENGTH_SHORT).show();

            setPowerState();
        } else
            Toast.makeText(this, "The Reader is not connected", Toast.LENGTH_SHORT).show();
        return bStatus;
    }


    public void doInventory() {
        final Handler handler = new Handler();

        final int scantimes = Integer.parseInt((mSharedpref.getString("cfg_inventory_times", "25")));

        if(deviceConnected) {
            final ProgressDialog mProgDlg = ProgressDialog.show(this, "Inventory", "Searching ...", true);

            new Thread() {
                int numTags;
                String tagId;

                ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

                public void run() {
                    tagList.clear();
                    for(int i = 0; i < scantimes; i++) {
                        mMtiCmd = new CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory(mUsbCommunication);
                        CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory finalCmd = (CMD_Iso18k6cTagAccess.RFID_18K6CTagInventory) mMtiCmd;

                        if(finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.StartInventory)) {
                            tagId = finalCmd.getTagId();
                            if(finalCmd.getTagNumber() > 0) {
//                                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                                if(!tagList.contains(tagId))
                                    tagList.add(tagId);
//								finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.GetAllTags);
                            }

                            for(numTags = finalCmd.getTagNumber(); numTags > 1; numTags--) {
                                if(finalCmd.setCmd(CMD_Iso18k6cTagAccess.Action.NextTag)) {
                                    tagId = finalCmd.getTagId();
                                    if(!tagList.contains(tagId)){
                                        tagList.add(tagId);
                                    }
                                }
                            }
                            Collections.sort(tagList);
                            handler.post(updateResult);
                        } else {
                            // Error while processing
                        }
                    }
                    mProgDlg.dismiss();
                    setPowerState();
                }

                final Runnable updateResult = new Runnable() {
                    @Override
                    public void run() {
                        readValueTextView.setText(String.valueOf(tagList.size()));
                    }
                };
            }.start();
        } else
            Toast.makeText(this, "The Reader is not connected", Toast.LENGTH_SHORT).show();
    }


    //Scan tags and report number task
    public void initializeScanTagTask() {

        scanTask = new TimerTask() {
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //Get tag value
                        int readValue = getTagValue(TARGET_TAG_ID);
                        if(readValue != -1) {
                            readValueTextView.setText(String.valueOf(readValue));
                            readValueTextView.setTextColor(Color.argb(255,2,136,209));
                            DataPoint point = new DataPoint(readValue, System.currentTimeMillis());
                            dataList.add(point);
                        }else {
                            readValueTextView.setTextColor(Color.RED);
                        }

                    }
                });
            }
        };
    }





}
