package com.example.projecthuawei.view.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.projecthuawei.R;
import com.example.projecthuawei.adapter.movies.CastAdapter;
import com.example.projecthuawei.models.movies.Cast;
import com.example.projecthuawei.models.movies.Credits;
import com.example.projecthuawei.models.movies.MovieDetail;
import com.example.projecthuawei.models.movies.VideoTrailer;
import com.example.projecthuawei.retrofit_client.MovieClient;
import com.example.projecthuawei.retrofit_interface.MovieInterface;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity
{
    MovieInterface movieInterface = MovieClient.getMovieClientInstance().create(MovieInterface.class);

    String api_key = "bc438e0efb83ccfeca23d6b5cb8cc7d2";

    String videoPath = "";
    String movie_id;
    TextView movieTitle;
    ImageView movieImage;
    TextView movieOverview;
    TextView movieRating;
    TextView movieVote;
    RecyclerView rv_cast;
    List<Cast> temp, casts;
    CastAdapter castAdapter;

    YouTubePlayerView movieView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movie_id = Integer.toString(getIntent().getIntExtra("movie_id",0)) ;
        movieTitle = findViewById(R.id.tv_title_detail);
        movieImage = findViewById(R.id.iv_image_detail);
        movieOverview = findViewById(R.id.tv_desc_detail);
        movieRating = findViewById(R.id.tv_rating_detail);
        movieVote = findViewById(R.id.tv_vote_count_detail);
        rv_cast = findViewById(R.id.rv_cast);

        movieView = findViewById(R.id.movie_trailer);

        getLifecycle().addObserver(movieView);
        getMovieDetail();
        getMovieCast();
        getVideoTrailer();
        movieView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo(videoPath, 0);
                youTubePlayer.play();
            }
        });


    }

    private void getVideoTrailer() {

        Call<VideoTrailer> videoTrailerCall = movieInterface.getMovieTrailer(movie_id, api_key);
        videoTrailerCall.enqueue(new Callback<VideoTrailer>() {
            @Override
            public void onResponse(Call<VideoTrailer> call, Response<VideoTrailer> response) {

                for (int i = 0; i < response.body().getResults().size(); i++) {
                    if(response.body().getResults().get(i).getType().equals("Trailer")){
                        videoPath = response.body().getResults().get(i).getKey();
                        Log.d("api_response", "Api Response : " + response.body().getResults().get(i).getName());
                        break;
                    }

                }

            }

            @Override
            public void onFailure(Call<VideoTrailer> call, Throwable t) {

            }
        });



    }

    private void getMovieCast() {
        Call<Credits> creditsCall = movieInterface.getMovieCredits(movie_id, api_key);
        casts = new ArrayList<>();
        temp = new ArrayList<>();
        creditsCall.enqueue(new Callback<Credits>() {

            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {

                temp = response.body().getCast();

                for (int i = 0 ; i < 10 ; i++){
                    casts.add(temp.get(i));
                }

                castAdapter = new CastAdapter(casts, DetailActivity.this);
                rv_cast.setAdapter(castAdapter);
                rv_cast.setLayoutManager(new LinearLayoutManager(DetailActivity.this,LinearLayoutManager.HORIZONTAL, false));

            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {

            }
        });
    }

    private void getMovieDetail(){
        Call<MovieDetail> movieDetailCall = movieInterface.getMovieDetail(movie_id,api_key);
        movieDetailCall.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {

                movieTitle.setText(response.body().getOriginalTitle());
                Glide.with(DetailActivity.this)
                        .load("https://image.tmdb.org/t/p/w185"+response.body().getPosterPath())
                        .into(movieImage);
                movieRating.setText(Double.toString(response.body().getVoteAverage()));
                movieVote.setText("(" + Integer.toString(response.body().getVoteCount()) + ")");
                movieOverview.setText(response.body().getOverview());
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {

            }
        });
    }

}