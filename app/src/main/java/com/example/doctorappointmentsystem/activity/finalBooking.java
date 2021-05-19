package com.example.doctorappointmentsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.api.appointment;
import com.example.doctorappointmentsystem.model.appointmentModel;
import com.example.doctorappointmentsystem.url.url;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class finalBooking extends AppCompatActivity {
    private TextView tvSelectedTime;
    EditText query;
    private int intHour, intMinute;
    private Button btnOpentimePickerDialog, btnOpendatePickerDialog, btnBook;
    private String strMin = "", docId, date, time;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking);
        Intent i = getIntent();
        docId = i.getStringExtra("docId");

        System.out.println("The doctor id is :" +docId);
        query = findViewById(R.id.Query);
        btnBook = (Button)findViewById(R.id.btnBook);
        btnOpendatePickerDialog = (Button)findViewById(R.id.btnChooseDate);
        final TextView selectedDate = (TextView)findViewById(R.id.selectedDate);

        getIds();
        setListner();

        btnOpendatePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(finalBooking.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                selectedDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
    });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentModel createAppointment = new appointmentModel(docId, date, time, query.getText().toString());

                appointment appointmentApi = url.getInstance().create(appointment.class);
                Call<appointmentModel> appointmentCall = appointmentApi.saveAppointment(url.token, createAppointment);
                appointmentCall.enqueue(new Callback<appointmentModel>() {
                    @Override
                    public void onResponse(Call<appointmentModel> call, Response<appointmentModel> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(finalBooking.this, "Couldn't get appointment", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(finalBooking.this, "Your appointment has been booked", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<appointmentModel> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void setListner() {
        try {
            btnOpentimePickerDialog.setOnClickListener(onClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                switch (view.getId()){
                    case R.id.btnChooseTime:
                        openTimePicker();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void openTimePicker() {
        CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(finalBooking.this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("NewApi")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.e("Log", "selected time----" + hourOfDay + ":" + minute);
                String strTime = hourOfDay + ":"+ minute + ":00";
                updateTime(hourOfDay, minute);
                time=strTime;
            }
        }, intHour, intMinute, false);
        timePickerDialog.show();

    }

    private void updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
//TODO=================Adding zero in one dightleft  and right======================
        if (mins < 10)
            strMin = "0" + mins;
        else
            strMin = String.valueOf(mins);
        // Append in a StringBuilder
        String aTime = new StringBuilder().append(pad(hours)).append(':')
                .append(pad(Integer.parseInt(strMin))).append(" ").append(timeSet).toString();

        Log.e("aTime checking ==>",""+aTime);

        tvSelectedTime.setText(aTime);
    }


    private void getIds() {
        try {
            btnOpentimePickerDialog = findViewById(R.id.btnChooseTime);
            tvSelectedTime = findViewById(R.id.selectedTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
