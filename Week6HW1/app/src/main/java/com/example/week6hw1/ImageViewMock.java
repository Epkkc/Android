package com.example.week6hw1;

public class ImageViewMock extends Mocks {
    private String title;
    private int count;

    public ImageViewMock(int count) {
        this.count = count;
        this.title = "Image View " + count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(int count) {
        this.count = count;
        this.title = "Image View " + count;
    }

}
