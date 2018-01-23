package com.example.zumirka.androidquiz.AsyncTasks;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.zumirka.androidquiz.Model.Category;
import com.example.zumirka.androidquiz.Model.Statistic;
import com.example.zumirka.androidquiz.StatisticActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class GetStatisticBackgroundWorker extends AsyncTask<Void, Void, Void> {


    ArrayList<Statistic> statsList = new ArrayList<>();
    StatisticActivity statisticActivity;
    String userName;
    String startTime;
    String endTime;
    String register_url = "http://quizinz.herokuapp.com/getStatistic.php";


    public GetStatisticBackgroundWorker(StatisticActivity statsActivity, String userName, String startTime, String endTime) {
        this.statisticActivity = statsActivity;
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    protected Void doInBackground(Void... params) {


        try {

            HttpURLConnection httpURLConnection = getHttpURLConnection();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = parseQuery();
            sendQuery(outputStream, bufferWriter, post_data);
            String result = reciveData(httpURLConnection);
            parseStatistic(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    private Void parseStatistic(String result)  {

        try {
            JSONArray jsArray = new JSONArray(result);
            JSONObject jsObject = null;

            for (int i = 0; i < jsArray.length(); i++) {
                Statistic stats = new Statistic();
                jsObject = jsArray.getJSONObject(i);
                stats.setCategory(new Category(jsObject.getString("CategoryName")));
                stats.setDifficulty(jsObject.getString("Difficulty"));
                stats.setTime(jsObject.getString("Time"));
                stats.setPoints(jsObject.getString("Points"));
                stats.setDate(jsObject.getString("Date"));
                statsList.add(stats);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    @NonNull
    private String reciveData(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String result = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return result;
    }

    private void sendQuery(OutputStream outputStream, BufferedWriter bufferWriter, String post_data) throws IOException {
        bufferWriter.write(post_data);
        bufferWriter.flush();
        bufferWriter.close();
        outputStream.close();
    }

    @NonNull
    private String parseQuery() throws UnsupportedEncodingException {
        return URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&"
                + URLEncoder.encode("time1", "UTF-8") + "=" + URLEncoder.encode(startTime, "UTF-8") + "&"
                + URLEncoder.encode("time2", "UTF-8") + "=" + URLEncoder.encode(endTime, "UTF-8");
    }

    @NonNull
    private HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(register_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Void v) {
        statisticActivity.setCategory(statsList);
    }

}

