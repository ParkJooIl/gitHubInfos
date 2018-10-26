package userinfo.github.sundaypark.githubuserinfo;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import userinfo.github.sundaypark.githubuserinfo.core.item_repo;
import userinfo.github.sundaypark.githubuserinfo.core.item_userinfo;
import userinfo.github.sundaypark.githubuserinfo.listiner.ConnectionListiner;

public class aSyncuserinfo extends AsyncTask<String , Integer , Integer> {
    ArrayList<Object> item_cores = new ArrayList<>();
    ConnectionListiner connectionListiner;
    public aSyncuserinfo(ConnectionListiner connectionListiner) {
        this.connectionListiner = connectionListiner;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        String ResultString = JsonResult( "https://api.github.com/users/" + strings[0]);

        JsonParser paser = new JsonParser();
        JsonElement ResultCheck = paser.parse(ResultString);
        if(ResultString == null ){
            return ConnectionListiner.ResultCodeError;
        }
        if(ResultCheck.getAsJsonObject().has("message")){
            Log.v("HTTP" , ResultString);
            return ConnectionListiner.ResultCodeNoname;
        }
        Gson gson = new Gson();
        item_userinfo userinfo = (item_userinfo)gson.fromJson(ResultString , item_userinfo.class);
        // 사용자 정보 입력 끝
        item_cores.add(userinfo);
        String ResultrepoString = JsonResult( "https://api.github.com/users/" + strings[0]+ "/repos");
        item_repo[] repos = gson.fromJson(ResultrepoString , item_repo[].class);
        item_cores.addAll(Arrays.asList(repos));
        Log.v("HTTP" , "오케 [" + item_cores.size());

        return ConnectionListiner.ResultCodeOK;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(Integer s) {
        connectionListiner.OnResults((int)s , item_cores);
    }



    private String JsonResult(String Url){
        Log.v("HTTP" , Url);
        HttpURLConnection httpurl = null;
        try{
            URL Addr = new URL(Url);
            httpurl = (HttpURLConnection) Addr.openConnection();
            httpurl.setRequestMethod("GET");
            if(httpurl.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpurl.getInputStream(), "UTF-8"));
            String line;
            String page = "";

            while ((line = reader.readLine()) != null){
                page += line;
            }
            return page;
        }catch(MalformedURLException e){
            Log.e("HTTP" , "주소확인");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(httpurl != null){
                httpurl.disconnect();
                httpurl = null;
            }
        };
        return null;
    }
}
