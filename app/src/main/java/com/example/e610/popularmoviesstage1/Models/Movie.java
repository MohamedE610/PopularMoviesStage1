package com.example.e610.popularmoviesstage1.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by E610 on 21/09/2016.
 */
public class Movie implements Parcelable {

    public boolean Favourite=false;
    private String Title;
    private String Overview ;
    private String Poster_ImageUrl;
    private String Release_Date;
    private String id;
    private String Vote_Average;



    public Movie() {

    }


    public static ArrayList<Movie> ParsingMoviesData(String JsonData)
    {
        int counter = 0;
        ArrayList<Movie> MovieList = new ArrayList<Movie>();
        try {
            JSONObject jj = new JSONObject(JsonData);
            JSONArray ja = jj.getJSONArray("results");

            while (counter < ja.length()) {
                JSONObject j = ja.getJSONObject(counter);
                Movie movie = new Movie();
                movie.setTitle(j.getString("title"));
                movie.setOverview(j.getString("overview"));
                movie.setRelease_Date(j.getString("release_date"));
                movie.setPoster_ImageUrl(j.getString("poster_path"));
                movie.setId(j.getString("id"));
                movie.setVote_average(j.getString("vote_average"));

                MovieList.add(movie);
                counter++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            return MovieList;
        }

        }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getPoster_ImageUrl() {
        return Poster_ImageUrl;
    }

    public void setPoster_ImageUrl(String poster_ImageUrl) {
        Poster_ImageUrl = poster_ImageUrl;
    }

    public String getRelease_Date() {
        return Release_Date;
    }

    public void setRelease_Date(String release_Date) {
        Release_Date = release_Date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVote_average() {
        return Vote_Average;
    }

    public void setVote_average(String vote_average) {
        this.Vote_Average = vote_average;
    }



    protected Movie(Parcel in) {
        Favourite = in.readByte() != 0x00;
        Title = in.readString();
        Overview = in.readString();
        Poster_ImageUrl = in.readString();
        Release_Date = in.readString();
        id = in.readString();
        Vote_Average = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (Favourite ? 0x01 : 0x00));
        dest.writeString(Title);
        dest.writeString(Overview);
        dest.writeString(Poster_ImageUrl);
        dest.writeString(Release_Date);
        dest.writeString(id);
        dest.writeString(Vote_Average);


    }

    @SuppressWarnings("unused")
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

