package com.example.zumirka.androidquiz;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.getStatisticBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Statistic;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


public class StatisticActivity extends AppCompatActivity implements
        OnChartGestureListener,
        OnChartValueSelectedListener {


    String userName = "", time1, time2;
    ArrayList<Statistic> statisticsList = new ArrayList<>();
    TableLayout l;
    ImageButton fromDate, toDate;
    Calendar cal;
    int day, month, year;
    EditText fromDateTxt, toDateTxt;
    TableRow tr, trl;
    ArrayList<Entry> entries;
    ArrayList<String> pieEntryLabels;
    int correctAnswear = 0, pointsCount = 0;
    LineChart lineChart;
    TextView emptyTableTxt;
    int buttonId;

    public DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            if (buttonId == fromDate.getId()) {
                fromDateTxt.setText(String.format("%02d", selectedDay) + "-" + String.format("%02d", selectedMonth + 1) + "-" + String.format("%04d", selectedYear));
                time1 = String.format("%04d", selectedYear) + "-" + String.format("%02d", selectedMonth + 1) + "-" + String.format("%02d", selectedDay);
            }
            if (buttonId == toDate.getId()) {
                toDateTxt.setText(String.format("%02d", selectedDay) + "-" + String.format("%02d", selectedMonth + 1) + "-" + String.format("%04d", selectedYear));
                time2 = String.format("%04d", selectedYear) + "-" + String.format("%02d", selectedMonth + 1) + "-" + String.format("%02d", selectedDay);
                TakeStatistic();
            }

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        SharedPreferences resSettings = getSharedPreferences("BYLECO", MODE_PRIVATE);
        initGUI(resSettings);
        setData();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initGUI(SharedPreferences resSettings) {
        userName = resSettings.getString("USER_NAME", "empty");
        l = (TableLayout) findViewById(R.id.table);
        fromDate = (ImageButton) findViewById(R.id.fromDate);
        toDate = (ImageButton) findViewById(R.id.toDate);
        fromDateTxt = findViewById(R.id.fromDateTxt);
        toDateTxt = findViewById(R.id.toDateTxt);
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        lineChart = (LineChart) findViewById(R.id.linechart);
        lineChart.setOnChartGestureListener(this);
        lineChart.setOnChartValueSelectedListener(this);
        Legend l = lineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        emptyTableTxt = findViewById(R.id.EmptyTableTxt);
    }

    public void onClickV(View v) {
        buttonId = v.getId();
        showDialog(0);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {

        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    // TODO type
    private void TakeStatistic() {

        String type = "getStats";
        getStatisticBackgroundWorker getStatisticBackgroundWorker = new getStatisticBackgroundWorker(this);
        getStatisticBackgroundWorker.execute(type, userName, time1, time2);
        l.removeAllViewsInLayout();
    }

    public void setCategory(ArrayList<Statistic> stats) {
        this.statisticsList = stats;
        if (stats.size() != 0) {
            emptyTableTxt.setText("");
            initTableHeader();
            l.addView(trl);

            for (int i = 0; i < statisticsList.size(); i++) {
                insertData(i);
                l.addView(tr);
                tr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entries = new ArrayList<>();
                        pieEntryLabels = new ArrayList<String>();
                        TableRow clickRow = (TableRow) view;
                        TextView firstTextView = (TextView) clickRow.getChildAt(1);
                        TextView secondTextView = (TextView) clickRow.getChildAt(3);
                        String firstText = firstTextView.getText().toString();
                        if (firstText == "Łatwy") {
                            pointsCount = 10;
                        }
                        if (firstText == "Średni") {
                            pointsCount = 20;
                        }
                        if (firstText == "Trudny") {
                            pointsCount = 30;
                        }
                        correctAnswear = Integer.parseInt(secondTextView.getText().toString());


                    }
                });

            }
        } else {
            l.removeAllViews();
            emptyTableTxt.setText("Brak statystyk");
            emptyTableTxt.setGravity(Gravity.CENTER);

        }

    }

    private void insertData(int i) {
        tr = new TableRow(this);
        TextView txt = new TextView(this);
        txt.setText(statisticsList.get(i).getCategory().getName());
        txt.setGravity(Gravity.CENTER);
        tr.addView(txt);
        txt = new TextView(this);
        txt.setText(statisticsList.get(i).getDifficulty());
        txt.setGravity(Gravity.CENTER);
        tr.addView(txt);
        txt = new TextView(this);
        txt.setText(statisticsList.get(i).getTime());
        txt.setGravity(Gravity.CENTER);
        tr.addView(txt);
        txt = new TextView(this);
        txt.setText(statisticsList.get(i).getPoints());
        txt.setGravity(Gravity.CENTER);
        tr.addView(txt);
        txt = new TextView(this);
        txt.setText(statisticsList.get(i).getDate());
        txt.setGravity(Gravity.CENTER);
        tr.addView(txt);
        tr.setBackgroundColor(Color.LTGRAY);
        tr.setPadding(2, 4, 3, 3);
    }

    private void initTableHeader() {
        trl = new TableRow(this);
        TextView labels = new TextView(this);
        labels.setText("Kategoria");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Poziom trudności");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Czas");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Punkty");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Data");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        trl.setBackgroundColor(Color.GRAY);
        trl.setPadding(1, 4, 1, 3);
    }

    //set x value on chart
    private ArrayList<String> setXAxisValues() {
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");

        return xVals;
    }

    //set y value on chart
    private ArrayList<Entry> setYAxisValues() {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));

        return yVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        lineChart.setData(data);
        // lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);

    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
