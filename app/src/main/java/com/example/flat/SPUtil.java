package com.example.flat;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SPUtil {
    private SPUtil(){}

    public static final String PREF_NAME = "RoomPreferences";
    public static final String POSITION = "position";
    public static final String QUERY = "query";

    public static SharedPreferences getRoomPreferences(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getPreferencesString(Context context, String key){
        return getRoomPreferences(context).getString(key, "");
    }

    public static int getPreferencesInt(Context context, String key){
        return getRoomPreferences(context).getInt(key, 0);
    }

    public static void setPrefString(Context context, String key, String value){
        SharedPreferences.Editor editor = getRoomPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setPrefInt(Context context, String key, int value){
        SharedPreferences.Editor editor = getRoomPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static ArrayList<String> getQueryList(Context context){
        ArrayList<String> queryList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            String query = getRoomPreferences(context).getString(QUERY + String.valueOf(i), "");
            if(!query.isEmpty()){
                query = query.replace(",", " ");
                queryList.add(query);
            }
        }
        return queryList;
    }
}
