package com.example.projecthuawei.view.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projecthuawei.R;
import com.example.projecthuawei.adapter.movies.ComingSoonAdapter;
import com.example.projecthuawei.adapter.movies.NowPlayingAdapter;
import com.example.projecthuawei.adapter.movies.TrendingAdapter;
import com.example.projecthuawei.models.movies.Responses;
import com.example.projecthuawei.models.movies.Result;
import com.example.projecthuawei.retrofit_client.MovieClient;
import com.example.projecthuawei.retrofit_interface.MovieInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    public static HomeFragment fragment = null;

    MovieInterface movieInterface = MovieClient.getMovieClientInstance().create(MovieInterface.class);

    String nowPlaying = "now_playing";
    String api_key = "bc438e0efb83ccfeca23d6b5cb8cc7d2";
    String lang = "en-US";
    String comingSoon = "upcoming";

    int page = 1;

    RecyclerView rv_now_playing, rv_trending, rv_coming_soon;

    NowPlayingAdapter nowPlayingAdapter;
    TrendingAdapter trendingAdapter;
    ComingSoonAdapter comingSoonAdapter;
    List<Result> movies, trending, temp, upComingMovie;

    public static HomeFragment newInstance() {
        if(fragment == null)
            fragment = new HomeFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rv_coming_soon = view.findViewById(R.id.rv_coming_soon);
        rv_trending = view.findViewById(R.id.rv_trending);
        rv_now_playing = view.findViewById(R.id.rv_now_playing);

        getNowPlaying();
        getTrending();
        getComingSoon();

        return view;
    }

    private void getComingSoon() {
        Call<Responses> comingSoonCall = movieInterface.getMovies(comingSoon, api_key, lang, page);
        comingSoonCall.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                upComingMovie = response.body().getResults();

                comingSoonAdapter = new ComingSoonAdapter(upComingMovie, getActivity());
                rv_coming_soon.setAdapter(comingSoonAdapter);
                rv_coming_soon.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {

            }
        });

    }

    private void getTrending() {
        Call<Responses> trendingCall = movieInterface.getTrending(api_key);
        trending = new ArrayList<>();
        trendingCall.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {

                temp = response.body().getResults();

                for (int i = 0; i < 3; i++) {
                    trending.add(temp.get(i));
                }

                trendingAdapter = new TrendingAdapter(trending, getActivity());
                rv_trending.setAdapter(trendingAdapter);
                rv_trending.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {

            }
        });
    }

    private void getNowPlaying() {
        Call<Responses> movieListCall = movieInterface.getMovies(nowPlaying,api_key,lang, page);

        movieListCall.enqueue(new Callback<Responses>() {


            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                movies = response.body().getResults();

                nowPlayingAdapter = new NowPlayingAdapter(movies, getActivity());
                rv_now_playing.setAdapter(nowPlayingAdapter);
                rv_now_playing.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {

            }

        });
    }
}