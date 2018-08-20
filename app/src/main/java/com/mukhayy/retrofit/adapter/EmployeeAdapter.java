package com.mukhayy.retrofit.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mukhayy.retrofit.Activities.MapsActivity;
import com.mukhayy.retrofit.Models.Employee;
import com.mukhayy.retrofit.R;

import java.util.ArrayList;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>  {

    private ArrayList<Employee> employees;
    private Context context;

    public EmployeeAdapter(ArrayList<Employee> employees,Context context) {
        this.employees = employees;
        this.context=context;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view, parent, false);

        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EmployeeViewHolder holder, int position) {

        holder.name.setText(employees.get(position).getName());
        holder.email.setText(employees.get(position).getEmail());
        holder.phone.setText(employees.get(position).getPhone());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
               //
            }
        });

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }


    public class EmployeeViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        TextView name, email, phone;
        CardView parentLayout;
        ImageView imageView;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.employee_name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
