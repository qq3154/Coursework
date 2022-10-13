package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
                        break;
                    case R.id.action_search:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_upload:
                        viewPager.setCurrentItem(2);
                        break;

                }
                return true;
            }
        });

    }


    private void setupViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);
    }


}