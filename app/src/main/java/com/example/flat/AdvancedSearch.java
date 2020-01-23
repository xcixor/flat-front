package com.example.flat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class AdvancedSearch extends AppCompatActivity {

    private EditText etLocation;
    private EditText etPrice;
    private EditText etRoomType;
    private EditText etOwner;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        etLocation = (EditText)findViewById(R.id.location_search_edit_text);
        etPrice = (EditText)findViewById(R.id.price_search_edit_text);
        etRoomType = (EditText)findViewById(R.id.room_type_search_edit_text);
        etOwner = (EditText)findViewById(R.id.owner_search_edit_text);
        searchBtn = (Button)findViewById(R.id.search_btn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = etLocation.getText().toString().trim();
                String roomType = etRoomType.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                String owner = etOwner.getText().toString().trim();
                if (location.isEmpty() && roomType.isEmpty() && price.isEmpty() && owner.isEmpty()){
                    String errorMessage = getString(R.string.search_data_error);
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                }else{
                    URL query = ApiUtil.buildUrl("/advanced_search", location, roomType, price, owner);

                    Context context = getApplicationContext();
                    int position = SPUtil.getPreferencesInt(context, SPUtil.POSITION);

                    if (position == 0 || position == 5){
                        position = 1;
                    }else{
                        position++;
                    }

                    String key = SPUtil.QUERY + String.valueOf(position);
                    String value = location + "," + roomType + "," + price + "," + owner + ",";
                    SPUtil.setPrefString(context, key, value);
                    SPUtil.setPrefInt(context, SPUtil.POSITION, position);

                    Intent intent = new Intent(getApplicationContext(), RoomListActivity.class);
                    intent.putExtra("query", query.toString());
                    startActivity(intent);
                }
            }
        });
    }
}
