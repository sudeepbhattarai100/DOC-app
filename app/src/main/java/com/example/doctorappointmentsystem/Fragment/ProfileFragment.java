package com.example.doctorappointmentsystem.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.activity.MainActivity;
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

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {


    EditText firstName, lastName, address, email, username;
    Button btnUpdate;
    ImageView ivProfile;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    String imagePath,imageName;

    Dialog editProfile, editPass;


  @Nullable
    @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_profile, container, false);


      username = v.findViewById(R.id.username);
      firstName = v.findViewById(R.id.firstName);
      lastName = v.findViewById(R.id.lastName);
      email = v.findViewById(R.id.email);
      address = v.findViewById(R.id.address);
      ivProfile  = v.findViewById(R.id.ivProfile);
      btnUpdate = v.findViewById(R.id.btnUpdate);


        loadUserDetails();

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
      return v;

  }



    private void chooseImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data!=null) {

                Uri uri = data.getData();
                ivProfile.setImageURI(uri);
                imagePath = getPath(uri);

            } else {
                Toast.makeText(getActivity(), "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "image couldnot upload", Toast.LENGTH_SHORT).show();
        }
    }



    private String getPath(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity().getApplicationContext(), uri, proj, null, null, null);

        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String res = cursor.getString(colIndex);
        cursor.close();
        return res;
    }





//    private void uploadFile(){
//        if(imagePath!=null){
//            File file = new File(imagePath);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
//
//            doctor_api doctorApi = url.getInstance().create(doctor_api.class);
//            Call<picResponse> imageResponseCall = doctorApi.uploadPic(url.token, body);
//
//            private void strictMode(){
//                android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
//                android.os.StrictMode.setThreadPolicy(policy);
//            }
//
//
//            try {
//                Response<ImageResponse> imageResponseResponse = imageResponseCall.execute();
//                imageName = imageResponseResponse.body().getFilename();
//            }catch (IOException e){
//                Toast.makeText(UserupdateActivity.this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        }else {
//            Toast.makeText(UserupdateActivity.this, "Please choose file to update picture", Toast.LENGTH_SHORT).show();
//        }
//
//    }


    private void updateProfile() {

//        if(imageName!=null){
            if( email.getText().toString()!=null && username.getText().toString()!=null && firstName.getText().toString() != null && lastName.getText().toString() != null    && address.getText().toString() != null   ){
                patientResponse users = new patientResponse(username.getText().toString(),email.getText().toString(),firstName.getText().toString(), lastName.getText().toString(), address.getText().toString(), 1);
                doctor_api doctorApi = url.getInstance().create(doctor_api.class);
                Call<patientResponse> updateInfoCall = doctorApi.updatePatience(url.token, users);
               // System.out.println(url.token);

                updateInfoCall.enqueue(new Callback<patientResponse>() {
                    @Override
                    public void onResponse(Call<patientResponse> call, Response<patientResponse> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getActivity(), "Error : Failed to update info" , Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(getActivity(), "Information updated successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<patientResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(getActivity(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
            }
//        }
//        else {
//            if(firstName.getText().toString() != null && lastName.getText().toString() != null   && username.getText().toString()!=null   && email.getText().toString()!=null&& address.getText().toString() != null){
//                patients users = new patients(firstName.getText().toString(), lastName.getText().toString(), username.getText().toString(), email.getText().toString(),address.getText().toString(), imageName,  2);
//                doctor_api doctorApi = url.getInstance().create(doctor_api.class);
//                Call<patients> updateInfoCall = doctorApi.updatePatience(url.token, users);
//
//                updateInfoCall.enqueue(new Callback<patients>() {
//                    @Override
//                    public void onResponse(Call<patients> call, Response<patients> response) {
//                        if(!response.isSuccessful()){
//                            Toast.makeText(getActivity(), "Error : Failed to update info" , Toast.LENGTH_SHORT).show();
//                        }
//
//                        Toast.makeText(getActivity(), "Information updated successfully", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<patients> call, Throwable t) {
//                        Toast.makeText(getActivity(), "Error : "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//            else {
//                Toast.makeText(getActivity(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
//            }
//        }



    }




    private void loadUserDetails() {

        doctor_api userDetails = url.getInstance().create(doctor_api.class);
        Call<patientResponse> callUser = userDetails.getUserDetails(url.token);

        callUser.enqueue(new Callback<patientResponse>() {
            @Override
            public void onResponse(Call<patientResponse> call, Response<patientResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Error : Couldn't get user", Toast.LENGTH_SHORT).show();
                }
                username.setText(response.body().getUsername());
                firstName.setText(response.body().getFirstName());
                lastName.setText(response.body().getLastName());
                address.setText(response.body().getAddress());
                email.setText(response.body().getEmail());

                String imgPath = url.imagePath +  response.body().getProfileImage();
                Picasso.get().load(imgPath).into(ivProfile);

            }

            @Override
            public void onFailure(Call<patientResponse> call, Throwable t) {

            }
        });




    }

}
