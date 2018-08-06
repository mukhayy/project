package com.mukhayy.retrofit.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mukhayy.retrofit.Fragments.HomeFragment;
import com.mukhayy.retrofit.Fragments.OrderFragment;
import com.mukhayy.retrofit.Fragments.ProfileFragment;
import com.mukhayy.retrofit.Fragments.SearchFragment;
import com.mukhayy.retrofit.Models.Employee;
import com.mukhayy.retrofit.Models.EmployeeList;
import com.mukhayy.retrofit.R;
import com.mukhayy.retrofit.adapter.EmployeeAdapter;
import com.mukhayy.retrofit.network.GetEmployeeDataService;
import com.mukhayy.retrofit.network.RetrofitInstance;
import com.mukhayy.retrofit.utils.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.home_grey,
            R.drawable.search_grey,
            R.drawable.fuel,
            R.drawable.user_grey
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        setUpViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setTabIcons();
    }

    public void setTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setUpViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(),getString(R.string.home) );
        adapter.addFrag(new SearchFragment(),getString(R.string.search) );
        adapter.addFrag(new OrderFragment(),getString(R.string.order) );
        adapter.addFrag(new ProfileFragment(),getString(R.string.profile) );
        viewPager.setAdapter(adapter);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}