package com.example.user.android_hw7;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ADDRESS = "Add";
    private static final String NAME = "Name";
    private static final String IMAGE = "Picture1";

    private ListView lst_hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst_hotel = (ListView)findViewById(R.id.lst_hotel);

        HotelArrayAdapter adapter = new HotelArrayAdapter(this, new ArrayList<Hotel>());
        lst_hotel.setAdapter(adapter);

        getHotelsFromFirebase();
    }

    private void getHotelsFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new FirebaseThread(dataSnapshot).start();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for(DataSnapshot ds: dataSnapshot.getChildren()){
//                            DataSnapshot dsAddress = ds.child(ADDRESS);
//                            DataSnapshot dsName = ds.child(NAME);
//                            DataSnapshot dsImage = ds.child(IMAGE);
//
//                            String hotelAddress =(String)dsAddress.getValue();
//                            String hotelName =(String)dsName.getValue();
//                            String hotelImage =(String)dsImage.getValue();
//
//                            Bitmap img = getBitmap(hotelImage);
//
//                            Log.v("Hotel Info", hotelName + " : " + hotelAddress);
//                        }
//                    }
//                }).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Hotel Info", databaseError.getMessage());
            }
        });
    }

    private Bitmap getBitmap(String hotelImage) {
        try{
            URL url = new URL(hotelImage);
            Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bm;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    class FirebaseThread extends Thread{
        private DataSnapshot dataSnapshot;

        private static final int LIST_HOTELS = 1;
        private HotelArrayAdapter adapter = null;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case LIST_HOTELS:
                        List<Hotel> hotels = (List<Hotel>) msg.obj;
                        refreshHotelList(hotels);
                        break;
                }
            }
        };

        private void refreshHotelList(List<Hotel> hotels){
            adapter.clear();
            adapter.addAll(hotels);
        }

        public FirebaseThread(DataSnapshot dataSnapshot){
            this.dataSnapshot = dataSnapshot;
        }

        public void run(){
            List<Hotel> lsHotels = new ArrayList<>();
            for(DataSnapshot ds: dataSnapshot.getChildren()){
                DataSnapshot dsAddress = ds.child(ADDRESS);
                DataSnapshot dsName = ds.child(NAME);
                DataSnapshot dsImage = ds.child(IMAGE);

                String hotelAddress =(String)dsAddress.getValue();
                String hotelName =(String)dsName.getValue();
                String hotelImage =(String)dsImage.getValue();
                Bitmap img = getBitmap(hotelImage);

                lsHotels.add(new Hotel(img, hotelName, hotelAddress));

                Log.v("Hotel Info", hotelName + " : " + hotelAddress);

                Message message = new Message();
                message.what = LIST_HOTELS;
                message.obj = lsHotels;
                handler.sendMessage(message);
            }
        }
    }
}
