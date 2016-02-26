package edu.lclark.githubfragmentapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.models.GithubFollower;

/**
 * Created by ntille on 2/25/16.
 */
public class FollowerFragment extends Fragment {

    public static final String ARG_FOLLOWER = "FollowerFragment.Follower";
    private GithubFollower mFollower;

    @Bind(R.id.fragment_follower_imageview)
    ImageView mImageView;

    @Bind(R.id.fragment_follower_name_textview)
    TextView mNameTextView;

    public static FollowerFragment newInstance(GithubFollower follower) {
        FollowerFragment fragment = new FollowerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FOLLOWER, follower);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_follower, container, false);

        ButterKnife.bind(this, rootView);

        mFollower = getArguments().getParcelable(ARG_FOLLOWER);

        Picasso.with(getContext()).load(mFollower.getAvatar_url()).fit().centerInside().into(mImageView);

        mNameTextView.setText(mFollower.getLogin());

        return rootView;
    }
}
