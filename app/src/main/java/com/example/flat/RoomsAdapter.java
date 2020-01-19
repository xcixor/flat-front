package com.example.flat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>{

    ArrayList<Room> rooms;

    public RoomsAdapter(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.room_list_item, parent, false);
        return new RoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        TextView tvRoomType;
        TextView tvPrice;
        TextView tvLocation;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomType = (TextView)itemView.findViewById(R.id.room_type);
            tvPrice = (TextView)itemView.findViewById(R.id.price);
            tvLocation = (TextView)itemView.findViewById(R.id.location);

        }
        public void bind(Room room){
            tvRoomType.setText(room.roomType);
            tvPrice.setText(Integer.toString(room.price));
            tvLocation.setText(room.location);
        }
    }
}
