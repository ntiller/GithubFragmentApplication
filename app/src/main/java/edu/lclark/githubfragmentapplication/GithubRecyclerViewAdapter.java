package edu.lclark.githubfragmentapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.models.GithubUser;

/**
 * Created by ntille on 2/23/16.
 */
public class GithubRecyclerViewAdapter extends RecyclerView.Adapter<GithubRecyclerViewAdapter.GithubViewHolder> {

    private RowClickListener mListener;
    private ArrayList<GithubUser> mFollowers;

    public interface RowClickListener {
        void onRowClicked(int position);
    }

    public GithubRecyclerViewAdapter(ArrayList<GithubUser> followers, RowClickListener listener) {
        mFollowers = followers;
        mListener = listener;
    }

    @Override
    public GithubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_account, parent, false);
        Log.d("GHRecyclerViewAdapter", "In onCreateViewHolder");
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GithubViewHolder holder, int position) {
        GithubUser follower = mFollowers.get(position);
        holder.textView.setText(follower.getLogin());

        Picasso.with(holder.imageView.getContext())
                .load(follower.getAvatar_url())
                .fit()
                .centerInside()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRowClicked(holder.getAdapterPosition());
            }
        });
    }

    public void setFollowers(ArrayList<GithubUser> followers) {
        mFollowers = followers;
        notifyItemRangeInserted(0, mFollowers.size());
    }

    public GithubUser getItem(int position) {
        return mFollowers == null ? null : mFollowers.get(position);
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
