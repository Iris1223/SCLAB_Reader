package com.example.stud.a7777;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout LineChart;
    private View chart;
    int index=0;
    double t=0;
    int k=0;
//    List<double[]> x = new ArrayList<double[]>(); // 點的x坐標
//    List<double[]> y = new ArrayList<double[]>(); // 點的y坐標
    double x[];
    double y[];
    XYMultipleSeriesDataset dataset;
    XYMultipleSeriesRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        LineChart = (FrameLayout) findViewById(R.id.LineChart);
        String[] titles = new String[] { "折線1" }; // 定義折線的名稱
        x=new double[600];
        y=new double[600];
        x[k]=0;
        y[k]=0;

        // 數值X,Y坐標值輸入
//        x.add(new double[] { 0});
//        y.add(new double[] { 0});
        dataset = buildDatset(titles, x, y); // 儲存座標值

        int[] colors = new int[] { Color.BLUE };// 折線的顏色
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE}; // 折線點的形狀
        renderer = buildRenderer(colors, styles, true);

        setChartSettings(renderer, "DI Water", "Time(s)", "Capacitance(pF)", 0, 600, 0, 10, Color.BLACK);// 定義折線圖
        chart = ChartFactory.getLineChartView(MainActivity.this, dataset, renderer);
        LineChart.removeAllViews();
        LineChart.addView(chart);
        Log.i("Create Android", "Test0");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(index){
                    case 0:
                        try
                        {
                            //取得SD卡儲存路徑
                            File mSDFile = Environment.getExternalStorageDirectory();
                            //讀取文件檔路徑
                            FileReader mFileReader = new FileReader(mSDFile.getParent() + "/" + mSDFile.getName() + "/MyAndroid/Pubby.txt");

                            BufferedReader mBufferedReader = new BufferedReader(mFileReader);
                            String mReadText = "";
                            String mTextLine = mBufferedReader.readLine();

                            //一行一行取出文字字串裝入String裡，直到沒有下一行文字停止跳出
                            while (mTextLine!=null)
                            {
                                mReadText += mTextLine+"\n";
                                mTextLine = mBufferedReader.readLine();
                            }
                            Snackbar.make(view, "Reader Connecting", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            //@Todo: Connect to the reader
                            Snackbar.make(view, "Reader Connected", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                        catch(Exception e)
                        {
                        }
                        index=1;

                        break;
                    case 1:
                        Snackbar.make(view, "start", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        //倒數計時器(計時1分鐘)
                        new CountDownTimer(100000,1000){

                        @Override
                        public void onFinish() {

                        }

                        @Override
                        public void onTick(long millisUntilFinished) {
                            t=100-(int)millisUntilFinished/1000;
                        }

                        }.start();
                        Thread ttt = new MyThread(); // 產生Thread物件
                        ttt.start();

                        index=0;
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, com.example.stud.a7777.SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
                                    String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor) {
        renderer.setChartTitle(title); // 折線圖名稱
        renderer.setChartTitleTextSize(35); // 折線圖名稱字形大小
        renderer.setXTitle(xTitle); // X軸名稱
        renderer.setYTitle(yTitle); // Y軸名稱
        renderer.setAxisTitleTextSize(25);
        renderer.setXAxisMin(xMin); // X軸顯示最小值
        renderer.setXAxisMax(xMax); // X軸顯示最大值
        renderer.setXLabelsColor(Color.BLACK); // X軸線顏色
        renderer.setYAxisMin(yMin); // Y軸顯示最小值
        renderer.setYAxisMax(yMax); // Y軸顯示最大值
        renderer.setLabelsTextSize(20);//设置刻度显示文字的大小(XY轴都会被设置)
        renderer.setAxesColor(axesColor); // 設定坐標軸顏色
        renderer.setYLabelsColor(0, Color.BLACK); // Y軸線顏色
        renderer.setLabelsColor(Color.BLACK); // 設定標籤顏色
        renderer.setMarginsColor(Color.WHITE); // 設定背景顏色
        renderer.setShowGrid(true); // 設定格線

    }

    // 定義折線圖的格式
    private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles, boolean fill) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setFillPoints(fill);
            renderer.addSeriesRenderer(r); //將座標變成線加入圖中顯示
        }
        return renderer;
    }

    // 資料處理
//    private XYMultipleSeriesDataset buildDatset(String[] titles, List<double[]> xValues,
//                                                List<double[]> yValues) {
    private XYMultipleSeriesDataset buildDatset(String[] titles,double[] xValues, double[] yValues) {
         dataset = new XYMultipleSeriesDataset();

        int length = titles.length; // 折線數量
        for (int i = 0; i < length; i++) {
            // XYseries對象,用於提供繪製的點集合的資料
            XYSeries series = new XYSeries(titles[i]); // 依據每條線的名稱新增
            double[] xV = xValues; // 獲取第i條線的資料
            double[] yV = yValues;
            int seriesLength = xV.length; // 有幾個點
            for (int k = 0; k < seriesLength; k++) // 每條線裡有幾個點
            {
                series.add(xV[k], yV[k]);

            }
            dataset.addSeries(series);
        }
        return dataset;
    }
    class MyThread extends Thread {

        public void run() {
            super.run();
            try {
                do {

                    k=k+1;
                    Log.i("Create Android", "k"+k);
                    //@Todo: Place the get sensor value code here
                    java.util.Random rnd = new java.util.Random(
                            System.currentTimeMillis()); // 取亂數
                    float mInput = new Float(rnd.nextInt(1000) + 0) / 100; // 取0~10小數兩位亂數值
                    double reading = (double) mInput; // mInput偵測到的接收值，本程式使用亂數模擬。
                    sleep(1000);//如果不加這行第一個值得時間會是0這樣畫圖會出問題，不過學長取值需要時間所以之後可以不用這行~

                    y[k]=reading;
                    x[k]=t;//當下的時間


                    Log.i("Create Android", "x[]"+t);
                    Log.i("Create Android", "y[]"+reading);
                    String[] titles = new String[] { "折線1" }; // 定義折線的名稱
                    dataset = buildDatset(titles, x, y); // 儲存座標值
                    addPoint(); // 顯示線
                    sleep(5000); // 暫停1000ms
                } while (MainActivity.MyThread.interrupted() == false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//     顯示心跳線
    private void addPoint() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LineChart.removeAllViews();
                chart = ChartFactory.getLineChartView(MainActivity.this, dataset, renderer);
                LineChart.addView(chart);




            }
        });
    }
}
