package com.elegion.test.behancer.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.data.AppDelegate;
import com.elegion.data.viewModels.ProfileViewModel;
import com.elegion.data.uiComponents.RefreshCallbackInterface;
import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.databinding.FragmentProfileBinding;


import javax.inject.Inject;

import toothpick.Toothpick;


/**
 * Created by Vladislav Falzan.
 */

public class ProfileFragment extends Fragment implements Refreshable {

    public static final String PROFILE_KEY = "PROFILE_KEY";
    public static final String TAG = ProfileFragment.class.getSimpleName();

    private String mUsername;

    private ProfileViewModel viewModel;

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toothpick.inject(this, AppDelegate.getAppScope());
        viewModel = new ProfileViewModel();
        viewModel.setup();
        FragmentProfileBinding binding1 = FragmentProfileBinding.inflate(inflater, container, false);
        binding1.setViewModel(viewModel);
        return binding1.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (getArguments() != null) {
            mUsername = getArguments().getString(PROFILE_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle(mUsername);
        }
        viewModel.setUsername(mUsername);

        onRefreshData(null);
    }

    @Override
    public void onRefreshData(RefreshCallbackInterface callback) {
        viewModel.loadProfile(callback);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
