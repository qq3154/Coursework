package com.example.myapplication.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.Expense.Expense;
import com.example.myapplication.Trip.Trip;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    private static final String DATABASE_NAME = "test";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_TRIPS = "trips";
    public static final String TRIP_ID = "trip_id";
    public static final String TRIP_NAME = "trip_name";
    public static final String TRIP_DESTINATION = "trip_destination";
    public static final String TRIP_DATE = "trip_date";
    public static final String TRIP_RISK_ASSESSMENT = "trip_risk_assessment";
    public static final String TRIP_DESCRIPTION = "trip_description";

    private static final String TABLE_EXPENSES = "expenses";
    public static final String EXPENSE_ID = "expense_id";
    public static final String EXPENSE_TYPE = "expense_type";
    public static final String EXPENSE_AMOUNT = "expense_amount";
    public static final String EXPENSE_DATE = "expense_date";

    private static final String CREATE_TABLE_TRIPS = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s INTEGER, " +
                    "%s TEXT)",
            TABLE_TRIPS, TRIP_ID, TRIP_NAME, TRIP_DESTINATION, TRIP_DATE, TRIP_RISK_ASSESSMENT, TRIP_DESCRIPTION
    );

    private static final String CREATE_TABLE_EXPENSES = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER, " +
                    "%s TEXT, " +
                    "%s REAL, " +
                    "%s TEXT, " +
                    "FOREIGN KEY (%s) REFERENCES %s (%s) )",
            TABLE_EXPENSES, EXPENSE_ID, TRIP_ID, EXPENSE_TYPE, EXPENSE_AMOUNT, EXPENSE_DATE, TRIP_ID, TABLE_TRIPS, TRIP_ID
    );



    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON");

        sqLiteDatabase.execSQL(CREATE_TABLE_TRIPS);
        sqLiteDatabase.execSQL(CREATE_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);

        Log.v(this.getClass().getName(), TABLE_TRIPS +
                "database upgrade to version" + newVersion + " - old data lost"
        );

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);

        Log.v(this.getClass().getName(), TABLE_EXPENSES +
                "database upgrade to version" + newVersion + " - old data lost"
        );

        onCreate(db);
    }

    public List<Trip> getTrips(){
        List<Trip> mTrips = new ArrayList<>();

        Cursor results = database.query(TABLE_TRIPS,
                new String[]{TRIP_ID, TRIP_NAME, TRIP_DESTINATION, TRIP_DATE, TRIP_RISK_ASSESSMENT, TRIP_DESCRIPTION},
                null, null, null, null, TRIP_NAME
        );
        String resultText ="";

        results.moveToFirst();
        while(!results.isAfterLast()){
            int id = results.getInt(0);
            String name = results.getString(1);
            String destination = results.getString(2);
            String date = results.getString(3);
            int intRiskAssessment = results.getInt(4);
            String description = results.getString(5);

            Boolean riskAssessment = intRiskAssessment == 1;
            Trip trip = new Trip(id, name, destination, date, riskAssessment, description);
            mTrips.add(trip);

            results.moveToNext();
        }

        return mTrips;
    }

    public long insertTrip(String name, String destination, String date, Boolean riskAssessment, String description){
        ContentValues rowValues = new ContentValues();

        rowValues.put(TRIP_NAME, name);
        rowValues.put(TRIP_DESTINATION, destination);
        rowValues.put(TRIP_DATE, date);
        rowValues.put(TRIP_RISK_ASSESSMENT, riskAssessment);
        rowValues.put(TRIP_DESCRIPTION, description);

        return database.insertOrThrow(TABLE_TRIPS, null, rowValues);
    }

    public void updateTrip(Trip trip){
        Boolean boolRiskAssessment = trip.getRiskAssessment();
        int riskAssessment = boolRiskAssessment ? 1 : 0;


        String DATABASE_UPDATE = String.format("UPDATE %s SET %s = '%s', %s = '%s', %s = '%s', %s = '%s', %s = '%s' WHERE %s = %s;",
                TABLE_TRIPS,
                TRIP_NAME, trip.getName(),
                TRIP_DESTINATION, trip.getDestination(),
                TRIP_DATE, trip.getDate(),
                TRIP_RISK_ASSESSMENT, riskAssessment,
                TRIP_DESCRIPTION,
                trip.getDescription(),
                TRIP_ID, trip.getId());
        database.execSQL(DATABASE_UPDATE);
    }

    public void deleteTrip(Trip trip){
        String DATABASE_DELETE = String.format("DELETE FROM %s  WHERE %s = %s;",
                TABLE_TRIPS, TRIP_ID, trip.getId());
        database.execSQL(DATABASE_DELETE);
    }

    public void deleteAllTrips(){
        String DATABASE_DELETE_ALL = String.format("DELETE FROM %s;", TABLE_TRIPS);
        database.execSQL(DATABASE_DELETE_ALL);
    }

    public Boolean checkTripExist(String tripName){
        List<Trip> trips = getTrips();
        for (Trip trip : trips) {
            String myTripName = trip.getName();
            if(myTripName.equals(tripName)){
                return true;
            }
        }
        return false;
    }

    public List<Expense> getExpenses(){
        List<Expense> expenses = new ArrayList<>();

        Cursor results = database.query(TABLE_EXPENSES,
                new String[]{EXPENSE_ID, TRIP_ID, EXPENSE_TYPE, EXPENSE_AMOUNT, EXPENSE_DATE},
                null, null, null, null, EXPENSE_TYPE
        );
        String resultText ="";

        results.moveToFirst();
        while(!results.isAfterLast()){
            int id = results.getInt(0);
            int trip_id = results.getInt(1);
            String type = results.getString(2);
            Float amount = results.getFloat(3);
            String date = results.getString(4);

            Expense expense = new Expense(id, trip_id, type, amount, date);
            expenses.add(expense);

            results.moveToNext();
        }

        return expenses;
    }

    public long insertExpense(String type, String amount, String date, int tripId){
        ContentValues rowValues = new ContentValues();

        rowValues.put(TRIP_ID, tripId);
        rowValues.put(EXPENSE_TYPE, type);
        rowValues.put(EXPENSE_AMOUNT, amount);
        rowValues.put(EXPENSE_DATE, date);

        String insertSql = String.format("INSERT INTO %s " +
                        "(%s, %s, %s, %s) " +
                        "VALUES(%s, '%s', '%s', '%s')",
                TABLE_EXPENSES, TRIP_ID,EXPENSE_TYPE, EXPENSE_AMOUNT, EXPENSE_DATE, tripId, type, amount, date );


        return database.insertOrThrow(TABLE_EXPENSES, null, rowValues);
    }

    public void updateExpense(Expense expense){
        String DATABASE_UPDATE = String.format("UPDATE %s SET %s = '%s', %s = '%s', %s = '%s' WHERE %s = %s;",
                TABLE_EXPENSES, EXPENSE_TYPE, expense.getType(), EXPENSE_AMOUNT, expense.getAmount(), EXPENSE_DATE, expense.getDate(), EXPENSE_ID, expense.getId());
        database.execSQL(DATABASE_UPDATE);
    }

    public void deleteExpense(Expense expense){
        String DATABASE_DELETE = String.format("DELETE FROM %s  WHERE %s = %s;",
                TABLE_EXPENSES, EXPENSE_ID, expense.getId());
        database.execSQL(DATABASE_DELETE);
    }

    public void deleteAllExpenses(){
        String DATABASE_DELETE_ALL = String.format("DELETE FROM %s;", TABLE_EXPENSES);
        database.execSQL(DATABASE_DELETE_ALL);
    }

    public Boolean checkExpenseExist(String expenseType){
        List<Expense> expenses = getExpenses();
        for (Expense expense : expenses) {
            String myExpenseType = expense.getType();
            if(myExpenseType.equals(expenseType)){
                return true;
            }
        }
        return false;
    }


}
