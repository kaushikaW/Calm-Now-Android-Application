package com.mycomapny.neraby;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUri {

    public String retireveUrl(String uri) throws IOException{
        String uriData = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{
            URL getUrl = new URL(uri);
            httpURLConnection = (HttpURLConnection) getUrl.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";

            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }

            uriData = sb.toString();
            bufferedReader.close();
        }
        catch (Exception e){
            Log.d("Exception",e.toString());
        }finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return uriData;
    }



}
