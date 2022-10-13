package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.SQLite.DatabaseHelper;
import com.example.myapplication.Trip.Trip;

public class UpdateTripActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtDestination;
    private Button btnDatePicker;
    private TextView tvDate;
    private Switch swRiskAssessment;
    private EditText edtDescription;
    private Button btnUpdateTrip;

    private DatabaseHelper databaseHelper;

    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Update Trip");

        databaseHelper = new DatabaseHelper(this);

        edtName = findViewById(R.id.edt_trip_name);
        edtDestination = findViewById(R.id.edt_trip_destination);
        btnDatePicker = findViewById(R.id.btn_date_picker);
        tvDate = findViewById(R.id.tv_trip_date);
        swRiskAssessment = findViewById(R.id.sw_risk_assessment);
        edtDescription = findViewById(R.id.edt_trip_description);
        btnUpdateTrip = findViewById(R.id.btn_update_trip);

        btnUpdateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTrip();
            }
        });

        trip = (Trip) getIntent().getExtras().get("object_trip");

        if(trip != null){
            edtName.setText(trip.getName());
            edtDestination.setText(trip.getDestination());
            tvDate.setText(trip.getDate());
            swRiskAssessment.setChecked(trip.getRiskAssessment());
            edtDescription.setText(trip.getDescription());
        }

    }


    private void updateTrip() {
        String strName = edtName.getText().toString().trim();
        String strDestination = edtDestination.getText().toString().trim();
        String strDate = tvDate.getText().toString().trim();
        Boolean riskAssessmentChecked = swRiskAssessment.isChecked();
        String strDescription = edtDescription.getText().toString().trim();

        if(strName == null || strName.length() == 0){
            Toast.makeText(this, "Please enter trip name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strDestination == null || strDestination.length() == 0){
            Toast.makeText(this, "Please enter trip destination!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strDescription == null || strDescription.length() == 0){
            Toast.makeText(this, "Please enter trip description!", Toast.LENGTH_SHORT).show();
            return;
        }

        trip.setName(strName);
        trip.setDestination(strDestination);
        trip.setDate(strDate);
        trip.setRiskAssessment(riskAssessmentChecked);
        trip.setDescription(strDescription);


        databaseHelper.updateTrip(trip);
        Toast.makeText(this, "Update trip successfully", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK, intentResult);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intentResult = new Intent();
                setResult(Activity.RESULT_CANCELED, intentResult);
                finish();
                onBackPressed();
                return true;

            case R.id.delete:
                databaseHelper.deleteTrip(trip);
                Toast.makeText(this, "Delete trip successfully", Toast.LENGTH_SHORT).show();
                Intent intentDeleteResult = new Intent();
                setResult(Activity.RESULT_OK, intentDeleteResult);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}