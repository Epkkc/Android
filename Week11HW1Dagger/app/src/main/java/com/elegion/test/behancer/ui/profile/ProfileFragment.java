package com.elegion.test.behancer.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Vladislav Falzan.
 */

public class ProfileFragment extends Fragment implements Refreshable, ProfileView {


    public static final String PROFILE_KEY = "PROFILE_KEY";

    @Inject
    ProfilePresenter mPresenter;

    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mProfileView;
    private String mUsername;

    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileCreatedOn;
    private TextView mProfileLocation;

    @Inject
    public ProfileFragment(){}

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDelegate.getAppComponent().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRefreshOwner = context instanceof RefreshOwner ? (RefreshOwner) context : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mErrorView = view.findViewById(R.id.errorView);
        mProfileView = view.findViewById(R.id.view_profile);
        mPresenter.setView(this);
        mProfileImage = view.findViewById(R.id.iv_profile);
        mProfileName = view.findViewById(R.id.tv_display_name_details);
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        mProfileLocation = view.findViewById(R.id.tv_location_details);
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

        mProfileView.setVisibility(View.VISIBLE);
        onRefreshData();
    }

    @Override
    public void onRefreshData() {
        mPresenter.getProfile(mUsername);
    }

    @Override
    public void bind(User user) throws IOException {
//        Picasso.with(getContext())
//                .load(user.getImage().getPhotoUrl())
//                .fit()
//                .into(mProfileImage);
        mPresenter.setPicture(getContext(), user.getImage().getPhotoUrl());
        mProfileName.setText(user.getDisplayName());
        mProfileCreatedOn.setText(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.setText(user.getLocation());
    }

    @Override
    public void setPicture(Bitmap bitmap) {
        mProfileImage.setImageBitmap(bitmap);
    }


    @Override
    public void onDetach() {
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showRefresh() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshOwner.setRefreshState(false);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mProfileView.setVisibility(View.GONE);
    }

    @Override
    public void showProfile() {
        mErrorView.setVisibility(View.GONE);
        mProfileView.setVisibility(View.VISIBLE);

    }
}
