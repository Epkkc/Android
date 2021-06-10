package com.elegion.test.behancer.ui.profile;

import com.elegion.data.model.user.User;
import com.elegion.test.behancer.common.BaseView;

public interface ProfileView extends BaseView {

    void bind(User user);

}
