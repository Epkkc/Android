package com.elegion.test.behancer;

import android.app.Application;

import com.elegion.test.behancer.di.AppComponent;
import com.elegion.test.behancer.di.AppModule;

import com.elegion.test.behancer.di.DaggerAppComponent;
import com.elegion.test.behancer.di.NetworkModule;
import com.elegion.test.behancer.di.ProfilePresenterComponent;
import com.elegion.test.behancer.di.ProfilePresenterModule;

/**
 * Created by Vladislav Falzan.
 */

public class AppDelegate extends Application {

    private static AppComponent sAppComponent;
    private static ProfilePresenterComponent sPPComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule()).build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static ProfilePresenterComponent getProfilePresenterComponent(){
        if (sPPComponent == null) {
            sPPComponent = sAppComponent.getProfilePresenterComponent(new ProfilePresenterModule());
        }
        return sPPComponent;
    }

    public static void clearProfilePresenterComponent(){
        sPPComponent = null;
    }


}
