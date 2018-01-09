package com.example.zumirka.androidquiz.AsyncTasks;

import android.os.AsyncTask;
import com.example.zumirka.androidquiz.MainMenuActivity;
import com.example.zumirka.androidquiz.Model.Category;

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
import java.util.ArrayList;
import java.util.List;

public class CategoryBackgroundWorker extends AsyncTask<Void,Void,Void> {
    MainMenuActivity mm;
   ArrayList<Category> categoriesList=new ArrayList<>();


   public CategoryBackgroundWorker (MainMenuActivity MMA)
    {
        this.mm=MMA;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        String getCategory_url="http://quizinz.herokuapp.com/getCategory.php";


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

                    for(int i=0;i<jsArray.length();i++)
                    {
                        Category category= new Category();
                        jsObject=jsArray.getJSONObject(i);

                        category.setCategoryID(jsObject.getInt("Id"));
                        category.setNameOFCategory(jsObject.getString("Name"));
                        categoriesList.add(category);
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
        return null;
    }


    @Override
    protected void onPreExecute() {


    }

    @Override
    protected void onPostExecute(Void result) {
        mm.setCategory(categoriesList);


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
