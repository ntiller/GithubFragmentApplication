package edu.lclark.githubfragmentapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ntille on 2/23/16.
 */
public class GithubRecyclerViewAdapter extends RecyclerView.Adapter<GithubRecyclerViewAdapter.GithubViewHolder> {


    @Override
    public GithubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_account, parent, false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GithubViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
