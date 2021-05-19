package com.example.doctorappointmentsystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorappointmentsystem.Fragment.AppointmentFragment;
import com.example.doctorappointmentsystem.Fragment.HomeFragment;
import com.example.doctorappointmentsystem.Fragment.MessageFragment;
import com.example.doctorappointmentsystem.Fragment.ProfileFragment;
import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.serverResponse.doctorResponse;
import com.example.doctorappointmentsystem.url.url;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class docMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    TextView fullName, email;
    CircleImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer=findViewById(R.id.drawyer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fullName = navigationView.getHeaderView(0).findViewById(R.id.fullName);
        email = navigationView.getHeaderView(0). findViewById(R.id.email);
        profilePic = navigationView.getHeaderView(0).findViewById(R.id.navProfilePic);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

            loadCurrentUser();
        }

    }

    private void loadCurrentUser(){
        doctor_api doctorDetails = url.getInstance().create(doctor_api.class);
        Call<doctorResponse> callUser = doctorDetails.getDoctorDetails(url.token);

        callUser.enqueue(new Callback<doctorResponse>() {
            @Override
            public void onResponse(Call<doctorResponse> call, Response<doctorResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(docMainActivity.this, "Error : Couldn't get user", Toast.LENGTH_SHORT).show();
                }

                String imgPath = url.imagePath +  response.body().getProfileImage();
                System.out.println("The imageName is: " + response.body().getProfileImage());
                Picasso.get().load(imgPath).into(profilePic);
                fullName.setText(response.body().getFirstName()+response.body().getLastName());
                email.setText(response.body().getEmail());
            }

            @Override
            public void onFailure(Call<doctorResponse> call, Throwable t) {

            }


        });
    }



    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START )){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessageFragment()).commit();
                break;

            case R.id.nav_appointment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AppointmentFragment()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;

            case R.id.nav_logout:
                logout();
                break;


        }
        return true;
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                //rememberData.edit().clear().commit();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}

