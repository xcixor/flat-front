package com.example.flat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class RoomListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        try {
            URL roomUrl = ApiUtil.buildUrl("rooms");
            new RoomsQueryTask().execute(roomUrl);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

//    NB using the async task strategy to work outside the main thread can have implications
//    when working with networking tasks, when you get comfortable, use an IntentService
//    instead to achieve the same
    public class RoomsQueryTask extends AsyncTask<URL, Void, String>{

    @Override
    protected String doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String result = null;
        try {
            result = ApiUtil.getJsonData(searchUrl);
        } catch (IOException e) {
            Log.d("Error", e.getMessage());
        }
        return result;
    }
    @Override
    protected void onPostExecute (String result){
        TextView tvResponse = (TextView) findViewById(R.id.tv_response);
        tvResponse.setText(result);

    }
}
}
