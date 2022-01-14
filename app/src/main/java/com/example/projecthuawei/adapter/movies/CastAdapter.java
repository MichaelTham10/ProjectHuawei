package com.example.projecthuawei.adapter.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projecthuawei.R;
import com.example.projecthuawei.models.movies.Cast;
import com.example.projecthuawei.models.movies.Result;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder>{

    LayoutInflater inflater;
    List<Cast> casts;
    Context context;

    public CastAdapter(List<Cast> casts, Context context) {
        this.inflater =LayoutInflater.from(context);
        this.casts = casts;
        this.context = context;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View castView = inflater.inflate(R.layout.cast_view_holder, parent, false);

        return new CastViewHolder(castView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Cast cast = casts.get(position);
        holder.castName.setText(cast.getName());
        holder.castCharacter.setText(cast.getCharacter());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+cast.getProfilePath())
                .into(holder.castImage);

    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder{

        TextView castName;
        TextView castCharacter;
        ImageView castImage;
        RecyclerView.Adapter adapter;
        public CastViewHolder(@NonNull View itemView, CastAdapter adapter) {
            super(itemView);
            this.castName = itemView.findViewById(R.id.tv_cast_name);
            this.castImage = itemView.findViewById(R.id.iv_cast_image);
            this.castCharacter = itemView.findViewById(R.id.tv_cast_character);

            this.adapter = adapter;

        }
    }

}
