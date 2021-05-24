package com.example.week6hw1;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageViewMock extends Mocks implements Parcelable {
    private String title;

    public ImageViewMock(int count) {
        this.title = "Image View " + count;
    }
    public ImageViewMock(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(int count) {
        this.title = "Image View " + count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    public static final Parcelable.Creator<ImageViewMock> CREATOR = new Parcelable.Creator<ImageViewMock>(){
        @Override
        public ImageViewMock createFromParcel(Parcel source) {
            return new ImageViewMock(source.readString());
        }

        @Override
        public ImageViewMock[] newArray(int size) {
            return new ImageViewMock[0];
        }
    };

}
