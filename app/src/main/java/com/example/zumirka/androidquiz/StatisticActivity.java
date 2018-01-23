package com.example.zumirka.androidquiz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.GetStatisticBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Statistic;
import com.example.zumirka.androidquiz.Utilities.ConnectionChecker;
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


public class StatisticActivity extends AppCompatActivity {


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        SharedPreferences resSettings = getSharedPreferences("userName", MODE_PRIVATE);
        initGUI(resSettings);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initGUI(SharedPreferences resSettings) {
        userName = resSettings.getString("USER_NAME", "empty");
        l = (TableLayout) findViewById(R.id.Table);
        fromDate = (ImageButton) findViewById(R.id.FromDate);
        toDate = (ImageButton) findViewById(R.id.ToDate);
        fromDateTxt = findViewById(R.id.FromDateTxt);
        toDateTxt = findViewById(R.id.ToDateTxt);
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        emptyTableTxt = findViewById(R.id.EmptyTableTxt);

    }

    public void onClickV(View v) {
        buttonId = v.getId();
        showDialog(0);
    }


    protected Dialog onCreateDialog(int id) {

        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private void TakeStatistic() {
        if (ConnectionChecker.checkInternetConnection(this)) {
            GetStatisticBackgroundWorker getStatisticBackgroundWorker = new GetStatisticBackgroundWorker(this, userName, time1, time2);
            getStatisticBackgroundWorker.execute();
            l.removeAllViewsInLayout();
        } else {
            showErrorDialog();
        }


    }

    private void showErrorDialog() {
        AlertDialog alert;
        alert = new AlertDialog.Builder(this).create();
        alert.setTitle(this.getString(R.string.status));
        alert.setMessage(this.getString(R.string.internetCommunicat));
        alert.show();
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
        txt.setTextColor(getResources().getColor(R.color.brown));
        tr.addView(txt);
        txt = new TextView(this);
        txt.setText(statisticsList.get(i).getDifficulty());
        txt.setGravity(Gravity.CENTER);
        txt.setTextColor(getResources().getColor(R.color.brown));
        tr.addView(txt);
        txt = new TextView(this);
        txt.setText(statisticsList.get(i).getTime());
        txt.setGravity(Gravity.CENTER);
        txt.setTextColor(getResources().getColor(R.color.brown));
        tr.addView(txt);
        txt = new TextView(this);
        txt.setText(statisticsList.get(i).getPoints());
        txt.setGravity(Gravity.CENTER);
        txt.setTextColor(getResources().getColor(R.color.brown));
        tr.addView(txt);
        txt = new TextView(this);
        txt.setText(statisticsList.get(i).getDate());
        txt.setGravity(Gravity.CENTER);
        txt.setTextColor(getResources().getColor(R.color.brown));
        tr.addView(txt);
        tr.setBackgroundColor(getResources().getColor(R.color.lightbrown));
        tr.setPadding(2, 4, 3, 3);
    }

    private void initTableHeader() {

        trl = new TableRow(this);
        TextView labels = new TextView(this);
        labels.setText("Kategoria");
        labels.setGravity(Gravity.CENTER);
        labels.setTextColor(getResources().getColor(R.color.white));
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Poziom trudności");
        labels.setGravity(Gravity.CENTER);
        labels.setTextColor(getResources().getColor(R.color.white));
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Czas");
        labels.setGravity(Gravity.CENTER);
        labels.setTextColor(getResources().getColor(R.color.white));
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Punkty");
        labels.setGravity(Gravity.CENTER);
        labels.setTextColor(getResources().getColor(R.color.white));
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Data");
        labels.setGravity(Gravity.CENTER);
        labels.setTextColor(getResources().getColor(R.color.white));
        trl.addView(labels);
        trl.setBackgroundColor(getResources().getColor(R.color.brown));
        trl.setPadding(1, 4, 1, 3);

    }


}
