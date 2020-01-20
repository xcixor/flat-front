package com.example.flat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.flat.databinding.ActivityRoomDetailBinding;

public class RoomDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        Room room = getIntent().getParcelableExtra("Room");
        ActivityRoomDetailBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_room_detail);
        binding.setRoom(room);
    }
}
