package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.Expense.Expense;
import com.example.myapplication.Expense.ExpenseAdapter;
import com.example.myapplication.SQLite.DatabaseHelper;
import com.example.myapplication.Trip.Trip;
import com.example.myapplication.Trip.TripAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewExpenseActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    private RecyclerView rcvExpense;
    private ExpenseAdapter expenseAdapter;
    private FloatingActionButton btnAddExpense;

    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("All expenses");

        databaseHelper = new DatabaseHelper(this);

        trip = (Trip) getIntent().getExtras().get("object_trip");

        rcvExpense = findViewById(R.id.rcv_expense);
        rcvExpense.setLayoutManager(new LinearLayoutManager(this));

        expenseAdapter = new ExpenseAdapter(new ExpenseAdapter.IClickItemExpense() {
            @Override
            public void updateExpense(Expense expense) {
                clickUpdateExpense(expense);
            }
        });
        rcvExpense.setAdapter(expenseAdapter);


        btnAddExpense = findViewById(R.id.btn_add_expense);
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAddExpense();
            }
        });

        loadData();
    }

    private void loadData() {
        List<Expense> expenses = databaseHelper.getExpenses();
        List<Expense> expensesOfTrip = new ArrayList<>();
        for (Expense expense:expenses) {
            if(expense.getTripId() == trip.getId()){
                expensesOfTrip.add(expense);
            }
        }
        expenseAdapter.setData(expensesOfTrip);
    }

    private void clickAddExpense() {
        Intent intent = new Intent(this, AddExpenseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_trip", trip);
        intent.putExtras(bundle);
        someActivityResultLauncher.launch(intent);
    }

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

    private void clickUpdateExpense(Expense expense) {
        Intent intent = new Intent(this, UpdateExpenseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_expense", expense);
        intent.putExtras(bundle);
        someActivityResultLauncher.launch(intent);
    }

    private void deleteAllExpenses() {
        databaseHelper.deleteAllExpenses();
        loadData();
        Toast.makeText( this, "Delete Successful!", Toast.LENGTH_SHORT).show();
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
                finish();
                onBackPressed();
                return true;

            case R.id.delete:
                deleteAllExpenses();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}