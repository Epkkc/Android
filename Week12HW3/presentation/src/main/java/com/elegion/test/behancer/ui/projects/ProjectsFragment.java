package com.elegion.test.behancer.ui.projects;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.data.AppDelegate;
import com.elegion.data.uiComponents.OnItemClickListenerInterface;
import com.elegion.data.viewModels.ProjectsViewModel;
import com.elegion.test.behancer.databinding.ProjectsBinding;
import com.elegion.test.behancer.ui.profile.ProfileActivity;
import com.elegion.test.behancer.ui.profile.ProfileFragment;

import toothpick.Scope;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsFragment extends Fragment {

    ProjectsViewModel viewModel;

    OnItemClickListenerInterface onItemClickListener = new OnItemClickListenerInterface() {
        @Override
        public void onItemClick(String username) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            Bundle args = new Bundle();
            args.putString(ProfileFragment.PROFILE_KEY, username);
            intent.putExtra(ProfileActivity.USERNAME_KEY, args);
            startActivity(intent);
        }
    };

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        viewModel = new ProjectsViewModel();
        viewModel.setup();
        viewModel.setOnItemClickListener(onItemClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Scope scope = AppDelegate.getAppScope();

        ProjectsBinding binding = ProjectsBinding.inflate(inflater, container, false);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }


}
