package com.example.zumirka.androidquiz.AsyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.example.zumirka.androidquiz.MainMenuActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;


public class LoginBackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alert ;
    String[] data;
    String login;

    ProgressDialog dialog;

    public LoginBackgroundWorker (Context ctx)

    {
        alert=new AlertDialog.Builder(ctx).create();
        dialog=new ProgressDialog(ctx);
        context=ctx;

    }



    @Override
    protected String doInBackground(String...params) {

        String type=params[0];
        String login_url="http://quizinz.herokuapp.com/login.php";

            try {
                login= params[1];
                String password= params[2];
                URL url = new URL(login_url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(login, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                    bufferWriter.write(post_data);
                    bufferWriter.flush();
                    bufferWriter.close();
                    outputStream.close();
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
                } catch (UnknownHostException e)
                {
                    alert.setMessage("Brak połączenia z internetem");
                }

            }

            catch(MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
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


        if(result.equals("1")) {
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



        }else
        {
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
    public String[] getData()
    {
        return data;
    }
}
