package com.example.zumirka.androidquiz.AsyncTasks;

import android.os.AsyncTask;

import com.example.zumirka.androidquiz.Model.Answer;
import com.example.zumirka.androidquiz.Model.Question;
import com.example.zumirka.androidquiz.Model.Test;

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

/**
 * Created by Zumirka on 28.12.2017.
 */

public class TestDownloadBackgroundWorker extends AsyncTask<Void,Void,Void>{

    private Test test;
    private int caterogryId;
    private int difficult;
    public TestDownloadBackgroundWorker(int caterogryId,int difficult)
    {
        this.caterogryId=caterogryId;
        this.difficult=difficult;
        test=new Test();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        String getCategory_url="http://quizinz.herokuapp.com/getTest.php";
        try {
            URL url = new URL(getCategory_url);

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();

            BufferedWriter bufferWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(Integer.toString(caterogryId),"UTF-8")+"&"
                    +URLEncoder.encode("diff","UTF-8")+"="+URLEncoder.encode(Integer.toString(difficult),"UTF-8");
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

            try
            {
               JSONArray jsArray=new JSONArray(result);
               JSONObject jsObject=null;

                for(int i=0;i<jsArray.length();i++)
                {
                    jsObject=jsArray.getJSONObject(i);


                    if(test.getQuestions().size()==0||!test.getLastQuestion().getContent().equals(jsObject.getString("Question")))
                    {
                        Question q=new Question();
                        q.setContent(jsObject.getString("Question"));
                        q.setDifficult(jsObject.getInt("Difficult"));
                        test.getQuestions().add(q);
                    }
                    Answer a =new Answer();
                    a.setContent(jsObject.getString("Answer"));
                    if(jsObject.getInt("IsTrue")==1)
                    a.setiSTrue(true);
                    else a.setiSTrue(false);
                    test.getLastQuestion().addAnswer(a);
                }

            } catch (Exception e) {
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
}
