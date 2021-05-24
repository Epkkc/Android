package com.example.week6hw1;

import android.os.Parcel;
import android.os.Parcelable;

public class Mocks implements Parcelable{


    protected Mocks(Parcel in) {
    }

    public static final Creator<Mocks> CREATOR = new Creator<Mocks>() {
        @Override
        public Mocks createFromParcel(Parcel in) {
            return new Mocks(in);
        }

        @Override
        public Mocks[] newArray(int size) {
            return new Mocks[size];
        }
    };

    public Mocks() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
