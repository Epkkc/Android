package com.elegion.test.behancer.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elegion.test.behancer.R;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.ui.projects.ProjectsActivity;
import com.elegion.test.behancer.ui.projects.ProjectsFragment;
import com.elegion.test.behancer.utils.ApiUtils;
import com.elegion.test.behancer.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vladislav Falzan.
 */

public class ProfileFragment extends Fragment implements Refreshable, ProfileView {

    public static final String TAG = ProfileFragment.class.getSimpleName();

    public static final String PROFILE_KEY = "PROFILE_KEY";
    private ProfilePresenter mPresenter;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mProfileView;
    private String mUsername;
    private Storage mStorage;

    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileCreatedOn;
    private TextView mProfileLocation;

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStorage = context instanceof Storage.StorageOwner ? ((Storage.StorageOwner) context).obtainStorage() : null;
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

        Button mButton = view.findViewById(R.id.btn_profile);
        mProfileImage = view.findViewById(R.id.iv_profile);
        mProfileName = view.findViewById(R.id.tv_display_name_details);
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        mProfileLocation = view.findViewById(R.id.tv_location_details);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonClick();
            }
        });
    }

    @Override
    public void startAuthorProjects() {
        Log.i(TAG, "startAuthorProjects: ");
        Intent intent = new Intent(getActivity(), ProjectsActivity.class);
        Bundle args = new Bundle();
        args.putString(ProjectsFragment.AUTHOR_KEY, mUsername);
        intent.putExtra(ProjectsActivity.AUTHOR_BUNDLE, args);
        startActivity(intent);
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
        mPresenter = new ProfilePresenter(this, mStorage);
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
        mStorage = null;
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showLoading() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideLoading() {
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
