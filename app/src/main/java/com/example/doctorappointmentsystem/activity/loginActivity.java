package com.example.doctorappointmentsystem.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctorappointmentsystem.BroadCastReceiver;
import com.example.doctorappointmentsystem.CreateChannel;
import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.patients;
import com.example.doctorappointmentsystem.serverResponse.patientResponse;
import com.example.doctorappointmentsystem.url.url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{

    public SensorManager sensorManager;

    Button btnLogin, btnRegister, btnDoctor;
    EditText username, password;
    Vibrator vibrator;
    public NotificationManagerCompat notificationManagerCompat;
    BroadCastReceiver broadCastReceiver = new BroadCastReceiver(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.createChannel();

        btnLogin = findViewById(R.id.btnLogin);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        btnRegister=findViewById(R.id.btnRegister);
        btnDoctor = findViewById(R.id.btnDoctor);
        vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);

        btnDoctor.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        getNotification();

      //  lightsensor();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegister:
                openRegister();
                break;
            case R.id.btnDoctor:
                openDoctor();
                break;
                default:
                    break;
        }
    }

    private void login() {
        if(username.getText().toString()!=null && password.getText().toString()!=null){
            patients patientLogin = new patients(username.getText().toString(), password.getText().toString());
            doctor_api patientApi = url.getInstance().create(doctor_api.class);
            Call<patientResponse> loginCall = patientApi.login(patientLogin);

            loginCall.enqueue(new Callback<patientResponse>() {
                @Override
                public void onResponse(Call<patientResponse> call, Response<patientResponse> response) {

                    if(!response.isSuccessful()){
                        Toast.makeText(loginActivity.this, "Username and password didn't match", Toast.LENGTH_SHORT).show();
                        vibrator.vibrate(50);
                        return;
                    }

                    url.token += response.body().getToken();
                    openDashboard();
                }

                @Override
                public void onFailure(Call<patientResponse> call, Throwable t) {
                    Toast.makeText(loginActivity.this, "Username and password didn't match" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
       



    }

    public void openDashboard(){
        Intent openDashboard = new Intent(loginActivity.this, MainActivity.class);
        startActivity(openDashboard);
    }



    public void openRegister(){
     Intent intent = new Intent(loginActivity.this, registerActivity.class);
     startActivity(intent);
    }

    public void openDoctor(){
        Intent intent = new Intent(loginActivity.this, DocLoginActivity.class);
        startActivity(intent);
    }



//    private void lightsensor() {
//        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
//        Sensor sensor= sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        SensorEventListener sensorEventListener= new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                if( event.sensor.getType() == Sensor.TYPE_LIGHT) {
//                    Toast.makeText(getApplicationContext(), "On SensorChanged" + event.values[0], Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        };
//        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_FASTEST);
//
//    }

    public void getNotification() {
        Notification notification=new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.doc)
                .setContentTitle("Docapp")
                .setContentText("You have been connected to a network")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2,notification);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        registerReceiver(broadCastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadCastReceiver);
    }
}
