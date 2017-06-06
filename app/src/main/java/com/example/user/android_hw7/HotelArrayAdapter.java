package com.example.user.android_hw7;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2017/6/6.
 */

public class HotelArrayAdapter extends ArrayAdapter<Hotel> {

    Context context;

    public HotelArrayAdapter(Context context, List<Hotel> items){
        super(context, 0, items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout itemLayout = null;

        if (convertView == null){
            itemLayout = (LinearLayout)inflater.inflate(R.layout.hotel_item, null);
        }else{
            itemLayout = (LinearLayout)convertView;
        }

        Hotel hotel = (Hotel)getItem(position);
        TextView tvAddress = (TextView)itemLayout.findViewById(R.id.tv_address);
        TextView tvName = (TextView)itemLayout.findViewById(R.id.tv_name);
        ImageView ivImg = (ImageView)itemLayout.findViewById(R.id.iv_hotel);

        tvAddress.setText(hotel.getAddress());
        tvName.setText(hotel.getName());
        ivImg.setImageBitmap(hotel.getImage());

        return itemLayout;
    }
}
