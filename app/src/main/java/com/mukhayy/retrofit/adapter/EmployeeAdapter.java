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

import jp.wasabeef.blurry.Blurry;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //LayoutInflater inflater = LayoutInflater.from(context);
                //view = inflater.inflate(R.layout.dialog, (ViewGroup) view.findViewById(R.id.layout));
               // view = inflater.inflate(R.layout.dialog, (ViewGroup) view.findViewById(R.id.MainLayout));
                builder.setNeutralButton(
                        R.string.go_to_map, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                );

                //builder.setView(view);
                AlertDialog dialog = builder.create();
                Blurry.with(context)
                        .radius(40)
                        .sampling(100)
                        .async()
                        .capture(view)
                        .into((ImageView) view);

                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }


    public class EmployeeViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        TextView name, email, phone;
       // ImageView image;
        CardView parentLayout;
        ImageView imageView;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.employee_name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
           // image = itemView.findViewById(R.id.image);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
