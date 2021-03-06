package com.example.zumirka.androidquiz.AsyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.zumirka.androidquiz.Model.Answear;
import com.example.zumirka.androidquiz.Model.Question;
import com.example.zumirka.androidquiz.Model.Test;
import com.example.zumirka.androidquiz.R;
import com.example.zumirka.androidquiz.TestActivity;

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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;


public class TestDownloadBackgroundWorker extends AsyncTask<Void, Void, Void> {

    ProgressDialog dialog;
    AlertDialog alert;
    Context ctx;
    String getCategory_url = "http://quizinz.herokuapp.com/getTest.php";
    private Test test;
    private int caterogryId;
    private int difficult;
    private TestActivity TA;

    public TestDownloadBackgroundWorker(int caterogryId, int difficult, TestActivity ta, Context ctx) {
        this.ctx=ctx;
        dialog = new ProgressDialog(ctx);
        alert = new AlertDialog.Builder(ctx).create();
        this.caterogryId = caterogryId;
        this.difficult = difficult;
        this.TA = ta;
        test = new Test();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = parseQuery();
            sendQuery(outputStream, bufferWriter, post_data);
            String result = reciveData(httpURLConnection);
            try {
                parseTest(result);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }catch (UnknownHostException e) {
            alert.setMessage("Brak połączenia z internetem");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseTest(String result) throws JSONException {
        JSONArray jsArray = new JSONArray(result);
        JSONObject jsObject = null;

        for (int i = 0; i < jsArray.length(); i++) {
            jsObject = jsArray.getJSONObject(i);


            if (test.getQuestions().size() == 0 || !test.getLastQuestion().getContent().equals(jsObject.getString("Question"))) {
                Question q = new Question();
                q.setContent(jsObject.getString("Question"));
                q.setDifficult(jsObject.getInt("Difficult"));
                test.getQuestions().add(q);
            }
            Answear a = new Answear();
            a.setContent(jsObject.getString("Answear"));
            if (jsObject.getInt("IsTrue") == 1)
                a.setCorrect(true);
            else a.setCorrect(false);
            test.getLastQuestion().addAnswer(a);
        }
        ShuffleAnswears();
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
        return URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(caterogryId), "UTF-8") + "&"
                + URLEncoder.encode("diff", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(difficult), "UTF-8");
    }

    @NonNull
    private HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(getCategory_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage(ctx.getString(R.string.getData));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void result) {
        TA.questionTaker(test);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }


    }



    private void ShuffleAnswears() {
        for (int i = 0; i < test.getQuestions().size(); i++) {
            for (int j = 0; j < test.getQuestions().get(i).getAnswears().size(); j++) {
                shuffleArray(test.getQuestions().get(i).getAnswears());
            }
        }
    }

    private void shuffleArray(ArrayList<Answear> answears) {
        Random rnd = new Random();
        for (int i = answears.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            Answear a = answears.get(index);
            answears.set(index, answears.get(i));
            answears.set(i, a);
        }

    }
}
