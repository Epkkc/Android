package com.elegion.data;

import android.app.Application;

import com.elegion.data.di.AppModule;
import com.elegion.data.di.NetworkModule;
import com.elegion.data.di.RepositoryModule;
import com.elegion.data.di.ServiceModule;
import com.elegion.data.di.VMModel;


import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.configuration.Configuration;
import toothpick.registries.FactoryRegistryLocator;
import toothpick.registries.MemberInjectorRegistryLocator;

/**
 * Created by Vladislav Falzan.
 */

public class AppDelegate extends Application {

    private static Scope sAppScope;

    @Override
    public void onCreate() {
        super.onCreate();

        Toothpick.setConfiguration(Configuration.forProduction().disableReflection());
        MemberInjectorRegistryLocator.setRootRegistry(new com.elegion.test.behancer.MemberInjectorRegistry());
        FactoryRegistryLocator.setRootRegistry(new com.elegion.test.behancer.FactoryRegistry());

        sAppScope = Toothpick.openScope(AppDelegate.class);
        sAppScope.installModules(new AppModule(this)
                , new NetworkModule()
                , new RepositoryModule()
                , new ServiceModule()
                , new VMModel()
        );
        System.out.println("test");


    }


    public static Scope getAppScope() {
        return sAppScope;
    }
}
