package com.example.flat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RoomListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private RecyclerView rvRooms;
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        mLoadingProgress = (ProgressBar)findViewById(R.id.pb_loading);

        rvRooms = (RecyclerView)findViewById(R.id.rv_rooms);
        LinearLayoutManager roomsLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        rvRooms.setLayoutManager(roomsLayoutManager);
        Intent advancedSearchIntent = getIntent();
        String query = advancedSearchIntent.getStringExtra("Query");
        URL roomUrl = null;
        try {
            if (query == null || query.isEmpty()){
                roomUrl = ApiUtil.buildUrl("rooms");
            }else{
                roomUrl = new URL(query);
            }
            new RoomsQueryTask().execute(roomUrl);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.room_list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_advanced_search:
                Intent intent = new Intent(this, AdvancedSearch.class);
                startActivity(intent);
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String searchQuery;
        try {
            searchQuery = "rooms/" + Integer.parseInt(query);
            URL searchUrl = ApiUtil.buildUrl(searchQuery);
            new RoomsQueryTask().execute(searchUrl);
        }catch (Exception e){
            Log.d("error", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    //    NB using the async task strategy to work outside the main thread can have implications
//    when working with networking tasks, when you get comfortable, use an IntentService
//    instead to achieve the same
    public class RoomsQueryTask extends AsyncTask<URL, Void, String>{

    @Override
    protected String doInBackground(URL... urls) {
//        android.os.Debug.waitForDebugger();
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
        mLoadingProgress.setVisibility(View.INVISIBLE);
        TextView tvError = (TextView)findViewById(R.id.tv_error);
        TextView tvHead = (TextView)findViewById(R.id.tv_head);
        if (result == null){
            rvRooms.setVisibility(View.INVISIBLE);
            tvError.setVisibility(View.VISIBLE);
        }else{
            ArrayList<Room> rooms = ApiUtil.getRoomsFromJson(result);
            RoomsAdapter adapter = new RoomsAdapter(rooms);
            rvRooms.setAdapter(adapter);
            tvError.setVisibility(View.INVISIBLE);
            rvRooms.setVisibility(View.VISIBLE);
            tvHead.setText("View over " + Integer.toString(rooms.size()) + "+ rentals waiting");
        }

    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        mLoadingProgress.setVisibility(View.VISIBLE);
    }
}
}
