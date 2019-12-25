package com.jangkung.ktm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sankarea on 1/8/2018.
 */

public class Item implements Parcelable{

    public String title;
    public String title2;
    public String nrp;
    public String prodi;
    public boolean isExpanded;

    public Item(){}

    public Item(Parcel in){
        title = in.readString();
        title2 = in.readString();
        nrp = in.readString();
        prodi = in.readString();
        isExpanded = in.readInt() == 1;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(title);
        dest.writeString(title2);
        dest.writeString(nrp);
        dest.writeString(prodi);
        dest.writeInt(isExpanded ? 1 : 0);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>(){
        @Override
        public Item createFromParcel(Parcel source){
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }

    };
}
