package com.mukhayy.retrofit.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mukhayy.retrofit.Activities.MainActivity;
import com.mukhayy.retrofit.Models.Employee;
import com.mukhayy.retrofit.Models.EmployeeList;
import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.adapter.EmployeeAdapter;
import com.mukhayy.retrofit.network.GetEmployeeDataService;
import com.mukhayy.retrofit.network.RetrofitInstance;

import java.util.ArrayList;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private EmployeeAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView imageView;

    private ShimmerLayout shimmerLayout;

    public HomeFragment() { // Required empty public constructor
         }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        shimmerLayout = view.findViewById(R.id.shimmer_view_container);
        shimmerLayout.startShimmerAnimation();

       // imageView = view.findViewById(R.id.image);

        GetEmployeeDataService service = RetrofitInstance.getRetrofit().create(GetEmployeeDataService.class);
        Call<EmployeeList> call = service.getEmployeeData(100);
        /*Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<EmployeeList>() {
            @Override
            public void onResponse(Call<EmployeeList> call, Response<EmployeeList> response) {
                shimmerLayout.setVisibility(View.GONE);
                generateEmployeeList(response.body().getEmployeeArrayList(), view);

            }

            @Override
            public void onFailure(Call<EmployeeList> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void generateEmployeeList(ArrayList<Employee> employees, View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new EmployeeAdapter(employees,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
