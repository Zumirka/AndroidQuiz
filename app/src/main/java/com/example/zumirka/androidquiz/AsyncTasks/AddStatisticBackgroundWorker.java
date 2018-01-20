package com.example.zumirka.androidquiz.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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

public class AddStatisticBackgroundWorker extends AsyncTask<Void, Void, Void> {

    Context context;
    String userName;
    String idCategory;
    String difficulty;
    String time;
    String correctAnswear;
    String register_url = "http://quizinz.herokuapp.com/addStatistic.php";

    public AddStatisticBackgroundWorker(Context ctx, String userName, String idCategory, String difficulty, String time, String correctAnswear) {
        context = ctx;
        this.userName = userName;
        this.idCategory = idCategory;
        this.difficulty = difficulty;
        this.time = time;
        this.correctAnswear = correctAnswear;
    }

    @Override
    protected Void doInBackground(Void... params) {


        try {


            HttpURLConnection httpURLConnection = getHttpURLConnection();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = parseQuery();
            sendQuery(outputStream, bufferWriter, post_data);
            reciveData(httpURLConnection);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @NonNull
    private void reciveData(HttpURLConnection httpURLConnection) throws IOException {
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
                + URLEncoder.encode("CategoryId", "UTF-8") + "=" + URLEncoder.encode(idCategory, "UTF-8") + "&"
                + URLEncoder.encode("Difficulty", "UTF-8") + "=" + URLEncoder.encode(difficulty, "UTF-8") + "&"
                + URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&"
                + URLEncoder.encode("Points", "UTF-8") + "=" + URLEncoder.encode(correctAnswear, "UTF-8");
    }

    @NonNull
    private HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(register_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Void v) {

    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
