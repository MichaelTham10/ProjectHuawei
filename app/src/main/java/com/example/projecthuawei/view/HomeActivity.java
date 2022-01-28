package com.example.projecthuawei.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.projecthuawei.R;
import com.example.projecthuawei.adapter.movies.ComingSoonAdapter;
import com.example.projecthuawei.adapter.movies.NowPlayingAdapter;
import com.example.projecthuawei.adapter.movies.TrendingAdapter;
import com.example.projecthuawei.models.movies.Responses;
import com.example.projecthuawei.models.movies.Result;
import com.example.projecthuawei.retrofit_client.MovieClient;
import com.example.projecthuawei.retrofit_interface.MovieInterface;
import com.example.projecthuawei.view.main.HomeFragment;
import com.example.projecthuawei.view.main.SearchFragment;
import com.example.projecthuawei.view.main.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    UserFragment userFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        homeFragment = HomeFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        userFragment = UserFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, HomeFragment.class, null)
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    Fragment f = null;
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                        switch (item.getItemId()){
                            case R.id.menu_home:
                                f = homeFragment;
                                break;
                            case R.id.menu_search:
                                f = searchFragment;
                                break;
                            case R.id.menu_profile:
                                f = userFragment;

                                break;
                        }

                        // change the fragment depending on selected nav button
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment_container, f, null)
                                .commit();
                        return true;
                    }
                }
        );

    }


}