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

public class AddQuestionBackgroundWorker extends AsyncTask<Void, Void, Void> {
    Context context;
    String[] data;
    String idCategory;
    String diff;
    String question;
    String answ1;
    String answ2;
    String answ3;
    String register_url = "http://quizinz.herokuapp.com/addQuestion.php";

    public AddQuestionBackgroundWorker(Context ctx, String idCategory, String diff, String question, String answ1, String answ2, String answ3) {
        context = ctx;
        this.idCategory = idCategory;
        this.diff = diff;
        this.question = question;
        this.answ1 = answ1;
        this.answ2 = answ2;
        this.answ3 = answ3;
    }

    @Override
    protected Void doInBackground(Void... voids) {

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

    //odbierz dane
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
    private HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(register_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    @NonNull
    private String parseQuery() throws UnsupportedEncodingException {
        return URLEncoder.encode("idCategory", "UTF-8") + "=" + URLEncoder.encode(idCategory, "UTF-8") + "&"
                + URLEncoder.encode("diff", "UTF-8") + "=" + URLEncoder.encode(diff, "UTF-8") + "&"
                + URLEncoder.encode("question", "UTF-8") + "=" + URLEncoder.encode(question, "UTF-8") + "&"
                + URLEncoder.encode("answ1", "UTF-8") + "=" + URLEncoder.encode(answ1, "UTF-8") + "&"
                + URLEncoder.encode("answ2", "UTF-8") + "=" + URLEncoder.encode(answ2, "UTF-8") + "&"
                + URLEncoder.encode("answ3", "UTF-8") + "=" + URLEncoder.encode(answ3, "UTF-8");
    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Void v) {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String[] getData() {
        return data;
    }
}


