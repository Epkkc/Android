package com.elegion.test.behancer.utils;


import android.arch.paging.PagedList;
import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.elegion.data.model.project.RichProject;
import com.elegion.data.uiComponents.OnItemClickListenerInterface;
import com.elegion.test.behancer.ui.projects.ProjectsAdapterMVVM;
import com.squareup.picasso.Picasso;

/**
 * @author Azret Magometov
 */
public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage) {
        Picasso.with(imageView.getContext()).load(urlImage).into(imageView);
    }

    @BindingAdapter({"bind:data", "bind:clickHandler"})
    public static void configureRecyclerView(RecyclerView recyclerView, PagedList<RichProject> projects, OnItemClickListenerInterface listener) {
        ProjectsAdapterMVVM adapter = new ProjectsAdapterMVVM(listener);
        adapter.submitList(projects);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"bind:refreshState", "bind:onRefresh"})
    public static void configureSwipeRefreshLayout(SwipeRefreshLayout layout, boolean isLoading, SwipeRefreshLayout.OnRefreshListener listener) {
        layout.setOnRefreshListener(listener);
        layout.post(() -> layout.setRefreshing(isLoading));
    }

    @BindingAdapter({"bind:clickListener"})
    public static void configureButton(Button button, View.OnClickListener listener){
        button.setOnClickListener(listener);
    }


}
