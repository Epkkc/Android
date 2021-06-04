package com.elegion.test.behancer.ui.profile;

import android.graphics.Bitmap;

import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.data.model.user.User;

import java.io.IOException;

public interface ProfileView extends BaseView {

    void showProfile();

    void bind(User user) throws IOException;

    void setPicture(Bitmap bitmap);

    void startAuthorProjects();

}
