package com.example.myapplication.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.SQLite.DatabaseHelper;
import com.example.myapplication.Trip.Trip;
import com.example.myapplication.Trip.TripAdapter;
import com.example.myapplication.UpdateTripActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {

    DatabaseHelper databaseHelper;

    private EditText edtSearch;
    private Button btnSearch;
    private RecyclerView rcvTrip;
    private TripAdapter tripAdapter;

    private List<Trip> trips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_search, container, false);
       return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        rcvTrip = view.findViewById(R.id.rcv_trip);
        rcvTrip.setLayoutManager(new LinearLayoutManager(getContext()));
        tripAdapter = new TripAdapter(new TripAdapter.IClickItemTrip() {
            @Override
            public void updateTrip(Trip trip) {
                clickUpdateTrip(trip);
            }
        });
        rcvTrip.setAdapter(tripAdapter);

        edtSearch = view.findViewById(R.id.edt_search);
        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSearch();
            }
        });

        trips = new ArrayList<>();

        loadData();
    }

    private void onClickSearch() {
        String search = edtSearch.getText().toString().trim().toUpperCase();
        if(search.length() ==0){
            return;
        }
        List<Trip> allTrips = databaseHelper.getTrips();
        trips.clear();

        for (Trip trip:allTrips
             ) {
            if(trip.getName().toUpperCase().contains(search)){
                trips.add(trip);
            }
            else if(trip.getDestination().toUpperCase().contains(search)){
                trips.add(trip);
            }
            else if(trip.getDate().toUpperCase().contains(search)){
                trips.add(trip);
            }

        }

        loadData();
    }

    private void  loadData(){
        tripAdapter.setData(trips);
    }

    private void  reloadData(){
        onClickSearch();
        loadData();
    }

    private void clickUpdateTrip(Trip trip){
        Intent intent = new Intent(this.getContext(), UpdateTripActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_trip", trip);
        intent.putExtras(bundle);
        someActivityResultLauncher.launch(intent);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        reloadData();
                    }
                }
            });

    @Override
    public void onResume() {
        reloadData();
        super.onResume();

    }
}
