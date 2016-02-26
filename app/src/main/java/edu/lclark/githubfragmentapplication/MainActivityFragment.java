package edu.lclark.githubfragmentapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements NetworkAsyncTask.GithubListener {

    @Bind(R.id.fragment_main_recyclerview)
    RecyclerView mRecyclerView;

    NetworkAsyncTask mAsyncTask;
    GithubRecyclerViewAdapter mAdapter;

    ArrayList<GithubFollower> mFollowers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mAdapter = new GithubRecyclerViewAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAsyncTask == null && (mFollowers == null || mFollowers.isEmpty())) {
            mAsyncTask = new NetworkAsyncTask(this);
            mAsyncTask.execute("ntiller");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAsyncTask != null && !mAsyncTask.isCancelled()) {
            mAsyncTask.cancel(true);
            mAsyncTask = null;
        }
    }

    @Override
    public void onGithubFollowersRetrieved(@Nullable ArrayList<GithubFollower> followers) {
        mFollowers = followers;
        mAdapter.setFollowers(followers);
    }
}
