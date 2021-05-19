package com.example.doctorappointmentsystem.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.patients;
import com.example.doctorappointmentsystem.serverResponse.patientResponse;
import com.example.doctorappointmentsystem.serverResponse.picResponse;
import com.example.doctorappointmentsystem.url.url;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registerActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS = 100;
    EditText username, password, firstName, lastName, address, email;
    Button btnRegister, btnChoosePic;
    String imagePath;
    private String imageName = "";
    ImageView imageView;


    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS);
        }
        email=findViewById(R.id.email);
        username = findViewById(R.id.username);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        imageView = findViewById(R.id.imageView);
        btnChoosePic = findViewById(R.id.btnSelectPic);
        btnRegister = findViewById(R.id.btnRegister);


        btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
                saveImageDb();
            }
        });


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {

                username.setText("");
                email.setText("");
                firstName.setText("");
                lastName.setText("");
                password.setText("");
                address.setText("");
                Picasso.get().load(R.drawable.patient).into(imageView);
            }
        });



    }



    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    private void choosePic(){
        Intent selectPic = new Intent(Intent.ACTION_PICK);
        selectPic.setType("image/*");
        startActivityForResult(selectPic, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(this, "Please Select an image", Toast.LENGTH_SHORT).show();
            }
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            imagePath = getRealPathFromURI(uri);
        }
    }

    @SuppressLint("NewApi")
    private String getRealPathFromURI(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this.getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void strictMode(){
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }

    private void uploadFile(){
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
        doctor_api uploadImage = url.getInstance().create(doctor_api.class);
        Call<picResponse> picResponseCall = uploadImage.uploadPic(body);

        strictMode();

        try{
            Response<picResponse> picResponseResponse = picResponseCall.execute();
            System.out.println("The image response is :" + picResponseResponse.body().getFilename());
            imageName = picResponseResponse.body().getFilename();
        }catch (IOException e){
            Toast.makeText(this, "Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImageDb(){
        System.out.println("The Image Name is: " +imageName);
        patients patientSignup = new patients(username.getText().toString(), email.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), address.getText().toString(), imageName);
        doctor_api registerApi = url.getInstance().create(doctor_api.class);
        Call<patientResponse> registerCall = registerApi.signup(patientSignup);

        registerCall.enqueue(new Callback<patientResponse>() {
            @Override
            public void onResponse(Call<patientResponse> call, Response<patientResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(registerActivity.this, "Code: ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent signupSuccess = new Intent(registerActivity.this, loginActivity.class);
                Toast.makeText(registerActivity.this, "Signed up sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(signupSuccess);
            }

            @Override
            public void onFailure(Call<patientResponse> call, Throwable t) {
                Toast.makeText(registerActivity.this, "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
