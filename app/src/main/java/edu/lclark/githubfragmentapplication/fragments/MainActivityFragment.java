package edu.lclark.githubfragmentapplication.fragments;

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
import edu.lclark.githubfragmentapplication.GithubRecyclerViewAdapter;
import edu.lclark.githubfragmentapplication.NetworkAsyncTask;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.activities.MainActivity;
import edu.lclark.githubfragmentapplication.models.GithubUser;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements NetworkAsyncTask.GithubListener,
        GithubRecyclerViewAdapter.RowClickListener {

    public static final String ARG_USER = "MainActivityFragment.User";

    @Bind(R.id.fragment_main_recyclerview)
    RecyclerView mRecyclerView;

    NetworkAsyncTask mAsyncTask;
    GithubRecyclerViewAdapter mAdapter;

    ArrayList<GithubUser> mFollowers;
    private FollowerSelectedListener mListener;

    private String mUserLogin = "ntiller";



    public interface FollowerSelectedListener {
        void onFollowerSelected(GithubUser follower);
    }

    public static MainActivityFragment newInstance(GithubUser user) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            GithubUser user = getArguments().getParcelable(ARG_USER);
            if (user != null) {
                mUserLogin = user.getLogin();
            }
        }


        getActivity().setTitle(getString(R.string.follower_list_title, mUserLogin));
        mAdapter = new GithubRecyclerViewAdapter(mFollowers, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mListener = (MainActivity) getActivity();


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAsyncTask == null && (mFollowers == null || mFollowers.isEmpty())) {
            mAsyncTask = new NetworkAsyncTask(this);
            mAsyncTask.execute(mUserLogin);
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
    public void onGithubFollowersRetrieved(@Nullable ArrayList<GithubUser> followers) {
        mFollowers = followers;
        mAdapter.setFollowers(followers);
    }


    @Override
    public void onRowClicked(int position) {
        mListener.onFollowerSelected(mAdapter.getItem(position));
    }
}
