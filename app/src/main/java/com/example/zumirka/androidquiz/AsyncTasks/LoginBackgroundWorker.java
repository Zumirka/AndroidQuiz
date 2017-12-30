package com.example.zumirka.androidquiz.AsyncTasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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


public class LoginBackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alert;
    String[] data;


    public LoginBackgroundWorker (Context ctx)

    {
        context=ctx;
    }


    @Override
    protected String doInBackground(String...params) {

        String type=params[0];
        String login_url="http://quizinz.herokuapp.com/login.php";

            try {
                String login= params[1];
                String password= params[2];
                URL url = new URL(login_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("login","UTF-8")+"="+URLEncoder.encode(login,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferWriter.write(post_data);
                bufferWriter.flush();
                bufferWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result="";
                String line;
                while((line=bufferedReader.readLine())!=null)
                {
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

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
        alert=new AlertDialog.Builder(context).create();
        alert.setTitle("Login Status");

    }

    @Override
    protected void onPostExecute(String result) {


        if(result.equals("1")) {
            alert.setMessage("logowanie powiodło się");
            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    context.startActivity(new Intent(context, MainMenuActivity.class));
                }
            });

        }else
        {
            alert.setMessage("logowanie nie powiodło się");

        }
        alert.show();
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
