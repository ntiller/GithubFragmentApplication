package edu.lclark.githubfragmentapplication;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ntille on 2/16/16.
 */
public class NetworkAsyncTask extends AsyncTask<String, Integer, JSONArray> {


    public static final String TAG = NetworkAsyncTask.class.getSimpleName();
    private final GithubListener mListener;

    public interface GithubListener {
        void onGithubAccountRetrieved(@Nullable JSONArray githubAccount);
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
    protected JSONArray doInBackground(String... params) {

        StringBuilder responseBuilder = new StringBuilder();
        JSONArray jsonArray = null;
        if (params.length == 0) {
            return null;
        }

        String userId = params[0];

        try {
            URL url = new URL("https://api.github.com/users/" + userId + "/subscriptions");
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

            jsonArray = new JSONArray(responseBuilder.toString());

            if (isCancelled()) {
                return null;
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return jsonArray;
    }

    @Override
    protected void onCancelled(JSONArray jsonArray) {
        super.onCancelled(jsonArray);
        Log.d(TAG, "AsyncTask cancelled");
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        if (jsonArray == null) {
            Log.e(TAG, "Resulting JSON is null");

            if (mListener != null) {
                mListener.onGithubAccountRetrieved(null);
            }
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject subscription = jsonArray.getJSONObject(i);
                    JSONObject owner = subscription.getJSONObject("owner");

                    Log.d(TAG, owner.getString("login") + " owns " + subscription.getString("name"));
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }
            Log.d(TAG, jsonArray.toString());

            if (mListener != null) {
                mListener.onGithubAccountRetrieved(jsonArray);
            }
        }
    }
}
