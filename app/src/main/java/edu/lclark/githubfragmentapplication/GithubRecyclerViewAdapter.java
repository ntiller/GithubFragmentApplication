package edu.lclark.githubfragmentapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ntille on 2/23/16.
 */
public class GithubRecyclerViewAdapter extends RecyclerView.Adapter<GithubRecyclerViewAdapter.GithubViewHolder> {

    private ArrayList<GithubFollower> mFollowers;

    public GithubRecyclerViewAdapter(ArrayList<GithubFollower> followers) {
        mFollowers = followers;
    }

    @Override
    public GithubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_account, parent, false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GithubViewHolder holder, int position) {
        GithubFollower follower = mFollowers.get(position);
        holder.textView.setText(follower.getLogin());

        Picasso.with(holder.imageView.getContext())
                .load(follower.getAvatar_url())
                .fit()
                .centerInside()
                .into(holder.imageView);
    }

    public void setFollowers(ArrayList<GithubFollower> followers) {
        mFollowers = followers;
        notifyItemRangeInserted(0, mFollowers.size());
    }

    @Override
    public int getItemCount() {
        return mFollowers == null ? 0 : mFollowers.size();
    }

    static class GithubViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.row_account_imageview)
        ImageView imageView;

        @Bind(R.id.row_account_textview)
        TextView textView;


        public GithubViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}
