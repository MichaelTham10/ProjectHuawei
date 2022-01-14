package com.example.projecthuawei.adapter.movies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projecthuawei.R;
import com.example.projecthuawei.SplashActivity;
import com.example.projecthuawei.models.movies.Result;
import com.example.projecthuawei.view.auth.LoginActivity;
import com.example.projecthuawei.view.main.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>{

    LayoutInflater inflater;
    List<Result> results;
    Context context;

    public NowPlayingAdapter(List<Result> results, Context context) {
        this.inflater =LayoutInflater.from(context);
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public NowPlayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View nowPlayingView = inflater.inflate(R.layout.now_playing_view_holder, parent, false);

        return new NowPlayingViewHolder(nowPlayingView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingViewHolder holder, int position) {
        Result result = results.get(position);
        holder.movieTitle.setText(result.getTitle());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+result.getPosterPath())
                .into(holder.movieImage);
        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie_id", result.getId());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class NowPlayingViewHolder extends RecyclerView.ViewHolder{

        TextView movieTitle;
        ImageView movieImage;
        Button buyBtn;
        RecyclerView.Adapter adapter;
        public NowPlayingViewHolder(@NonNull View itemView, NowPlayingAdapter adapter) {
            super(itemView);
            this.movieImage = itemView.findViewById(R.id.iv_now_movie);
            this.movieTitle = itemView.findViewById(R.id.tv_now_title);

            this.adapter = adapter;

        }
    }

}
