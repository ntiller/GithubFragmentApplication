package edu.lclark.githubfragmentapplication;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import edu.lclark.githubfragmentapplication.models.GithubFollower;

/**
 * Created by ntille on 2/16/16.
 */
public class NetworkAsyncTask extends AsyncTask<String, Integer, ArrayList<GithubFollower>> {


    public static final String TAG = NetworkAsyncTask.class.getSimpleName();
    private final GithubListener mListener;

    public interface GithubListener {
        void onGithubFollowersRetrieved(@Nullable ArrayList<GithubFollower> followers);
    }

    public NetworkAsyncTask(GithubListener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Started AsyncTask");
    }

    @Override
    protected ArrayList<GithubFollower> doInBackground(String... params) {

        StringBuilder responseBuilder = new StringBuilder();
        ArrayList<GithubFollower> followers = null;
        if (params.length == 0) {
            return null;
        }

        String userId = params[0];

        try {
            URL url = new URL("https://api.github.com/users/" + userId + "/followers");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            if (isCancelled()) {
                return null;
            }
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);

                if (isCancelled()) {
                    return null;
                }
            }

            GithubFollower[] followerArray = new Gson().fromJson(responseBuilder.toString(), GithubFollower[].class);
            followers = new ArrayList<>();
            followers.addAll(Arrays.asList(followerArray));

            if (isCancelled()) {
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return followers;
    }


    @Override
    protected void onPostExecute(ArrayList<GithubFollower> githubFollowers) {
        super.onPostExecute(githubFollowers);

        mListener.onGithubFollowersRetrieved(githubFollowers);
    }
}
