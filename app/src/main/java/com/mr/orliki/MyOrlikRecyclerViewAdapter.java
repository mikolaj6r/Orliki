package com.mr.orliki;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyOrlikRecyclerViewAdapter extends RecyclerView.Adapter<MyOrlikRecyclerViewAdapter.ViewHolder> {

    private final List<OrlikListContent.Orlik> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyOrlikRecyclerViewAdapter(List<OrlikListContent.Orlik> items, OnListFragmentInteractionListener listener) {
        Log.i("test", items.size() + "");
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_field, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        OrlikListContent.Orlik orlik = mValues.get(position);
        holder.mItem = orlik;
        holder.mTitleView.setText(orlik.adres);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentClickInteraction(holder.mItem, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public OrlikListContent.Orlik mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.textView_item);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.adres + "'";
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentClickInteraction(OrlikListContent.Orlik orlik, int position);
    }
}