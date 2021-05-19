package com.example.doctorappointmentsystem.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.api.category_api;
import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.category;
import com.example.doctorappointmentsystem.model.doctor;
import com.example.doctorappointmentsystem.serverResponse.doctorResponse;
import com.example.doctorappointmentsystem.serverResponse.picResponse;
import com.example.doctorappointmentsystem.url.url;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocRegisterActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS = 100;
    EditText username, password, firstName, lastName, address, email, qualification;
    Button btnRegister, btnChoosePic;
    String imagePath;
    private String imageName = "";
    ImageView imageView;

    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    List<category> categoryList;
    //For spinner
    Spinner spinner;
    ArrayList<String> list = new ArrayList<>();
    String categoryName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_register);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS);
        }
        email=findViewById(R.id.email);
        username = findViewById(R.id.username);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        qualification = findViewById(R.id.qualification);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        imageView = findViewById(R.id.imageView);
        spinner = findViewById(R.id.spinner);

        btnChoosePic = findViewById(R.id.btnSelectPic);
        btnRegister = findViewById(R.id.btnRegister);
        setSpinner();


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
                qualification.setText("");
                
                Picasso.get().load(R.drawable.doc).into(imageView);
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

    private String getRealPathFromURI(Uri uri) {
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
        System.out.println("The Image Name is: " +spinner.getSelectedItem().toString());
        doctor doctorSignup = new doctor(
                username.getText().toString(),
                password.getText().toString(),
                firstName.getText().toString(),
                qualification.getText().toString(),
                lastName.getText().toString(),
                address.getText().toString(),
                email.getText().toString(),
                categoryName,
                imageName
        );
        //Toast.makeText(DocRegisterActivity.this, "the values are: "+categoryName, Toast.LENGTH_SHORT).show();
       // System.out.println("the spinner value is: "+categoryName);
        doctor_api registerApi = url.getInstance().create(doctor_api.class);
        Call<doctorResponse> registerCall = registerApi.signup(doctorSignup);
        //System.out.println("the response is " + firstName.getText().toString() + "  " + lastName.getText().toString() + "  " + email.getText().toString());
        registerCall.enqueue(new Callback<doctorResponse>() {
            @Override
            public void onResponse(Call<doctorResponse> call, Response<doctorResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DocRegisterActivity.this, "Code: ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent signupSuccess = new Intent(DocRegisterActivity.this, DocLoginActivity.class);
                Toast.makeText(DocRegisterActivity.this, "Signed up sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(signupSuccess);
            }

            @Override
            public void onFailure(Call<doctorResponse> call, Throwable t) {
                Toast.makeText(DocRegisterActivity.this, "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void setSpinner(){

        category_api category_api = url.getInstance().create(category_api.class);
        Call<List<category>> categoryCall = category_api.getAllCategorieName();

        categoryCall.enqueue(new Callback<List<category>>() {
            @Override
            public void onResponse(Call<List<category>> call, Response<List<category>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DocRegisterActivity.this, "error:"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                categoryList = response.body();

                for(category category: categoryList) {
                    list.add(category.getCategoryName().toString());
                }
                spinnerSet();
            }

            @Override
            public void onFailure(Call<List<category>> call, Throwable t) {
                Toast.makeText(DocRegisterActivity.this, "error:"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void spinnerSet(){
        ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(listAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
