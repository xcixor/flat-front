package com.example.flat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvRoomType;
        TextView tvPrice;
        TextView tvLocation;
        ImageView imageView;
        private static final int radius = 10;
        private static final int margin = 0;
        private Transformation transformation;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomType = (TextView)itemView.findViewById(R.id.room_type);
            tvPrice = (TextView)itemView.findViewById(R.id.price);
            tvLocation = (TextView)itemView.findViewById(R.id.location);
            imageView = (ImageView)itemView.findViewById(R.id.room_main_image);
            itemView.setOnClickListener(this);
        }
        public void bind(Room room){
            tvRoomType.setText(room.roomType);
            tvPrice.setText(Integer.toString(room.price));
            tvLocation.setText(room.location);
            transformation = new RoundedCornersTransformation(radius, margin);
            if(!room.image.isEmpty()) {
                Picasso.get()
                        .load(room.image)
                        .transform(transformation)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .fit()
                        .into(imageView);
            }else {
//                Picasso.get()
//                        .load(R.drawable.image_placeholder)
//                        .transform(transformation)
//                        .fit()
//                        .into(imageView);
                imageView.setBackgroundResource(R.drawable.placeholder_image);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Room selectedRoom = rooms.get(position);
            Intent intent = new Intent(view.getContext(), RoomDetailActivity.class);
            intent.putExtra("Room", selectedRoom);
            view.getContext().startActivity(intent);
        }
    }
}
