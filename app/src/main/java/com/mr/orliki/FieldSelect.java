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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FieldSelect extends AppCompatActivity implements MyOrlikRecyclerViewAdapter.OnListFragmentInteractionListener {
    private MyOrlikRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_select);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View view = findViewById(R.id.list);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            mRecyclerViewAdapter = new MyOrlikRecyclerViewAdapter(OrlikListContent.ITEMS, this);
            recyclerView.setAdapter(mRecyclerViewAdapter);

            ArrayList<Orlik> orliks = Constants.getOrliksSharedPref(this);
            for(int i=0; i< orliks.size(); i++){
                Orlik orlik = orliks.get(i);
                String adres = orlik.adres;
                String id = orlik.id;
                Log.i("test", orlik.adres + orlik.id);

                OrlikListContent.addItem(OrlikListContent.createOrlik( i, adres, id));
            }


            mRecyclerViewAdapter.notifyDataSetChanged();
        }

    }

    public void notifyDataChange() {
        mRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onListFragmentClickInteraction(OrlikListContent.Orlik orlik, int position) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("idRet", orlik.id);
        setResult(FieldSelect.RESULT_OK,returnIntent);
        finish();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onStop() {
        OrlikListContent.clearList();
        super.onStop();

    }
}
