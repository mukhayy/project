package com.mukhayy.retrofit.Models;

import com.google.gson.annotations.SerializedName;
import com.mukhayy.retrofit.Models.Employee;

import java.util.ArrayList;

public class EmployeeList {

    @SerializedName("employeeList")
    private ArrayList<Employee> employeeList;

    public ArrayList<Employee> getEmployeeArrayList() {
        return employeeList;
    }

    public void setEmployeeArrayList(ArrayList<Employee> employeeArrayList) {
        this.employeeList = employeeArrayList;
    }

}
