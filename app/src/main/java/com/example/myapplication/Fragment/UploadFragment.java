package com.example.myapplication.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Api.ApiService;
import com.example.myapplication.Expense.Expense;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SQLite.DatabaseHelper;
import com.example.myapplication.Trip.Trip;
import com.example.myapplication.Upload.Detail;
import com.example.myapplication.Upload.ResponseLog;
import com.example.myapplication.Upload.UploadPayload;
import com.example.myapplication.Upload.UploadResponse;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.time.LocalDateTime;

public class UploadFragment extends Fragment {

    private Button btnUpload;
    private TextView tvResponseLog;

    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_upload, container, false);
       return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getContext());

        tvResponseLog = view.findViewById(R.id.tv_response_log);
        tvResponseLog.setMovementMethod(new ScrollingMovementMethod());

        btnUpload = view.findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUploadApi();

//                PostMethod myTask = new PostMethod();
//                Thread t1 = new Thread(myTask, "JSON Thread");
//                t1.start();
//                GetMethod myTask = new GetMethod();
//                Thread t1 = new Thread(myTask, "JSON Thread");
//                t1.start();
            }
        });

        loadLog();

    }

    private void loadLog(){
        List<ResponseLog> logs = databaseHelper.getLogs();
        StringBuilder sbLog = new StringBuilder();

        //revert the list so the latest log will display first
        for (int i = logs.size() - 1; i >= 0; i--) {
            sbLog.append(logs.get(i).toString());
            sbLog.append("\n");
            sbLog.append("-------------------------------------------------------------------");
            sbLog.append("\n");
        }

        tvResponseLog.setText(sbLog.toString());
    }

    private void sendUploadApi() {
        List<Detail> details = new ArrayList<>();
        List<Trip> trips = databaseHelper.getTrips();
        List<Expense> expenses = databaseHelper.getExpenses();

        for (Trip trip : trips) {
            List<Expense> expensesOfTrip = new ArrayList<>();
            for (Expense expense:expenses) {
                if(expense.getTripId() == trip.getId()){
                    expensesOfTrip.add(expense);
                }
            }
            Detail detail = new Detail(trip.getId(), trip.getName(), trip.getDestination(), trip.getDate(), trip.getRiskAssessment(), trip.getDescription(), expensesOfTrip);
            details.add(detail);
        }
        UploadPayload uploadPayload = new UploadPayload("user123", details);

        Gson gson = new Gson();
        String classToStringJsonType = gson.toJson(uploadPayload);

        //Get current time when upload
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
        int second = LocalDateTime.now().getSecond();

        String time = String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, minute, second);

        ApiService.apiService.postRequest(classToStringJsonType).enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                UploadResponse uploadResponse = response.body();
                ((MainActivity)getActivity()).onUpload(true, uploadResponse.toString());

                databaseHelper.insertLog(time, uploadResponse.toString());

                loadLog();
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                ((MainActivity)getActivity()).onUpload(true, t.toString());
                databaseHelper.insertLog(time, t.toString());

                loadLog();
            }
        });

    }

    class GetMethod implements Runnable {

        @Override
        public void run() {
            String urlGet = "";
            try  {
                URL url = new URL(urlGet);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(5000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {



                    StringBuilder sb = new StringBuilder();
                    InputStream in =  new BufferedInputStream(urlc.getInputStream());
                    BufferedReader bin = new BufferedReader(new InputStreamReader(in));

                    //string for each line
                    String inputLine;
                    while((inputLine = bin.readLine()) != null){
                        sb.append(inputLine);
                    }
                    //((MainActivity)getActivity()).onUpload();




                    urlc.disconnect();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class PostMethod implements Runnable {
        @Override
        public void run() {
            String urlPost = "";
            try {
                URL url = new URL(urlPost);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Key","Value");
                connection.setDoOutput(true);

                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                String data = URLEncoder.encode("userName", "UTF-8")
                        + "=" + URLEncoder.encode("username-1", "UTF-8");

                data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                        + URLEncoder.encode("password-1", "UTF-8");
                connection.setFixedLengthStreamingMode(data.getBytes().length);

                connection.getOutputStream();

                OutputStream outputPost = new BufferedOutputStream(connection.getOutputStream());

                outputPost.flush();
                outputPost.close();


                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("jsonpayload", "{ \"userId\":\"wm123\", \"detailList\":[ {\"name\":\"Android Conference\"} ] }");

                String query = builder.build().getEncodedQuery();

                OutputStream os = connection.getOutputStream() ;
                byte[] input = query.getBytes("utf-8");
                os.write(input, 0, input.length);

                //Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                //Expecting answer of type JSON single line {"json_items":[{"status":"OK","message":"<Message>"}]}
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }
                rd.close();
                System.out.println(response.toString()+"\n");
                connection.disconnect(); // close the connection after usage

            } catch (Exception e){
                System.out.println(this.getClass().getSimpleName() + " ERROR - Request failed");
            }
        }

    }

}
