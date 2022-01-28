package com.example.projecthuawei.view.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.example.projecthuawei.R;
import com.example.projecthuawei.adapter.movies.ComingSoonAdapter;
import com.example.projecthuawei.adapter.movies.NowPlayingAdapter;
import com.example.projecthuawei.adapter.movies.SearchAdapter;
import com.example.projecthuawei.adapter.movies.TrendingAdapter;
import com.example.projecthuawei.models.movies.Responses;
import com.example.projecthuawei.models.movies.Result;
import com.example.projecthuawei.models.movies.SearchPage;
import com.example.projecthuawei.models.movies.SearchResult;
import com.example.projecthuawei.retrofit_client.MovieClient;
import com.example.projecthuawei.retrofit_interface.MovieInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    public static SearchFragment fragment = null;
    MovieInterface movieInterface = MovieClient.getMovieClientInstance().create(MovieInterface.class);

    String api_key = "bc438e0efb83ccfeca23d6b5cb8cc7d2";
    private String stringTemp = "";
    int page = 1;
    boolean include_adult = false;
    String lang = "en-US";

    RecyclerView rv_search;
    SearchAdapter searchAdapter;
    List<SearchResult> search, temp;
    SearchView searchView;


    public static SearchFragment newInstance() {
        if(fragment == null)
            fragment = new SearchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rv_search = view.findViewById(R.id.rv_search);
        searchView = view.findViewById(R.id.search_box);




        searchView.setOnQueryTextListener(this);


        return view;
    }

    private void getResult(String text) {
        if(text == ""){
            text = "A";
        }
        Call<SearchPage> searchCall = movieInterface.getSearchResult(api_key, lang, text, page, include_adult);
        search = new ArrayList<>();

        searchCall.enqueue(new Callback<SearchPage>() {
            @Override
            public void onResponse(Call<SearchPage> call, Response<SearchPage> response) {
                Log.d("testing_search", "onResponse: " + response.errorBody());
                if(response.body().getResults() == null){
                    return;
                }
                temp = response.body().getResults();

                for (int i = 0; i < temp.size(); i++) {
                    search.add(temp.get(i));
                }

                searchAdapter = new SearchAdapter(search, getActivity());
                rv_search.setAdapter(searchAdapter);
                rv_search.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<SearchPage> call, Throwable t) {

                Log.e("testing_search", "onFail: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if(s.isEmpty()){
            getResult(stringTemp);
        }else{
            stringTemp = s;
            getResult(s);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {


        return true;
    }
}