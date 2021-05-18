package com.example.week5firsthw;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {



    public static void main(String[] args) throws ExecutionException, InterruptedException {
 
        int floorsCount = 100;
        int threadsCount = 4;
        int peopleCount = 0;

        Work work1 = new Work(floorsCount/4);
        Work work2 = new Work(floorsCount/4);
        Work work3 = new Work(floorsCount/4);
        Work work4 = new Work(floorsCount/4);

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        List<Future> futures = new ArrayList<>();
        Future<Integer> future1 = executor.submit(work1);
        Future<Integer> future2 = executor.submit(work2);
        Future<Integer> future3 = executor.submit(work3);
        Future<Integer> future4 = executor.submit(work4);

        futures.add(future1);
        futures.add(future2);
        futures.add(future3);
        futures.add(future4);

        for (Future future : futures) {
            peopleCount += (int) future.get();
        }

        System.out.println(peopleCount);


    }
}
