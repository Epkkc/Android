package com.elegion.test.behancer.ui.projects;

import android.support.v7.widget.RecyclerView;


import com.elegion.data.model.project.RichProject;
import com.elegion.data.uiComponents.OnItemClickListenerInterface;
import com.elegion.test.behancer.databinding.ProjectBinding;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsHolderMVVM extends RecyclerView.ViewHolder {

    private ProjectBinding mProjectBinding;

    public ProjectsHolderMVVM(ProjectBinding binding) {
        super(binding.getRoot());
        mProjectBinding = binding;
    }

    public void bind(RichProject item, OnItemClickListenerInterface onItemClickListener) {
        mProjectBinding.setProject(new ProjectListItemViewModel(item));
        mProjectBinding.setOnItemClickListener(onItemClickListener);
        mProjectBinding.executePendingBindings();
    }
}
