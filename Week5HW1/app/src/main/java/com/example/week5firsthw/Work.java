package com.example.week5firsthw;


import java.util.concurrent.Callable;

public class Work implements Callable<Integer> {

    int floorsCount;

    public Work(int floorsCount) {
        this.floorsCount = floorsCount;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;

        for (int i = 0; i < floorsCount; i++) {
            for (int j = 0; j < 15; j++) {
                count ++;
            }
        }

        return count;
    }
}
