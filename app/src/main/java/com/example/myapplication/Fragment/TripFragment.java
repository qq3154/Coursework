package com.example.myapplication.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.myapplication.AddTripActivity;
import com.example.myapplication.R;
import com.example.myapplication.SQLite.DatabaseHelper;
import com.example.myapplication.Trip.Trip;
import com.example.myapplication.Trip.TripAdapter;
import com.example.myapplication.UpdateTripActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripFragment extends Fragment {

    private RecyclerView rcvTrip;
    private TripAdapter tripAdapter;

    private DatabaseHelper databaseHelper;

    private FloatingActionButton btnAdd;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_trip, container, false);
       setHasOptionsMenu(true);
       getActivity().setTitle("All Trips");


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

            btnAdd = view.findViewById(R.id.btn_add_trip);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAddTrip();
                }
            });

            loadData();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                databaseHelper.deleteAllTrips();
                loadData();
                Toast.makeText( getActivity(), "Delete Successful!", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void  loadData(){
        tripAdapter.setData(databaseHelper.getTrips());
    }

    private void clickAddTrip() {

        Intent intent = new Intent(this.getActivity(), AddTripActivity.class);
        someActivityResultLauncher.launch(intent);

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
                        loadData();
                    }

                }
    });
}
