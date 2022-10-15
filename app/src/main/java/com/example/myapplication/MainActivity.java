package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.Fragment.SearchFragment;
import com.example.myapplication.SQLite.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        long tripId = dbHelper.insertTrip("Danang", "danang", "1/1/1", "none");
//        dbHelper.getTrips();
//        long expenseId = dbHelper.insertExpense("Food", "4.5f", "1/2/3", 1 );
//        dbHelper.getExpenses();



        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);

        setupViewPager();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_trip:
                        viewPager.setCurrentItem(0);
                        setTitle("All Trips");
                        break;
                    case R.id.action_search:
                        viewPager.setCurrentItem(1);
                        setTitle("Search");
                        break;
                    case R.id.action_upload:
                        viewPager.setCurrentItem(2);
                        setTitle("Upload");
                        break;

                }
                return true;
            }
        });

    }


    private void setupViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_trip).setChecked(true);
                        setTitle("All Trips");
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_search).setChecked(true);
                        setTitle("Search");
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_upload).setChecked(true);
                        setTitle("Upload");
                        break;

                }
                super.onPageSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }


}