package com.mr.orliki;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Orlik implements Parcelable {
    public final String id;
    public final String adres;
    public  ArrayList<Reservation> reservations;

    public Orlik(String id, String adres, ArrayList<Reservation> reservations  ){
        this.id = id;
        this.adres = adres;
        this.reservations = reservations;
    }

    protected Orlik(Parcel in) {
        id = in.readString();
        adres = in.readString();
        reservations = in.readArrayList(Reservation.class.getClassLoader());
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(adres);
        dest.writeList(reservations);
    }
}
