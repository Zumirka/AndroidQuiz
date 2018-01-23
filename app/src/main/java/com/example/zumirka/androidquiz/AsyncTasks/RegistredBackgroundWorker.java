package com.example.zumirka.androidquiz.AsyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.zumirka.androidquiz.RegisterActivity;

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


public class RegistredBackgroundWorker extends AsyncTask<String, Void, String> {
    Context ctx;
    RegisterActivity re;
    ProgressDialog dialog;
    String login;
    String password;
    AlertDialog alert;
    String register_url = "http://quizinz.herokuapp.com/registred.php";


    public RegistredBackgroundWorker(RegisterActivity reg, Context ctx, String login, String password) {
        dialog = new ProgressDialog(ctx);
        this.re = reg;
        this.ctx = ctx;
        this.login = login;
        this.password = password;
        alert = new AlertDialog.Builder(ctx).create();

    }


    @Override
    protected String doInBackground(String... params) {


        try {

            HttpURLConnection httpURLConnection = getHttpURLConnection();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = parseQuery();
            sendQuery(outputStream, bufferWriter, post_data);
            InputStream inputStream = httpURLConnection.getInputStream();
            String result = reciveData(httpURLConnection, inputStream);
            return result;

        } catch (UnknownHostException e) {
            alert.setMessage("Brak połączenia z internetem");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @NonNull
    private String reciveData(HttpURLConnection httpURLConnection, InputStream inputStream) throws IOException {
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
        URL url = new URL(register_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Rejestracja...");
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
            Toast.makeText(ctx, "Zostałeś zarejestrowany\n Możesz się zalogować", Toast.LENGTH_LONG).show();
            re.finish();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            re.showErrorDialog("Użytkownik o podanym loginie już istnieje");
        }
    }


}
