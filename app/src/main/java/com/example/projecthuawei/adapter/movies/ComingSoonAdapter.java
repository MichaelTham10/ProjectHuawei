package com.example.projecthuawei.adapter.movies;

import android.content.Context;
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
import com.example.projecthuawei.models.movies.Result;

import java.util.List;

public class ComingSoonAdapter extends RecyclerView.Adapter<ComingSoonAdapter.ComingSoonViewHolder>{

    LayoutInflater inflater;
    List<Result> results;
    Context context;

    public ComingSoonAdapter(List<Result> results, Context context) {
        this.inflater =LayoutInflater.from(context);
        this.results = results;
        this.context = context;
    }

    @NonNull
    @Override
    public ComingSoonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View nowPlayingView = inflater.inflate(R.layout.coming_soon_view_holder, parent, false);

        return new ComingSoonViewHolder(nowPlayingView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ComingSoonViewHolder holder, int position) {
        Result result = results.get(position);
        holder.movieTitle.setText(result.getTitle());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+result.getPosterPath())
                .into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ComingSoonViewHolder extends RecyclerView.ViewHolder{

        TextView movieTitle;
        ImageView movieImage;
        RecyclerView.Adapter adapter;
        public ComingSoonViewHolder(@NonNull View itemView, ComingSoonAdapter adapter) {
            super(itemView);
            this.movieImage = itemView.findViewById(R.id.iv_coming_movie);
            this.movieTitle = itemView.findViewById(R.id.tv_coming_title);

            this.adapter = adapter;

        }
    }

}
