package com.mr.orliki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class OrlikAdapter extends ArrayAdapter<Orlik> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public OrlikAdapter(Context context, int textViewResourceId, ArrayList<Orlik> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.content_field, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.textView_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Orlik item = getItem(position);
        if (item!= null) {
            viewHolder.itemView.setText(item.adres);
        }

        return convertView;
    }
}
