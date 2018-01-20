package com.example.zumirka.androidquiz.AsyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.zumirka.androidquiz.MainMenuActivity;

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


public class LoginBackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alert;
    String[] data;
    String login;
    String password;
    String login_url = "http://quizinz.herokuapp.com/login.php";
    ProgressDialog dialog;

    public LoginBackgroundWorker(Context ctx, String login, String password)

    {
        alert = new AlertDialog.Builder(ctx).create();
        dialog = new ProgressDialog(ctx);
        context = ctx;
        this.login = login;
        this.password = password;

    }


    @Override
    protected String doInBackground(String... params) {


        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = parseQuery();
            sendQuery(outputStream, bufferWriter, post_data);
            String result = reciveData(httpURLConnection);
            return result;

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        return URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(login, "UTF-8") + "&"
                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
    }

    @NonNull
    private HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(login_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Logowanie...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();


    }

    @Override
    protected void onPostExecute(String result) {


        if (result.equals("1")) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(context, "Logowanie pomyślne", Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    context.startActivity(new Intent(context, MainMenuActivity.class));
                }
            }, 500);


        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            alert.setMessage("logowanie nie powiodło się. \n Spróbuj ponownie.");
            alert.show();

        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String[] getData() {
        return data;
    }
}
