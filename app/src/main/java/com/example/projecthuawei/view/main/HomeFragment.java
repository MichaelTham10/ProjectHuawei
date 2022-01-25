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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.projecthuawei.R;
import com.example.projecthuawei.adapter.movies.ComingSoonAdapter;
import com.example.projecthuawei.adapter.movies.NowPlayingAdapter;
import com.example.projecthuawei.adapter.movies.TrendingAdapter;
import com.example.projecthuawei.models.movies.Responses;
import com.example.projecthuawei.models.movies.Result;
import com.example.projecthuawei.retrofit_client.MovieClient;
import com.example.projecthuawei.retrofit_interface.MovieInterface;
import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    FrameLayout adFrameLayout;

    public static HomeFragment newInstance() {
        if(fragment == null)
            fragment = new HomeFragment();

        return fragment;
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    private AdListener adListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            // Called when an ad is loaded successfully.
            showToast("Ad loaded.");
        }

        @Override
        public void onAdFailed(int errorCode) {
            // Called when an ad fails to be loaded.
            showToast(String.format(Locale.ROOT, "Ad failed to load with error code %d.", errorCode));
        }

        @Override
        public void onAdOpened() {
            // Called when an ad is opened.
            showToast(String.format("Ad opened "));
        }

        @Override
        public void onAdClicked() {
            // Called when a user taps an ad.
            showToast("Ad clicked");
        }

        @Override
        public void onAdLeave() {
            // Called when a user has left the app.
            showToast("Ad Leave");
        }

        @Override
        public void onAdClosed() {
            // Called when an ad is closed.
            showToast("Ad closed");
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HwAds.init(getContext());

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        adFrameLayout = view.findViewById(R.id.ad_frame);

        BannerView defaultBannerView = view.findViewById(R.id.hw_banner_view);


        defaultBannerView.setAdListener(adListener);
        defaultBannerView.setBannerRefresh(60);

        AdParam adParam = new AdParam.Builder().build();
        defaultBannerView.loadAd(adParam);

        BannerView bannerView = new BannerView(getContext());
        // Set the ad unit ID and ad dimensions. "testw6vs28auh3" is a dedicated test ad unit ID.
        bannerView.setAdId("testw6vs28auh3");
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_SMART);
        // Set the refresh interval to 30 seconds.
        bannerView.setBannerRefresh(60);
        // Create an ad request to load an ad.
        bannerView.setAdListener(adListener);
        bannerView.loadAd(new AdParam.Builder().build());

        adFrameLayout.addView(bannerView);



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