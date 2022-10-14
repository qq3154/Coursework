package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.SQLite.DatabaseHelper;
import com.example.myapplication.Trip.Trip;

import java.security.cert.CertPathBuilderSpi;
import java.time.LocalDate;

public class AddExpenseActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private Trip trip;

    private Spinner spnExpenseType;
    private EditText edtExpenseAmount;
    private Button btnDatePicker;
    private TextView tvDate;
    private Button btnAddExpense;

    private Boolean dateIsSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Expense");

        databaseHelper = new DatabaseHelper(this);

        trip = (Trip) getIntent().getExtras().get("object_trip");

        spnExpenseType = findViewById(R.id.spn_expense_type);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.expense_type, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnExpenseType.setAdapter(arrayAdapter);



        edtExpenseAmount = findViewById(R.id.edt_expense_amount);
        btnDatePicker = findViewById(R.id.btn_date_picker);
        tvDate = findViewById(R.id.tv_expense_date);
        btnAddExpense = findViewById(R.id.btn_add_expense);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpense();
            }
        });


    }

    private void addExpense() {

        String type = spnExpenseType.getSelectedItem().toString().trim();

        String strAmount = edtExpenseAmount.getText().toString().trim();
        Float amount;

        String date = tvDate.getText().toString().trim();

        if(strAmount == null || strAmount.length() == 0){
            Toast.makeText(this, "Please enter expense amount!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isNumeric(strAmount)) {
            amount = Float.parseFloat(strAmount);
        }
        else {
            Toast.makeText(this, "Amount is invalid!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!dateIsSelected){
            Toast.makeText(this, "Please select the expense date!", Toast.LENGTH_SHORT).show();
            return;
        }

        long expenseId = databaseHelper.insertExpense(type, amount.toString(), date, trip.getId());
        Toast.makeText(this, "Add expense successfully", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK, intentResult);
        finish();

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void showDatePickerDialog(View v){
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "AddExpense");
    }

    public void updateDate(LocalDate date){
        tvDate.setText(date.toString());
        dateIsSelected = true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intentResult = new Intent();
                setResult(Activity.RESULT_CANCELED, intentResult);
                finish();
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}