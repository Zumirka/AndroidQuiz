package com.example.zumirka.androidquiz.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.zumirka.androidquiz.Model.Category;
import com.example.zumirka.androidquiz.Model.Statistic;
import com.example.zumirka.androidquiz.StatisticActivity;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class StatisticBackgroundWorker extends AsyncTask<String,Void,String> {

    Context context;
    String[] data;
    ArrayList<Statistic>  statsList=new ArrayList<>();
    StatisticActivity statisticActivity;

    public StatisticBackgroundWorker(Context ctx) {
        context = ctx;
    }
    public StatisticBackgroundWorker(StatisticActivity statsActiviti) {
        this.statisticActivity=statsActiviti;
    }
    @Override
    protected String doInBackground(String... params) {

        String type = params[0];

        if (type.equals("addStats")) {
            try {
                String register_url = "http://quizinz.herokuapp.com/addStatistic.php";
                String userName = params[1];
                String categoryId = params[2];
                String diff = params[3];
                String time = params[4];
                String points = params[5];
                URL url = new URL(register_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&"
                        + URLEncoder.encode("CategoryId", "UTF-8") + "=" + URLEncoder.encode(categoryId, "UTF-8") + "&"
                        + URLEncoder.encode("Difficulty", "UTF-8") + "=" + URLEncoder.encode(diff, "UTF-8") + "&"
                        + URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&"
                        + URLEncoder.encode("Points", "UTF-8") + "=" + URLEncoder.encode(points, "UTF-8");


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

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("getStats")) {
            try {
                String register_url = "http://quizinz.herokuapp.com/getStatistic.php";
                String userName = params[1];
                String time1 = params[2];
                String time2 = params[3];
                URL url = new URL(register_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("UserName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                        +URLEncoder.encode("time1","UTF-8")+"="+URLEncoder.encode(time1,"UTF-8")+"&"
                        +URLEncoder.encode("time2","UTF-8")+"="+URLEncoder.encode(time2,"UTF-8");
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
                httpURLConnection.disconnect();

                try
                {
                    JSONArray jsArray=new JSONArray(result);
                    JSONObject jsObject=null;

                    for(int i=0;i<jsArray.length();i++)
                    {
                       Statistic stats=new Statistic();
                        jsObject=jsArray.getJSONObject(i);
                        stats.setCategoryName(jsObject.getString("CategoryName"));
                        stats.setDifficulty(jsObject.getString("Difficulty"));
                        stats.setTime(jsObject.getString("Time"));
                        stats.setPoints(jsObject.getString("Points"));
                        stats.setDate(jsObject.getString("Date"));
                        statsList.add(stats);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
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
    }

    @Override
    protected void onPostExecute(String result) {
        statisticActivity.setCategory(statsList);
    }
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
