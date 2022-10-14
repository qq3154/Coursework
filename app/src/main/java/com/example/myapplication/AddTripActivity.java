package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.SQLite.DatabaseHelper;

import java.time.LocalDate;

public class AddTripActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtDestination;
    private Button btnDatePicker;
    private TextView tvDate;
    private Switch swRiskAssessment;
    private EditText edtDescription;
    private Button btnAddTrip;

    private DatabaseHelper databaseHelper;

    private Boolean dateIsSelected =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Trip");

        databaseHelper = new DatabaseHelper(this);

        edtName = findViewById(R.id.edt_trip_name);
        edtDestination = findViewById(R.id.edt_trip_destination);
        btnDatePicker = findViewById(R.id.btn_date_picker);
        tvDate = findViewById(R.id.tv_trip_date);
        swRiskAssessment = findViewById(R.id.sw_risk_assessment);
        edtDescription = findViewById(R.id.edt_trip_description);
        btnAddTrip = findViewById(R.id.btn_add_trip);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTrip();
            }
        });


    }

    private void addTrip() {
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
        if(!dateIsSelected){
            Toast.makeText(this, "Please select the trip date!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strDescription == null || strDescription.length() == 0){
            Toast.makeText(this, "Please enter trip description!", Toast.LENGTH_SHORT).show();
            return;
        }

        long tripId = databaseHelper.insertTrip(strName, strDestination, strDate, riskAssessmentChecked, strDescription);
        Toast.makeText(this, "Add trip successfully", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK, intentResult);
        finish();

    }

    public void showDatePickerDialog(View v){
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "AddTrip");
    }

    public void updateDate(LocalDate date){
        tvDate.setText(date.toString());
        dateIsSelected = true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intentResult = new Intent();
                setResult(Activity.RESULT_CANCELED, intentResult);
                finish();
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);

    }
}