package com.example.week6hw1;

public class TextViewMock extends Mocks {
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
}
