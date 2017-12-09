package com.example.zumirka.androidquiz.AsyncTasks;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Zumirka on 03.12.2017.
 */

public class CategoryBackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alert;
    String[] data;

   public CategoryBackgroundWorker (Context ctx)

    {
        context=ctx;
    }


    @Override
    protected String doInBackground(String...params) {

        String type=params[0];
        String getCategory_url="http://quizinz.herokuapp.com/getCategory.php";


        if(type.equals("category"))
        {
            try {
                URL url = new URL(getCategory_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader((new InputStreamReader(inputStream,"UTF-8")));

                String result="";
                String line;
                while((line=bufferedReader.readLine())!=null)
                {
                    result+=line;
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
                try
                {
                    JSONArray jsArray=new JSONArray(result);
                    JSONObject jsObject=null;
                    data=new String[jsArray.length()];
                    for(int i=0;i<jsArray.length();i++)
                    {
                        jsObject=jsArray.getJSONObject(i);
                        data[i]=jsObject.getString("Name");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return result;

            }
            catch(MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
