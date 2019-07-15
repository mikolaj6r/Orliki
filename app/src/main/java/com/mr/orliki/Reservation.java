package com.mr.orliki;

import android.os.Parcel;
import android.os.Parcelable;

public class Reservation  implements Parcelable {
    public final String time;
    public final String date;
    public final String who;
    public final String where;
    public final String id;

    public Reservation( String time, String date, String who, String where, String id ){
        this.time = time;
        this.date = date;
        this.who = who;
        this.where = where;
        this.id = id;
    }

    protected Reservation(Parcel in) {
        time = in.readString();
        date = in.readString();
        who = in.readString();
        where = in.readString();
        id = in.readString();
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(who);
        dest.writeString(where);
        dest.writeString(id);
    }
}
