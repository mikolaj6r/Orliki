package com.mr.orliki;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservationsRecyclerViewAdapter extends RecyclerView.Adapter<ReservationsRecyclerViewAdapter.ViewHolder> {

    private final List<Reservation> mValues;

    public ReservationsRecyclerViewAdapter(List<Reservation> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Reservation reservation = mValues.get(position);
        holder.mItem = reservation;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date d = sdf.parse(reservation.date);
            sdf.applyPattern("dd MMM hh:mm");
            holder.mTitleView1.setText( sdf.format(d) );
        } catch (ParseException e) {
            e.printStackTrace();
            holder.mTitleView1.setText(reservation.date);
        }
        holder.mTitleView2.setText(reservation.who);
        holder.mTitleView3.setText(reservation.time);
        //holder.mTitleView1.setText(reservation.who);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView1;
        public final TextView mTitleView2;
        public final TextView mTitleView3;
        public Reservation mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView1 = (TextView) view.findViewById(R.id.textView1);
            mTitleView2 = (TextView) view.findViewById(R.id.textView2);
            mTitleView3 = (TextView) view.findViewById(R.id.textView3);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.who + "'";
        }
    }

}