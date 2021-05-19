package com.example.doctorappointmentsystem.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.adapter.AppointmentAdapater;
import com.example.doctorappointmentsystem.api.appointment;
import com.example.doctorappointmentsystem.api.doctor_api;
import com.example.doctorappointmentsystem.model.appointmentModel;
import com.example.doctorappointmentsystem.model.doctor;
import com.example.doctorappointmentsystem.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class HomeFragment extends Fragment {
//    Context context;
//
    RecyclerView recyclerView;

//    List<appointmentModel> lstappoint;
//
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        context = getContext();

        recyclerView  = view.findViewById(R.id.appointment);

           //loaduser();

           getappoiment();


        return view;
    }

//    private void loaduser() {
//        doctor_api  doc = url.getInstance().create(doctor_api.class);
//      //  Call<docterresponse> doctorCall = doc.getAllDoctor()
//    }

    private void getappoiment() {

        appointment ap = url.getInstance().create(appointment.class);
        Call<List<appointmentModel>> appointmentModelCall = ap.getAppointment(url.token);
        appointmentModelCall.enqueue(new Callback<List<appointmentModel>>() {
            @Override
            public void onResponse(Call<List<appointmentModel>> call, Response<List<appointmentModel>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                    return;
                }
                   List<appointmentModel> list = response.body();


                AppointmentAdapater appointmentAdapater = new AppointmentAdapater(getContext(),list);
                recyclerView.setAdapter(appointmentAdapater);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Call<List<appointmentModel>> call, Throwable t) {

            }
        });
    }
}
