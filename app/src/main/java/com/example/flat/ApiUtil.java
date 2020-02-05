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


    public ArrayList<Room> sRooms;
    private static ApiUtil sApiUtilInstance = null;
    private ApiQueryBuilder sQueryBuilder;
    private static final String BASEENDPOINT = "/";

    public static ApiUtil getInstance() {
        if(sApiUtilInstance == null) {
            sApiUtilInstance = new ApiUtil();
            sApiUtilInstance.initializeRooms();
        }
        return sApiUtilInstance;
    }

    private void initializeRooms() {
        sQueryBuilder = new ApiQueryBuilder.QueryBuilder(BASEENDPOINT).build();
        final URL url = sQueryBuilder.buildUrl();
        String result = null;
        try {
            result = getJsonData(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sRooms = getRoomsFromJson(result);
    }

    private ApiUtil(){}

    public static final String ID = "id";
    public static final String ROOMTYPE = "room_type";
    public static final String LOCATION = "location";
    public static final String PRICE = "price";
    public static final String RESULTS = "results";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";

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

    public Room getRoom(String id){
        for (Room room: sRooms){
            if (id.equals(room.id)){
                return room;
            }
        }
        return null;
    }
}
