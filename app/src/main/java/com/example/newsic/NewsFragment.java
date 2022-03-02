package com.example.newsic;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.newsic.News.NewsApiResponse;
import com.example.newsic.News.NewsHeadlines;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    RecyclerViewAdapterNews recyclerViewAdapterNews;
    ProgressDialog progressDialog;
    Button btnBusiness, btnEntertainment, btnHealth, btnScience, btnGeneral, btnSports, btnTech;
    SearchView searchNews;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewNews);
        searchNews = v.findViewById(R.id.searchNews);
        btnBusiness = v.findViewById(R.id.btnBusiness);
        btnBusiness.setOnClickListener(this);
        btnEntertainment = v.findViewById(R.id.btnEntertainment);
        btnEntertainment.setOnClickListener(this);
        btnHealth = v.findViewById(R.id.btnHealth);
        btnHealth.setOnClickListener(this);
        btnGeneral = v.findViewById(R.id.btnGeneral);
        btnGeneral.setOnClickListener(this);
        btnSports = v.findViewById(R.id.btnSports);
        btnSports.setOnClickListener(this);
        btnScience = v.findViewById(R.id.btnScience);
        btnScience.setOnClickListener(this);
        btnTech = v.findViewById(R.id.btnTechnology);
        btnTech.setOnClickListener(this);
        NewsRequestManager newsRequestManager = new NewsRequestManager(getContext());
        newsRequestManager.getNewsHeadlines(listener,"general",null);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching fresh news for you :)");
        progressDialog.show();

        searchNews.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching fresh news for you :)");
                progressDialog.show();
                NewsRequestManager newsRequestManager = new NewsRequestManager(getContext());
                newsRequestManager.getNewsHeadlines(listener,"general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return v;
    }

    private final OnDataFetchListener<NewsApiResponse> listener = new OnDataFetchListener<NewsApiResponse>() {
        @Override
        public void onDataFetch(List<NewsHeadlines> list, String msg) {
            if (list.isEmpty()){
                Toast.makeText(getContext(), "No data found :(", Toast.LENGTH_SHORT).show();
            }
            else{
                displayNews(list);
                progressDialog.dismiss();
            }

        }

        @Override
        public void onError(String msg) {

        }
    };

    private void displayNews(List<NewsHeadlines> list) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerViewAdapterNews = new RecyclerViewAdapterNews(getContext(),list);
        recyclerView.setAdapter(recyclerViewAdapterNews);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();
        progressDialog.setTitle("Fetching fresh news for "+ category + " :)");
        progressDialog.show();
        NewsRequestManager newsRequestManager = new NewsRequestManager(getContext());
        newsRequestManager.getNewsHeadlines(listener,category,null);

    }
}