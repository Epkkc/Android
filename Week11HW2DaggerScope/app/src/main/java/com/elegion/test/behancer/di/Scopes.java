package com.elegion.test.behancer.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

public class Scopes {

    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ProfileScope{}

}
