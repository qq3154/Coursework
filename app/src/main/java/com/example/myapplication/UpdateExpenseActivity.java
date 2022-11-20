package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Expense.Expense;
import com.example.myapplication.SQLite.DatabaseHelper;
import com.example.myapplication.Trip.Trip;

import java.time.LocalDate;

public class UpdateExpenseActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private Expense expense;

    private Spinner spnExpenseType;
    private EditText edtExpenseAmount;
    private Button btnDatePicker;
    private TextView tvDate;
    private Button btnUpdateExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Update Expense");

        databaseHelper = new DatabaseHelper(this);

        expense = (Expense) getIntent().getExtras().get("object_expense");

        spnExpenseType = findViewById(R.id.spn_expense_type);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.expense_type, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnExpenseType.setAdapter(arrayAdapter);



        edtExpenseAmount = findViewById(R.id.edt_expense_amount);
        btnDatePicker = findViewById(R.id.btn_date_picker);
        tvDate = findViewById(R.id.tv_expense_date);
        btnUpdateExpense = findViewById(R.id.btn_update_expense);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        btnUpdateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateExpense();
            }
        });

        if(expense != null){
            spnExpenseType.setSelection(arrayAdapter.getPosition(expense.getType()));
            edtExpenseAmount.setText(expense.getAmount().toString());
            tvDate.setText(expense.getDate());
        }
    }

    private void updateExpense() {
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

        if( amount <= 0){
            Toast.makeText(this, "Amount less than 0!", Toast.LENGTH_SHORT).show();
            return;
        }


        expense.setType(type);
        expense.setAmount(amount);
        expense.setDate(date);
        databaseHelper.updateExpense(expense);
        Toast.makeText(this, "Update expense successfully", Toast.LENGTH_SHORT).show();

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
        newFragment.show(getSupportFragmentManager(), "UpdateExpense");
    }

    public void updateDate(LocalDate date){
        tvDate.setText(date.toString());
        //dateIsSelected = true;
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
                databaseHelper.deleteExpense(expense);
                Toast.makeText(this, "Delete expense successfully", Toast.LENGTH_SHORT).show();
                Intent intentDeleteResult = new Intent();
                setResult(Activity.RESULT_OK, intentDeleteResult);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}