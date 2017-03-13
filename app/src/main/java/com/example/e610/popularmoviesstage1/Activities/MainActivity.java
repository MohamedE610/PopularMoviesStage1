package com.example.e610.popularmoviesstage1.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e610.popularmoviesstage1.Adapters.MovieAdapter;
import com.example.e610.popularmoviesstage1.Fragments.MainFragment;
import com.example.e610.popularmoviesstage1.Models.Movie;
import com.example.e610.popularmoviesstage1.R;
import com.example.e610.popularmoviesstage1.Utils.FetchData;
import com.example.e610.popularmoviesstage1.Utils.NetworkResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainFragment.SendToMainActivity {

    boolean InstanceState;

    MainFragment mainFragment;
    Spinner spinner;
    Activity CurrentActivity;
    ArrayList<Movie> Movies;
    static boolean checkFrag=false;
    MovieAdapter movieAdapter;
    Bundle MoviesInfo;
    Context context;
    boolean IsTablet;
    RecyclerView MoviesRecyclerView;
    View view;
    public static Activity ctx; // for using it inside NetworkState()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CurrentActivity=this;
        ctx =this;
        movieAdapter=new MovieAdapter();
        mainFragment=  new MainFragment();
        MoviesInfo = new Bundle();



    if(savedInstanceState==null){
        getFragmentManager().beginTransaction().add(R.id.MainFragment, mainFragment).commit();
        InstanceState = false;
       }
     else{
          mainFragment=(MainFragment)getFragmentManager().findFragmentById(R.id.MainFragment);
          InstanceState = true;
          Movies=savedInstanceState.getParcelableArrayList("Movies");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Movies",Movies);
        super.onSaveInstanceState(outState);
    }
//  I used this method to pass from MainFragment data to  MainActivity (this data is view of MainFragment)
    @Override
    public void send(View v) {
        view=v;
        context=view.getContext();
        MoviesRecyclerView=(RecyclerView) view.findViewById(R.id.MoviesRecyclerView);
        MoviesRecyclerView.setLayoutManager(new GridLayoutManager(context,2));

        spinner=(Spinner) view.findViewById(R.id.spinner);
        String [] SpinnerData= {"Popular Movies","Top Rated Movies"};
        ArrayAdapter<String>SpinnerAdapter=new ArrayAdapter<String>(context,R.layout.spinner_item,SpinnerData);
        spinner.setAdapter(SpinnerAdapter);


        DisplayMovies();
    }


    public void DisplayMovies(){
            collectData("Popular Movies");
        spinner.setSelection(0); // 0 index of "Popular Movies" in a Spinner also I can get it by  SpinnerAdapter.getPosition("Popular Movies");
        }



// this method for fetch  movies data
    public void collectData(String Key){

        if(MainActivity.NetworkState()) {
            FetchData fetchData = new FetchData(Key, "");
            ClickEvent();
            fetchData.execute();

            fetchData.setNetworkResponse(new NetworkResponse() {

                @Override
                public void OnSuccess(String JsonData) {

                    Movies = Movie.ParsingMoviesData(JsonData);
                    movieAdapter = new MovieAdapter(Movies, context);
                    MoviesRecyclerView.setAdapter(movieAdapter);

                    if(JsonData==null)
                        Toast.makeText(MainActivity.ctx," No Internet Connection", Toast.LENGTH_SHORT).show();
                    ClickEvent();
                }

                @Override
                public void OnUpdate(boolean IsDataReceived) {

                }
            });
        }
        else{
            Movies = new ArrayList<>();
            movieAdapter = new MovieAdapter(Movies, context);
            MoviesRecyclerView.setAdapter(movieAdapter);
            Toast.makeText(this," No Internet Connection", Toast.LENGTH_SHORT).show();
            ClickEvent();
        }
    }




    public void ClickEvent(){
        movieAdapter.setClickListener(new MovieAdapter.RecyclerViewClickListener() {
            @Override
            public void ItemClicked(View v, int position) {

                Movie movie=new Movie();
                movie=Movies.get(position);
                MoviesInfo.putParcelable("Movie",movie);

                    Intent in = new Intent( CurrentActivity , DetailedActivity.class);
                    in.putExtra("MoviesInfo", MoviesInfo);
                    startActivity(in);

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(view!=null) {
                    checkFrag=false;
                    TextView textView = (TextView) view;
                    String SpinnerKey = textView.getText() + "";
                    if (SpinnerKey.equals("Popular Movies")){
                        collectData("Popular Movies");
                    }

                    else if (SpinnerKey.equals("Top Rated Movies")){
                        collectData("Top Rated Movies");
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
//this method for check if internet is available or not
    public static boolean NetworkState()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
