package com.example.week6hw1;

import android.os.Parcel;
import android.os.Parcelable;

public class TextViewMock extends Mocks implements Parcelable {
    private String subText;

    public TextViewMock(int subText) {
        this.subText = String.valueOf(subText);
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subText);
    }

    public static final Parcelable.Creator<TextViewMock> CREATOR = new Parcelable.Creator<TextViewMock>(){
        @Override
        public TextViewMock createFromParcel(Parcel source) {
            return new TextViewMock(Integer.parseInt(source.readString()));
        }

        @Override
        public TextViewMock[] newArray(int size) {
            return new TextViewMock[0];
        }
    };







}
