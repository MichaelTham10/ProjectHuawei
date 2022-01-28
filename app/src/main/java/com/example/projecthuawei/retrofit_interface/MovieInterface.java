package com.example.projecthuawei.retrofit_interface;

import com.example.projecthuawei.models.movies.Credits;
import com.example.projecthuawei.models.movies.MovieDetail;
import com.example.projecthuawei.models.movies.MovieVideos;
import com.example.projecthuawei.models.movies.Responses;
import com.example.projecthuawei.models.movies.SearchPage;
import com.example.projecthuawei.models.movies.VideoTrailer;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {
    @GET("/3/movie/{category}")
    Call<Responses> getMovies(
        @Path("category") String category,
        @Query("api_key") String api_key,
        @Query("language") String language,
        @Query("page") int page
    );

    @GET("/3/trending/movie/week")
    Call<Responses> getTrending(
            @Query("api_key") String api_key
    );

    @GET("/3/movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("/3/movie/{movie_id}/videos")
    Call<MovieVideos> getMovieVideos(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("/3/movie/{movie_id}/credits")
    Call<Credits> getMovieCredits(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("/3/movie/{movie_id}/videos")
    Call<VideoTrailer> getMovieTrailer(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("/3/search/movie")
    Call<SearchPage> getSearchResult(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") boolean include_adult
    );








}
