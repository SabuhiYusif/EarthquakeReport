/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<EarthQuake>>{


    private TextView mTextView;
    private ProgressBar mProgressBar;
    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();
    private CustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
//        ArrayList<EarthQuake> earthquakes = QueryUtils.extractEarthquakes();
//        earthquakes = QueryUtils.fetchEarthquakeData("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10");
//

        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new CustomAdapter(this,R.layout.list_item,new ArrayList<EarthQuake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"Clicked", Toast.LENGTH_SHORT).show();
                EarthQuake currentEarthQuake = mAdapter.getItem(position);


                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentEarthQuake.getUrl()));
                startActivity(intent);

            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTextView = (TextView) findViewById(R.id.empity_list) ;
        earthquakeListView.setEmptyView(mTextView);


        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            Log.d(LOG_TAG, "initLoader Called.. " );


            getLoaderManager().initLoader(1,null,this).forceLoad();

        }else{
            mProgressBar.setVisibility(View.GONE);
            mTextView.setText("There is no internet connection");
        }

//        EarthquakeLoader asyncTask = new EarthquakeLoader(this,);
//

    }


    private static   class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuake>> {
        private String mUrl;

        public EarthquakeLoader(Context context, String url){
            super(context);

            this.mUrl = url;

        }

        @Override
        public List<EarthQuake> loadInBackground() {
            Log.d(LOG_TAG,"" + " loadInBackground called ..");
            List<EarthQuake> list = QueryUtils.fetchEarthquakeData(mUrl);
            return list;
        }

//        @Override
//        protected List<EarthQuake> doInBackground(String... params) {
//            if(params.length< 1 || params[0] == null){
//                return null;
//            }
//            return QueryUtils.fetchEarthquakeData(params[0]);
//
//        }

//        @Override
//        protected void onPostExecute(List<EarthQuake> data) {
//            // Clear the adapter of previous earthquake data
//            mAdapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (data != null && !data.isEmpty()) {
//                mAdapter.addAll(data);
//            }
//        }
    }

    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG,"onCreateLoader " + id);

        return new EarthquakeLoader(this,"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=15");
    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuake>> loader, List<EarthQuake> data) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.clear();

        mAdapter.addAll(data);
        Log.d(LOG_TAG,"onLOad Finished " + loader);

        mTextView.setText("There is no info");
    }

    @Override
    public void onLoaderReset(Loader<List<EarthQuake>> loader) {
        mAdapter.clear();
        Log.d(LOG_TAG,"onLOaderReset " + loader);
    }
}
