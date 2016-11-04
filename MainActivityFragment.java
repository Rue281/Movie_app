package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    JSONObject jsonObject;
    JSONArray jArray;
    ArrayList<Movies> MovieArrayList;
    String Poster_Path; //poster url
    String movieTitle, overview, date;
    Double vote;
    Long Poster_ID;
    MovieAdapter Adapter;
    GridView gridview;



    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(Adapter);
        Adapter = new MovieAdapter(getActivity(), MovieArrayList);

        //Register a callback to be invoked when an item in this AdapterView has been clicked.
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String forecast = (String) Adapter.getItem(i);
                Movies m = MovieArrayList.get(i);
                Long Movie_ID = m.getMposter_id();
                String Poster_URL = m.getMposter_path();
                String Movie_Title = m.getMtitle();
                String Movie_Date = m.getMdate();
                String Movie_Overview = m.getMoverview();
                Double Movie_Vote = m.getMvote();

                Intent intent = new Intent(getActivity(), Details.class);
                intent.putExtra("id", Movie_ID);
                intent.putExtra("poster_path", Poster_URL);
                intent.putExtra("title", Movie_Title);
                intent.putExtra("overview", Movie_Overview);
                intent.putExtra("release_date", Movie_Date);
                intent.putExtra("vote_average", Movie_Vote);
                startActivity(intent);
            }
        });
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        new FetchMovieData(getActivity()).execute();//lazm aktb l strings 2lli btro7 ll do in bkground ()
    }

    public class FetchMovieData extends AsyncTask<String, Void, ArrayList<Movies>> {
        private Context context;

        public FetchMovieData(Context context) {
            this.context = context;
        }



        @Override
        protected ArrayList<Movies> doInBackground(String... params) {
            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            /*Will contain the raw JSON response as a string.
            String movieJsonStr = null;*/
            try {
                final String mostPopularURL = "https://api.themoviedb.org/3/movie/popular?api_key=4843589caabdf86860e4e0a054175d02";
                final String highestRatedURL = "https://api.themoviedb.org/3/movie/top_rated?api_key=4843589caabdf86860e4e0a054175d02";
                URL url = new URL(mostPopularURL); //to read more than 1 URL

                //create the request & open connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");//method used for the server GET DATA
                urlConnection.connect();

                //read input stream into string
                InputStream inputStream = urlConnection.getInputStream(); //can't read line by line so we should use bufferReader
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));//data of type JSON

                String strReader;
                while ((strReader = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(strReader + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //movieJsonStr = buffer.toString();

                jsonObject = new JSONObject(strReader);
                jArray = jsonObject.getJSONArray("results");
                MovieArrayList = new ArrayList<>();
                //Array named result {from json formatter}
                //3shan 2wsl ll obejects gowa l array
                for (int i = 0; i < jArray.length(); i++) {
                    jsonObject = jArray.getJSONObject(i);
                    //3shn a-access kol 7aga gowa l object poster path
                    Poster_Path = jsonObject.getString("poster_path");
                    Poster_ID = jsonObject.getLong("id");
                    movieTitle = jsonObject.getString("title");
                    overview = jsonObject.getString("overview");
                    date = jsonObject.getString("release_date");
                    vote = jsonObject.getDouble("vote_average");

                    //to get poster path & poster id and put them in json object
                    Movies movieObj = new Movies(Poster_Path, Poster_ID, movieTitle, overview, date, vote);
                    movieObj.setMposter_path(Poster_Path);
                    movieObj.setMposter_id(Poster_ID);
                    movieObj.setMdate(date);
                    movieObj.setMoverview(overview);
                    movieObj.setMtitle(movieTitle);
                    movieObj.setMvote(vote);
                    MovieArrayList.add(movieObj);
                }
                inputStream.close();
                return null;
            } catch (ProtocolException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {///////////////??
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            return MovieArrayList;
        }
        @Override
        protected void onPostExecute(final ArrayList<Movies> MovieArrayList) {

           super.onPostExecute(MovieArrayList);
           /* Adapter = new MovieAdapter(getActivity(), MovieArrayList);
            gridview.setAdapter(Adapter);*/
            /*
            *how to add in the adapter*/

            //Register a callback to be invoked when an item in this AdapterView has been clicked.
            /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Movies m = MovieArrayList.get(i);
                    Long Movie_ID = m.getMposter_id();
                    String Poster_URL=m.getMposter_path();
                    String Movie_Title=m.getMtitle();
                    String Movie_Date=m.getMdate();
                    String Movie_Overview=m.getMoverview();
                    Double Movie_Vote=m.getMvote();

                    Intent intent = new Intent(getActivity(),Details.class);
                    intent.putExtra("id", Movie_ID);
                    intent.putExtra("poster_path",Poster_URL);
                    intent.putExtra("title",Movie_Title);
                    intent.putExtra("overview",Movie_Overview);
                    intent.putExtra("release_date",Movie_Date);
                    intent.putExtra("vote_average",Movie_Vote);
                    startActivity(intent);*/
                }
            }
        }
