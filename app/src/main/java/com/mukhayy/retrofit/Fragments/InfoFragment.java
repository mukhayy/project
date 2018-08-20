package com.mukhayy.retrofit.Fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mukhayy.retrofit.Activities.ProfileHomeActivity;
import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.database.dbManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private EditText nameru, lastName;
    private Button update;
    private dbManager manager;
    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        nameru = view.findViewById(R.id.name);
        lastName = view.findViewById(R.id.lastname2);
        update = view.findViewById(R.id.update);

        final String getArgument = getArguments().getString("phone");

        update.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                manager = new dbManager(getContext());
                manager.open();

                String name = nameru.getText().toString();
                String lname = lastName.getText().toString();

                manager.update(name, lname, getArgument);
                manager.close();

                Intent intent = new Intent(getActivity(), ProfileHomeActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
        return view;
    }
}
