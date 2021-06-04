package com.elegion.test.behancer.ui.projects;

import android.support.v4.app.Fragment;

import com.elegion.test.behancer.common.SingleFragmentActivity;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsActivity extends SingleFragmentActivity {
    public static final String AUTHOR_BUNDLE = "AUTHOR_BUNDLE";

    @Override
    protected Fragment getFragment() {

        if (getIntent().getBundleExtra(AUTHOR_BUNDLE) == null) {
            return ProjectsFragment.newInstance();
        } else {
            return ProjectsFragment.newInstanceWithBundle(getIntent().getBundleExtra(AUTHOR_BUNDLE));
        }
    }
}
