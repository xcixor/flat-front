package com.example.flat;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}

    public  static final String BASE_API_URL = "http://35.208.96.236/";

    public static URL buildUrl(String query){
        String fullUrl = BASE_API_URL + query + "/";
        URL url = null;
        try {
            url = new URL(fullUrl);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public  static String getJsonData(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();
            if (hasData){
                return scanner.next();
            }else {
                return null;
            }
        }catch (Exception e){
            Log.d("Data Error", e.toString());
            return null;
        }
        finally {
                connection.disconnect();
            }
    }
}
