package com.example.stud.a7777;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by stud on 2016/10/18.
 */

public class SettingActivity extends Activity {
    private Spinner spinner_filter;
    private Spinner spinner_window;
    private Spinner spinner_clk;
    private Spinner spinner_cref;
    private Spinner spinner_csub;
    private Spinner spinner_csen;
    private Spinner spinner_vref;
    private Spinner spinner_vina;
    private Spinner spinner_vinb;
    private Spinner spinner_mode_r;
    private Spinner spinner_cint;
    private Spinner spinner_isen;
    TextView Filter_txv;
    TextView Window_txv;
    TextView CLK_txv;
    TextView CREF_txv;
    TextView CSUB_txv;
    TextView CSEN_txv;
    TextView VREF_txv;
    TextView VINA_txv;
    TextView VINB_txv;
    TextView MODE_R_txv;
    TextView CINT_txv;
    TextView ISEN_txv;
    TextView command_txv;
    String filter;
    String window;
    String clk;
    String cref;
    String csub;
    String vref;
    String vina;
    String vinb;
    String mode_r;
    String cint;
    String csen;
    String isen;
    String Gen2_header="1110000010101101";
    String reg_dum="00";
    String eva_vref="0";
    String evb_vref="0";
    String ilp="0";
    String command;
    private String[] lunch_filter;
    private String[] lunch_window;
    private String[] lunch_clk;
    private String[] lunch_cref_csen;
    private String[] lunch_csub;
    private String[] lunch_vref_vina_vinb;
    private String[] lunch_mode_r;
    private String[] lunch_cint;
    private String[] lunch_isen;

    private ArrayAdapter<String> lunchList__filter;
    private ArrayAdapter<String> lunchList__window;
    private ArrayAdapter<String> lunchList__clk;
    private ArrayAdapter<String> lunchList__cref;
    private ArrayAdapter<String> lunchList__csen;
    private ArrayAdapter<String> lunchList__vref;
    private ArrayAdapter<String> lunchList__vina;
    private ArrayAdapter<String> lunchList__vinb;
    private ArrayAdapter<String> lunchList__mode_r;
    private ArrayAdapter<String> lunchList__csub;
    private ArrayAdapter<String> lunchList__cint;
    private ArrayAdapter<String> lunchList__isen;

    // 下拉单默认选中的位置
    private final static int DEFAULT_POSITION = 0;
    // 轻量存储的文件名
    private final static String PREFERENCES_FILE = "setting";
    // 下拉单位置在存储文件中的键名
    private final static String POSITION_KEY_filter = "position_filter";
    private final static String POSITION_KEY_window = "position_window";
    private final static String POSITION_KEY_clk = "position_clk";
    private final static String POSITION_KEY_cref = "position_cref";
    private final static String POSITION_KEY_csen = "position_csen";
    private final static String POSITION_KEY_vref = "position_vref";
    private final static String POSITION_KEY_vina = "position_vina";
    private final static String POSITION_KEY_vinb = "position_vinb";
    private final static String POSITION_KEY_mode_r = "position_mode_r";
    private final static String POSITION_KEY_csub = "position_csub";
    private final static String POSITION_KEY_cint = "position_cint";
    private final static String POSITION_KEY_isen = "position_isen";

    private final static String SELECT_TEXT_KEY_filter = "selectText_filter";
    private final static String SELECT_TEXT_KEY_window = "selectText_window";
    private final static String SELECT_TEXT_KEY_clk = "selectText_clk";
    private final static String SELECT_TEXT_KEY_cref = "selectText_cref";
    private final static String SELECT_TEXT_KEY_csen = "selectText_csen";
    private final static String SELECT_TEXT_KEY_vref = "selectText_vref";
    private final static String SELECT_TEXT_KEY_vina = "selectText_vina";
    private final static String SELECT_TEXT_KEY_vinb = "selectText_vinb";
    private final static String SELECT_TEXT_KEY_mode_r = "selectText_mode_r";
    private final static String SELECT_TEXT_KEY_csub = "selectText_csub";
    private final static String SELECT_TEXT_KEY_cint = "selectText_cint";
    private final static String SELECT_TEXT_KEY_isen = "selectText_isen";
    private final static String COMMAND = "command";
    // 下拉单选中的位置
    private int position_filter;
    private int position_window;
    private int position_clk;
    private int position_cref;
    private int position_csen;
    private int position_vref;
    private int position_vina;
    private int position_vinb;
    private int position_mode_r;
    private int position_csub;
    private int position_cint;
    private int position_isen;
    // 下拉单选中的文本
    private String selectText_filter = null;
    private String selectText_window = null;
    private String selectText_clk = null;
    private String selectText_cref = null;
    private String selectText_csen = null;
    private String selectText_vref = null;
    private String selectText_vina = null;
    private String selectText_vinb = null;
    private String selectText_mode_r = null;
    private String selectText_csub = null;
    private String selectText_cint = null;
    private String selectText_isen = null;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        Filter_txv=(TextView)findViewById(R.id.Filter_txv);
        Window_txv=(TextView)findViewById(R.id.Window_txv);
        CLK_txv=(TextView)findViewById(R.id.CLK_txv);
        CREF_txv=(TextView)findViewById(R.id.CREF_txv);
        CSUB_txv=(TextView)findViewById(R.id.CSUB_txv);
        CSEN_txv=(TextView)findViewById(R.id.CSEN_txv);
        VREF_txv=(TextView)findViewById(R.id.VREF_txv);
        VINA_txv=(TextView)findViewById(R.id.VINA_txv);
        VINB_txv=(TextView)findViewById(R.id.VINB_txv);
        MODE_R_txv=(TextView)findViewById(R.id.MODE_R_txv);
        CINT_txv=(TextView)findViewById(R.id.CINT_txv);
        ISEN_txv=(TextView)findViewById(R.id.ISEN_txv);
        command_txv=(TextView)findViewById(R.id.command_txv);

        spinner_filter = (Spinner)findViewById(R.id.Filter_spinner);
        spinner_window = (Spinner)findViewById(R.id.Windoe_spinner);
        spinner_clk = (Spinner)findViewById(R.id.CLK_spinner);
        spinner_cref = (Spinner)findViewById(R.id.CREF＿spinner);
        spinner_csen = (Spinner)findViewById(R.id.CSEN＿spinner);
        spinner_vref = (Spinner)findViewById(R.id.VREF＿spinner);
        spinner_vina = (Spinner)findViewById(R.id.VINA＿spinner);
        spinner_vinb = (Spinner)findViewById(R.id.VINB＿spinner);
        spinner_mode_r = (Spinner)findViewById(R.id.MODE_R＿spinner);
        spinner_cint = (Spinner)findViewById(R.id.CINT＿spinner);
        spinner_isen = (Spinner)findViewById(R.id.ISEN＿spinner);
        spinner_csub = (Spinner)findViewById(R.id.CSUB_spinner);
        Resources res = getResources();
        lunch_filter = res.getStringArray(R.array.filter);
        lunch_window = res.getStringArray(R.array.window);
        lunch_clk = res.getStringArray(R.array.clk);
        lunch_cref_csen = res.getStringArray(R.array.cref_csen);
        lunch_csub = res.getStringArray(R.array.csub);
        lunch_vref_vina_vinb = res.getStringArray(R.array.vref_vina_vinb);
        lunch_mode_r = res.getStringArray(R.array.mode_r);
        lunch_cint = res.getStringArray(R.array.cint);
        lunch_isen = res.getStringArray(R.array.isen_sel);

        lunchList__filter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_filter);
        lunchList__window = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_window);
        lunchList__clk = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_clk);
        lunchList__cref = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_cref_csen);
        lunchList__csen = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_cref_csen);
        lunchList__vref = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_vref_vina_vinb);
        lunchList__vina = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_vref_vina_vinb);
        lunchList__vinb = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_vref_vina_vinb);
        lunchList__mode_r = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_mode_r);
        lunchList__csub = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_csub);
        lunchList__cint = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_cint);
        lunchList__isen = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lunch_isen);
        spinner_filter.setAdapter(lunchList__filter);
        spinner_window.setAdapter(lunchList__window);
        spinner_clk.setAdapter(lunchList__clk);
        spinner_cref.setAdapter(lunchList__cref);
        spinner_csen.setAdapter(lunchList__csen);
        spinner_vref.setAdapter(lunchList__vref);
        spinner_vina.setAdapter(lunchList__vina);
        spinner_vinb.setAdapter(lunchList__vinb);
        spinner_mode_r.setAdapter(lunchList__mode_r);
        spinner_csub.setAdapter(lunchList__csub);
        spinner_cint.setAdapter(lunchList__cint);
        spinner_isen.setAdapter(lunchList__isen);

        // 添加spinner item被选择事件
        spinner_cref.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new CREFItemSelectedListener());
        spinner_csen.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new CSENItemSelectedListener());
        spinner_vref.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new VREFItemSelectedListener());
        spinner_vina.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new VINAItemSelectedListener());
        spinner_vinb.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new VINBItemSelectedListener());
        spinner_mode_r.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new MODERItemSelectedListener());
        spinner_csub.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new CSUBItemSelectedListener());
        spinner_cint.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new CINTItemSelectedListener());
        spinner_isen.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new ISENItemSelectedListener());
        spinner_filter.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new FilterItemSelectedListener());
        spinner_window.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new WindowItemSelectedListener());
        spinner_clk.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) new CLKItemSelectedListener());

        // 读取数据
        readPrefsState(this);

        // 设置下拉单选中的位置
        spinner_filter.setSelection(this.position_filter);
        spinner_window.setSelection(this.position_window);
        spinner_clk.setSelection(this.position_clk);
        spinner_cref.setSelection(this.position_cref);
        spinner_csen.setSelection(this.position_csen);
        spinner_vref.setSelection(this.position_vref);
        spinner_vina.setSelection(this.position_vina);
        spinner_vinb.setSelection(this.position_vinb);
        spinner_csub.setSelection(this.position_csub);
        spinner_cint.setSelection(this.position_cint);
        spinner_isen.setSelection(this.position_isen);
        spinner_mode_r.setSelection(this.position_mode_r);




    }
    private class CREFItemSelectedListener implements AdapterView.OnItemSelectedListener {

        // 重写此方法
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            // 设置当前选择的item位置
            SettingActivity.this.position_cref = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_cref = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    cref = "000";
                    break;
                case 1:
                    cref = "001";
                    break;
                case 2:
                    cref = "010";
                    break;
                case 3:
                    cref = "011";
                    break;
                case 4:
                    cref = "100";
                    break;
                case 5:
                    cref = "101";
                    break;
                case 6:
                    cref = "110";
                    break;
                case 7:
                    cref = "111";
                    break;
                default:
                    cref = "err";
            }
            CREF_txv.setText(cref);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
        private class CSENItemSelectedListener implements AdapterView.OnItemSelectedListener {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SettingActivity.this.position_csen = position;

                // 设置当前选择的item文本
                SettingActivity.this.selectText_csen = parent.getItemAtPosition(position)
                        .toString();

                switch (position) {
                    case 0:
                        csen = "000";
                        break;
                    case 1:
                        csen = "001";
                        break;
                    case 2:
                        csen = "010";
                        break;
                    case 3:
                        csen = "011";
                        break;
                    case 4:
                        csen = "100";
                        break;
                    case 5:
                        csen = "101";
                        break;
                    case 6:
                        csen = "110";
                        break;
                    case 7:
                        csen = "111";
                        break;
                    default:
                        csen = "err";
                }
                CSEN_txv.setText(csen);
                command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
                command_txv.setText(command);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        }
    private class VREFItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_vref = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_vref = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    vref = "0000";
                    break;
                case 1:
                    vref = "0001";
                    break;
                case 2:
                    vref = "0010";
                    break;
                case 3:
                    vref = "0011";
                    break;
                case 4:
                    vref = "0100";
                    break;
                case 5:
                    vref = "0101";
                    break;
                case 6:
                    vref = "0110";
                    break;
                case 7:
                    vref = "0111";
                    break;
                case 8:
                    vref = "1000";
                    break;
                case 9:
                    vref = "1001";
                    break;
                case 10:
                    vref = "1010";
                    break;
                case 11:
                    vref = "1011";
                    break;
                case 12:
                    vref = "1100";
                    break;
                case 13:
                    vref = "1101";
                    break;
                case 14:
                    vref = "1110";
                    break;
                case 15:
                    vref = "1111";
                    break;
                default:
                    vref = "err";
            }
            VREF_txv.setText(vref);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    private class VINAItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_vina = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_vina = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    vina = "0000";
                    break;
                case 1:
                    vina = "0001";
                    break;
                case 2:
                    vina = "0010";
                    break;
                case 3:
                    vina = "0011";
                    break;
                case 4:
                    vina = "0100";
                    break;
                case 5:
                    vina = "0101";
                    break;
                case 6:
                    vina = "0110";
                    break;
                case 7:
                    vina= "0111";
                    break;
                case 8:
                    vina = "1000";
                    break;
                case 9:
                    vina = "1001";
                    break;
                case 10:
                    vina = "1010";
                    break;
                case 11:
                    vina = "1011";
                    break;
                case 12:
                    vina = "1100";
                    break;
                case 13:
                    vina = "1101";
                    break;
                case 14:
                    vina = "1110";
                    break;
                case 15:
                    vina = "1111";
                    break;
                default:
                    vina = "err";
            }
            VINA_txv.setText(vina);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
        private class VINBItemSelectedListener implements AdapterView.OnItemSelectedListener {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SettingActivity.this.position_vinb = position;

                // 设置当前选择的item文本
                SettingActivity.this.selectText_vinb = parent.getItemAtPosition(position)
                        .toString();

                switch (position) {
                    case 0:
                        vinb = "0000";
                        break;
                    case 1:
                        vinb = "0001";
                        break;
                    case 2:
                        vinb = "0010";
                        break;
                    case 3:
                        vinb = "0011";
                        break;
                    case 4:
                        vinb = "0100";
                        break;
                    case 5:
                        vinb = "0101";
                        break;
                    case 6:
                        vinb = "0110";
                        break;
                    case 7:
                        vinb= "0111";
                        break;
                    case 8:
                        vinb = "1000";
                        break;
                    case 9:
                        vinb = "1001";
                        break;
                    case 10:
                        vinb = "1010";
                        break;
                    case 11:
                        vinb = "1011";
                        break;
                    case 12:
                        vinb = "1100";
                        break;
                    case 13:
                        vinb = "1101";
                        break;
                    case 14:
                        vinb= "1110";
                        break;
                    case 15:
                        vinb = "1111";
                        break;
                    default:
                        vinb = "err";
                }
                VINB_txv.setText(vinb);
                command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
                command_txv.setText(command);
            }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    private class MODERItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_mode_r = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_mode_r = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    mode_r = "0";
                    break;
                case 1:
                    mode_r = "1";
                    break;
                default:
                    mode_r = "err";
            }
            MODE_R_txv.setText(mode_r);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    private class FilterItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_filter = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_filter = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    filter = "0";
                    break;
                case 1:
                    filter = "1";
                    break;
                default:
                    filter = "err";
            }
            Filter_txv.setText(filter);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    private class ISENItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_isen = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_isen = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    isen = "00";
                    break;
                case 1:
                    isen  = "01";
                    break;
                case 2:
                    isen = "10";
                    break;
                case 3:
                    isen  = "11";
                    break;
                default:
                    isen  = "err";
            }
            ISEN_txv.setText(isen);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    private class WindowItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_window = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_window = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    window = "0000";
                    break;
                case 1:
                    window  = "0001";
                    break;
                case 2:
                    window = "0010";
                    break;
                case 3:
                    window  = "0011";
                    break;
                case 4:
                    window = "0100";
                    break;
                case 5:
                    window = "0101";
                    break;
                case 6:
                    window = "0110";
                    break;
                case 7:
                    window= "0111";
                    break;
                case 8:
                    window = "1000";
                    break;
                default:
                    window  = "err";
            }
            ISEN_txv.setText(isen);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    private class CLKItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_clk = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_clk = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    clk = "00";
                    break;
                case 1:
                    clk  = "01";
                    break;
                case 2:
                    clk = "10";
                    break;
                case 3:
                    clk  = "11";
                    break;
                default:
                    clk  = "err";
            }
            ISEN_txv.setText(isen);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    private class CINTItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_cint = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_cint = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    cint = "000";
                    break;
                case 1:
                    cint = "001";
                    break;
                case 2:
                    cint = "010";
                    break;
                case 3:
                    cint = "011";
                    break;
                case 4:
                    cint = "100";
                    break;
                case 5:
                    cint = "101";
                    break;
                case 6:
                    cint = "110";
                    break;
                case 7:
                    cint = "111";
                    break;
                default:
                    cint = "err";
            }
            CINT_txv.setText(cint);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    private class CSUBItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            SettingActivity.this.position_csub = position;

            // 设置当前选择的item文本
            SettingActivity.this.selectText_csub = parent.getItemAtPosition(position)
                    .toString();

            switch (position) {
                case 0:
                    csub = "000";
                    break;
                case 1:
                    csub = "001";
                    break;
                case 2:
                    csub = "010";
                    break;
                case 3:
                    csub = "011";
                    break;
                case 4:
                    csub = "100";
                    break;
                case 5:
                    csub = "101";
                    break;
                case 6:
                    csub = "110";
                    break;
                case 7:
                    csub = "111";
                    break;
                default:
                    csub = "err";
            }
            CSUB_txv.setText(csub);
            command=Gen2_header+filter+window+clk+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
            command_txv.setText(command);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    public boolean readPrefsState(Context c) {

        /*
         * 当前Activity方法获得一个SharedPreferences实例 第一个参数是文件名 第二个参数是设置文件的访问权限，有3个选择
         * 1、MODE_PRIVATE（只有调用的程序可以访问） 2、MODE_WORLD_READABLE（别的程序有读的权限）
         * 3、MODE_WORLD_WRITEABLE（别的程序有写的权限） 但是在API17里2，3被弃用了，it is dangerous
         */
        SharedPreferences spref = c.getSharedPreferences(PREFERENCES_FILE,
                MODE_PRIVATE);
        // 读取到POSITION_KEY和SELECT_TEXT_KEY的值，如果不存在此key，则默认为DEFAULT_POSITION
        this.position_cref = spref.getInt(POSITION_KEY_cref, DEFAULT_POSITION);
        this.position_csen = spref.getInt(POSITION_KEY_csen, DEFAULT_POSITION);
        this.position_vref = spref.getInt(POSITION_KEY_vref, DEFAULT_POSITION);
        this.position_vina = spref.getInt(POSITION_KEY_vina, DEFAULT_POSITION);
        this.position_vinb = spref.getInt(POSITION_KEY_vinb, DEFAULT_POSITION);
        this.position_mode_r = spref.getInt(POSITION_KEY_mode_r, DEFAULT_POSITION);
        this.position_csub = spref.getInt(POSITION_KEY_csub, DEFAULT_POSITION);
        this.position_cint = spref.getInt(POSITION_KEY_cint, DEFAULT_POSITION);
        this.position_isen = spref.getInt(POSITION_KEY_isen, DEFAULT_POSITION);
        this.selectText_cref = spref.getString(SELECT_TEXT_KEY_cref, "");
        this.selectText_csen = spref.getString(SELECT_TEXT_KEY_csen, "");
        this.selectText_vref = spref.getString(SELECT_TEXT_KEY_vref, "");
        this.selectText_vina = spref.getString(SELECT_TEXT_KEY_vina, "");
        this.selectText_vinb = spref.getString(SELECT_TEXT_KEY_vinb, "");
        this.selectText_mode_r = spref.getString(SELECT_TEXT_KEY_mode_r, "");
        this.selectText_csub = spref.getString(SELECT_TEXT_KEY_csub, "");
        this.selectText_cint = spref.getString(SELECT_TEXT_KEY_cint, "");
        this.selectText_isen = spref.getString(SELECT_TEXT_KEY_isen, "");
        this.command = spref.getString(COMMAND, "");
        // 包含此key则返回true，反之false
        return (spref.contains(POSITION_KEY_cref));


    }
    public boolean writePrefsState(Context c) {

        /*
         * 当前Activity方法获得一个SharedPreferences实例 第一个参数是文件名 第二个参数是设置文件的访问权限，有3个选择
         * 1、MODE_PRIVATE（只有调用的程序可以访问） 2、MODE_WORLD_READABLE（别的程序有读的权限）
         * 3、MODE_WORLD_WRITEABLE（别的程序有写的权限）
         */
        SharedPreferences spref = c.getSharedPreferences(PREFERENCES_FILE,
                MODE_PRIVATE);

        /*
         * 获取SharedPreferences.Editor对象，SharedPreferences本来只可以读取数据，
         * 如果要编辑就要获取Editor对象
         */
        SharedPreferences.Editor edit = spref.edit();

        // 存储这2个值
        edit.putInt(POSITION_KEY_cref, this.position_cref);
        edit.putInt(POSITION_KEY_csen, this.position_csen);
        edit.putInt(POSITION_KEY_vref, this.position_vref);
        edit.putInt(POSITION_KEY_vina, this.position_vina);
        edit.putInt(POSITION_KEY_vinb, this.position_vinb);
        edit.putInt(POSITION_KEY_mode_r, this.position_mode_r);
        edit.putInt(POSITION_KEY_csub, this.position_csub);
        edit.putInt(POSITION_KEY_cint, this.position_cint);
        edit.putInt(POSITION_KEY_isen, this.position_isen);
        edit.putString(SELECT_TEXT_KEY_cref, this.selectText_cref);
        edit.putString(SELECT_TEXT_KEY_csen, this.selectText_csen);
        edit.putString(SELECT_TEXT_KEY_vref, this.selectText_vref);
        edit.putString(SELECT_TEXT_KEY_vina, this.selectText_vina);
        edit.putString(SELECT_TEXT_KEY_vinb, this.selectText_vinb);
        edit.putString(SELECT_TEXT_KEY_csub, this.selectText_csub);
        edit.putString(SELECT_TEXT_KEY_cint, this.selectText_cint);
        edit.putString(SELECT_TEXT_KEY_mode_r, this.selectText_mode_r);
        edit.putString(SELECT_TEXT_KEY_isen, this.selectText_isen);
        command=Gen2_header+eva_vref+cref+ilp+csub+vref+vina+vinb+evb_vref+cint+mode_r+csen+isen+reg_dum;
        edit.putString(COMMAND, this.command);
        // 使用edit.commit()提交，这样才会真正存储到文件中，true表示成功，false表示失败
        return (edit.commit());
    }
    public void Myfile()
    {
        try
        {
            File mSDFile = null;

            //檢查有沒有SD卡裝置
            if(Environment.getExternalStorageState().equals( Environment.MEDIA_REMOVED))
            {
                Toast.makeText(this , "沒有SD卡!!!" , Toast.LENGTH_SHORT ).show();
                return ;
            }
            else
            {
                //取得SD卡儲存路徑
                mSDFile = Environment.getExternalStorageDirectory();
            }

            //建立文件檔儲存路徑
            File mFile = new File(mSDFile.getParent() + "/" + mSDFile.getName() + "/MyAndroid");

            //若沒有檔案儲存路徑時則建立此檔案路徑
            if(!mFile.exists())
            {
                mFile.mkdirs();
            }

            //取得command文字並儲存寫入至SD卡文件裡
            FileWriter mFileWriter = new FileWriter( mSDFile.getParent() + "/" + mSDFile.getName() + "/MyAndroid/Pubby.txt" );

            mFileWriter.write(command);
            mFileWriter.close();
            Toast.makeText(this, "已儲存command", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
        }
    }
    @Override
    protected void onStop() {

        // 此Activity停止的时候存储数据
        writePrefsState(this);
        Myfile();
        super.onStop();
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Switch on item id

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;

            default:

                return false;
        }

        return true;
    }


}

