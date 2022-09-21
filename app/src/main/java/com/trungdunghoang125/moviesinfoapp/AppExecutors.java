package com.trungdunghoang125.moviesinfoapp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by trungdunghoang125 on 13/09/2022
 */
public class AppExecutors {
    private static AppExecutors instance;
    private final ScheduledExecutorService netWorkIO = Executors.newScheduledThreadPool(3);

    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    public ScheduledExecutorService getNetWorkIO() {
        return netWorkIO;
    }
}