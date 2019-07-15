package com.mr.orliki;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrlikListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Orlik> ITEMS = new ArrayList<Orlik>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Orlik> ITEM_MAP = new HashMap<String, Orlik>();


    public static void addItem(Orlik item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static Orlik createOrlik(int position,String address, String id) {
        return new Orlik(String.valueOf(position), address, id);
    }

    public static Orlik getOrlik(int i){
        return ITEMS.get(i);
    }
    public static void clearList(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void removeItem(int position){
        String itemID = ITEMS.get(position).id;
        ITEMS.remove(position);
        ITEM_MAP.remove(itemID);
    }

    public static class Orlik implements Parcelable {
        public final String id;
        public final String adres;
        public final String position;

        public Orlik(String position, String adres, String id ) {
            this.id = id;
            this.adres = adres;
            this.position = position;
        }

        protected Orlik(Parcel in) {
            position = in.readString();
            id = in.readString();
            adres = in.readString();
        }

        public static final Creator<Orlik> CREATOR = new Creator<Orlik>() {
            @Override
            public Orlik createFromParcel(Parcel in) {
                return new Orlik(in);
            }

            @Override
            public Orlik[] newArray(int size) {
                return new Orlik[size];
            }
        };

        @Override
        public String toString() {
            return adres;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(position);
            dest.writeString(id);
            dest.writeString(adres);
        }
    }
}