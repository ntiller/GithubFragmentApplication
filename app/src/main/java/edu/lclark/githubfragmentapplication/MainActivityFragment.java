package edu.lclark.githubfragmentapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_main_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return rootView;
    }
}
