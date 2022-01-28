package com.example.projecthuawei.adapter.movies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projecthuawei.R;
import com.example.projecthuawei.models.movies.SearchResult;
import com.example.projecthuawei.view.main.DetailActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    LayoutInflater inflater;
    List<SearchResult> results;
    Context context;
//    ArrayList<SearchResult> arraylist;

    public SearchAdapter(List<SearchResult> results, Context context) {
        this.inflater =LayoutInflater.from(context);
        this.results = results;
        this.context = context;
//        this.arraylist = new ArrayList<SearchResult>();
//        arraylist.addAll(results);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View nowPlayingView = inflater.inflate(R.layout.search_holder, parent, false);

        return new SearchViewHolder(nowPlayingView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchResult result = results.get(position);
        holder.movieTitle.setText(result.getTitle());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+result.getPosterPath())
                .into(holder.movieImage);
        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView movieTitle;
        ImageView movieImage;

        RecyclerView.Adapter adapter;
        public SearchViewHolder(@NonNull View itemView, SearchAdapter adapter) {
            super(itemView);
            this.movieImage = itemView.findViewById(R.id.iv_search_result);
            this.movieTitle = itemView.findViewById(R.id.tv_search_result);
            this.adapter = adapter;

        }
    }
}
