package com.example.zumirka.androidquiz.AsyncTasks;

import android.content.Context;
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

public class AddStatisticBackgroundWorker extends AsyncTask<String,Void,String> {

    Context context;
    String[] data;

   public AddStatisticBackgroundWorker(Context ctx) {context=ctx; }

    @Override
    protected String doInBackground(String... params) {

        String type=params[0];
        String register_url="http://quizinz.herokuapp.com/addStatistic.php";

        try {
            String userName= params[1];
            String categoryId= params[2];
            String diff=params[3];
            String time=params[4];
            String points=params[5];
            URL url = new URL(register_url);

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data = URLEncoder.encode("UserName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                    +URLEncoder.encode("CategoryId","UTF-8")+"="+URLEncoder.encode(categoryId,"UTF-8")+"&"
                    +URLEncoder.encode("Difficulty","UTF-8")+"="+URLEncoder.encode(diff,"UTF-8")+"&"
                    +URLEncoder.encode("Time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"
                    +URLEncoder.encode("Points","UTF-8")+"="+URLEncoder.encode(points,"UTF-8");

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
    }

    @Override
    protected void onPostExecute(String result) {

    }
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
