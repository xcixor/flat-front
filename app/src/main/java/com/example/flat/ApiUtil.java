package com.example.flat;

import android.content.Intent;
import android.net.Uri;
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
import java.util.concurrent.ExecutionException;

public class ApiUtil {

    public static final String QUERY_PARAMETER_KEY = "?";

    private ApiUtil(){}

    public  static final String BASE_API_URL = "http://35.208.96.236/";

    public static final String ID = "id";
    public static final String ROOMTYPE = "room_type";
    public static final String LOCATION = "location";
    public static final String PRICE = "price";
    public static final String RESULTS = "results";
    public static final String COUNT = "count";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String OWNER = "owner_search_edit_text";

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

    public static URL buildUrl (String location, String room_type, String price, String owner){
        URL url = null;
        StringBuilder sb = new StringBuilder();
        if (!location.isEmpty()) sb.append(LOCATION + location + "&");
        if(!room_type.isEmpty()) sb.append(ROOMTYPE + room_type + "&");
        if(!price.isEmpty()) sb.append(PRICE + price + "&");
        if(!owner.isEmpty()) sb.append(OWNER + owner + "&");
        sb.setLength(sb.length() - 1);
        String query = sb.toString();
        Uri uri = Uri.parse(BASE_API_URL)
                .buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, query)
                .build();
        try {
            url = new URL(uri.toString());
            Log.d("** URL **", String.valueOf(url));
        }catch (Exception e){
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



        ArrayList<Room> rooms = new ArrayList<Room>();

        try {
            JSONObject jsonRooms = new JSONObject(json);
            JSONArray arrayRooms = jsonRooms.getJSONArray(RESULTS);
            int noOfRooms = arrayRooms.length();

            for (int i = 0; i < noOfRooms; i++){
                JSONObject jsonRoom = arrayRooms.getJSONObject(i);
                Room room = new Room(
                        Integer.toString(jsonRoom.getInt(ID)),
                        jsonRoom.isNull(ROOMTYPE) ? "": jsonRoom.getString(ROOMTYPE),
                        jsonRoom.isNull(LOCATION) ? "": jsonRoom.getString(LOCATION),
                        jsonRoom.getInt(PRICE),
                        jsonRoom.isNull(DESCRIPTION) ? "": jsonRoom.getString(DESCRIPTION),
                        jsonRoom.isNull(IMAGE) ? "": jsonRoom.getString(IMAGE)
                );
                rooms.add(room);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return rooms;
    }
}
