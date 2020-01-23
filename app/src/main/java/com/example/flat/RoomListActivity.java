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
    private TextView tvWelcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        mLoadingProgress = (ProgressBar)findViewById(R.id.pb_loading);
        tvWelcomeMsg = (TextView)findViewById(R.id.tv_welcome_message);
        tvWelcomeMsg.setVisibility(View.VISIBLE);

        rvRooms = (RecyclerView)findViewById(R.id.rv_rooms);
        LinearLayoutManager roomsLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        rvRooms.setLayoutManager(roomsLayoutManager);
        Intent advancedSearchIntent = getIntent();
        String query = advancedSearchIntent.getStringExtra("query");
        URL roomUrl = null;
        try {
            if (query == null || query.isEmpty()){
                roomUrl = ApiUtil.buildUrl("/","", "");
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
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        ArrayList<String> recentQueriesList = SPUtil.getQueryList(getApplicationContext());
        int queriesTotal = recentQueriesList.size();
        MenuItem recentMenu;
        for (int i = 0; i<queriesTotal; i++){
            recentMenu = menu.add(Menu.NONE, i, Menu.NONE, recentQueriesList.get(i));

        }
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
                    int position = item.getItemId() + 1;
                    String preferenceName = SPUtil.QUERY + String.valueOf(position);
                    String query = SPUtil.getPreferencesString(getApplicationContext(), preferenceName);
                    String[] prefParams = query.split("\\,");
                    String[] queryParams = new String[4];
                    for (int i=0; i<prefParams.length; i++){
                        queryParams[i] = prefParams[i];
                    }
                    URL roomUrl = ApiUtil.buildUrl(
                            "/advanced_search",
                            (queryParams[0] == null) ? "": queryParams[0],
                            (queryParams[1] == null) ? "": queryParams[1],
                            (queryParams[2] == null) ? "": queryParams[2],
                            (queryParams[3] == null) ? "": queryParams[3]
                            );
                    new RoomsQueryTask().execute(roomUrl);
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        String searchQuery;
        try {
//            searchQuery = "rooms/" + Integer.parseInt(query);
            URL searchUrl = ApiUtil.buildUrl("/search", "search", query);
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
        tvWelcomeMsg.setVisibility(View.INVISIBLE);
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
