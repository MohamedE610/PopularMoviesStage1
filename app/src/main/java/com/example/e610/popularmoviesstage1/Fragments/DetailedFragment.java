package com.example.e610.popularmoviesstage1.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e610.popularmoviesstage1.Models.Movie;
import com.example.e610.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;


/**
 * Created by E610 on 21/09/2016.
 */
public class DetailedFragment extends Fragment {

    final public  String imgString= "http://image.tmdb.org/t/p/w185/";


    ImageView Poster_Img;
    TextView Title;
    TextView Overview;
    TextView ReleaseDate;
    TextView Vote_Rating;
    TextView ReviewAuthor;
    TextView ReviewContent;
    TextView TrailerName;
    ImageView Favourite;
    ImageView button;


    Bundle MoviesInfo ;
    Movie movie ;

    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=  inflater.inflate(R.layout.detailed_fragment,container,false);
        view=v;
        MoviesInfo=new Bundle();
        movie=new Movie();



        Poster_Img=(ImageView)v.findViewById(R.id.Poster_Image);
        Title=(TextView)v.findViewById(R.id.Title);
        ReleaseDate=(TextView)v.findViewById(R.id.Release_Date);
        Overview=(TextView)v.findViewById(R.id.Overview);
        Vote_Rating=(TextView)v.findViewById(R.id.Vote_Rating);
        ReviewAuthor=(TextView)v.findViewById(R.id.ReviewAuthor);
        ReviewContent=(TextView)v.findViewById(R.id.ReviewContent);
        TrailerName=(TextView)v.findViewById(R.id.TrailerName);
        Favourite=(ImageView)v.findViewById(R.id.Favourite);
        button=(ImageView)v.findViewById(R.id.button);

        MoviesInfo=this.getArguments();

        if(savedInstanceState!=null){
            movie= savedInstanceState.getParcelable("Movie");
        }
        else{
            movie=MoviesInfo.getParcelable("Movie");
         }

        setMovieDetails();

        return v;
    }


    public void setMovieDetails(){

        Picasso.with(view.getContext()).load(imgString+ movie.getPoster_ImageUrl())
                .placeholder(R.drawable.loadingicon)
                .error(R.drawable.error).into(Poster_Img);

        Title.setText( movie.getTitle());
        Overview.setText(movie.getOverview());
        ReleaseDate.setText(movie.getRelease_Date());
        Vote_Rating.setText(movie.getVote_average()+"/10");
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable("Movie",movie);
        super.onSaveInstanceState(outState);
    }

}
