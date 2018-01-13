package com.example.zumirka.androidquiz.AsyncTasks;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

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

public class AddQuestionBackgroundWorker  extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alert;
    String[] data;

  public AddQuestionBackgroundWorker(Context ctx){context=ctx;}

    @Override
    protected String doInBackground(String...params) {

        String type=params[0];
        String register_url="http://quizinz.herokuapp.com/addQuestion.php";

         try {
                String idCategory= params[1];
                String diff= params[2];
                String question=params[3];
                String answ1=params[4];
                String answ2=params[5];
                String answ3=params[6];
                URL url = new URL(register_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("idCategory","UTF-8")+"="+URLEncoder.encode(idCategory,"UTF-8")+"&"
                        +URLEncoder.encode("diff","UTF-8")+"="+URLEncoder.encode(diff,"UTF-8")+"&"
                        +URLEncoder.encode("question","UTF-8")+"="+URLEncoder.encode(question,"UTF-8")+"&"
                        +URLEncoder.encode("answ1","UTF-8")+"="+URLEncoder.encode(answ1,"UTF-8")+"&"
                        +URLEncoder.encode("answ2","UTF-8")+"="+URLEncoder.encode(answ2,"UTF-8")+"&"
                        +URLEncoder.encode("answ3","UTF-8")+"="+URLEncoder.encode(answ3,"UTF-8");

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
        alert.setTitle("Status dodawania:");
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
    }

    @Override
    protected void onPostExecute(String result) {
        alert.setMessage(result);
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


