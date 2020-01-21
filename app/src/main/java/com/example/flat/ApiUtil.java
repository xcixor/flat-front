package com.example.flat;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

    public static ArrayList<Room> getRoomsFromJson(String json){

        final String ID = "id";
        final String ROOMTYPE = "room_type";
        final String LOCATION = "location";
        final String PRICE = "price";
        final String RESULTS = "results";
        final String COUNT = "count";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";

        ArrayList<Room> rooms = new ArrayList<Room>();

        try {
            JSONObject jsonRooms = new JSONObject(json);
            JSONArray arrayRooms = jsonRooms.getJSONArray(RESULTS);
            int noOfRooms = arrayRooms.length();

            for (int i = 0; i < noOfRooms; i++){
                JSONObject jsonRoom = arrayRooms.getJSONObject(i);
                Room room = new Room(
                        Integer.toString(jsonRoom.getInt(ID)),
                        jsonRoom.getString(ROOMTYPE),
                        jsonRoom.getString(LOCATION),
                        jsonRoom.getInt(PRICE),
                        jsonRoom.getString(DESCRIPTION),
                        jsonRoom.getString(IMAGE)
                );
                rooms.add(room);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return rooms;
    }
}
