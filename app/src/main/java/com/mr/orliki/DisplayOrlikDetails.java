package com.mr.orliki;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayOrlikDetails extends AppCompatActivity {
    private Orlik orlik = null;
    private ReservationsRecyclerViewAdapter mRecyclerViewAdapter;

    public static final String addExtra = "addExtra";
    public static final String getExtra = "getExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_orlik_details);
        Toolbar toolbar = findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent != null){
            orlik = ((Intent) intent).getParcelableExtra(MainActivity.fieldExtra);
        }
        if(orlik != null){
            getSupportActionBar().setTitle(orlik.adres);
        }


        View view = findViewById(R.id.list_reservations);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            mRecyclerViewAdapter = new ReservationsRecyclerViewAdapter(ReservationListContent.ITEMS);
            recyclerView.setAdapter(mRecyclerViewAdapter);

            ArrayList<Reservation> reservations = orlik.reservations;
            ReservationListContent.addItem(ReservationListContent.createReservation("KTO", "KIEDY", orlik.id, "H", "00001"));

            for(int i=0; i< reservations.size(); i++){
                Reservation reservation = reservations.get(i);
                String who = reservation.who;
                String date = reservation.date;
                String where = reservation.where;
                String time = reservation.time;
                String id = reservation.id;
                ReservationListContent.addItem(ReservationListContent.createReservation( who, date, where, time, id));
            }


            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void fabOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddReservation.class);
        intent.putExtra(addExtra, orlik.id);
        startActivity(intent);
    }

    public void onStop() {
        ReservationListContent.clearList();
        super.onStop();

    }

}
