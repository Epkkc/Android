package com.elegion.test.behancer.ui.profile;

import android.graphics.Bitmap;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.data.model.user.User;

import java.io.IOException;

public interface ProfileView extends BaseView {
    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void showProfile();
    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void bind(User user);
    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void setPicture(Bitmap bitmap);

}
