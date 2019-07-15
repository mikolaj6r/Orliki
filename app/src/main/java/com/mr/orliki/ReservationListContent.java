package com.mr.orliki;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Reservation> ITEMS = new ArrayList<Reservation>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Reservation> ITEM_MAP = new HashMap<String, Reservation>();


    public static void addItem(Reservation item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static Reservation createReservation(String who, String date, String where, String time, String id) {
        return new Reservation(time, date, who, where, id);
    }

    public static Reservation getReservation(int i){
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
}