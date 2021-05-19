package com.example.doctorappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.api.appointment;
import com.example.doctorappointmentsystem.model.appointmentModel;
import com.example.doctorappointmentsystem.url.url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentAdapater extends  RecyclerView.Adapter<AppointmentAdapater.AppointmentViewHolder> {

    Context mcontext;
    List<appointmentModel> lstappoint;

    public AppointmentAdapater(Context mcontext,List<appointmentModel> lstappoint){
        this.mcontext = mcontext;
        this.lstappoint = lstappoint;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.appointment,parent,false);
        return new AppointmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {

        final appointmentModel  apoint = lstappoint.get(position);

        holder.txtdate.setText(apoint.getAppointmentDate());
        holder.txttime.setText(apoint.getAppointmentTime());
        holder.txtquery.setText(apoint.getQuery());


        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(apoint.get_id());
                appointment appointmentApi = url.getInstance().create(appointment.class);
                Call<appointmentModel> appointmentCall = appointmentApi.deleteAppoint(url.token, apoint.get_id());

                appointmentCall.enqueue(new Callback<appointmentModel>() {
                    @Override
                    public void onResponse(Call<appointmentModel> call, Response<appointmentModel> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(mcontext, "Couldn't cancel appointment", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(mcontext, "Appointment Canceled Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<appointmentModel> call, Throwable t) {
                        Toast.makeText(mcontext, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return lstappoint.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder{
        TextView txtdate,txttime, txtquery;
        Button btnCancel;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
           txtdate = itemView.findViewById(R.id.tvDoctor);
           txttime  = itemView.findViewById(R.id.tvTime);
            txtquery  = itemView.findViewById(R.id.tvQuery);
            btnCancel = itemView.findViewById(R.id.btnCancel);

        }
    }
}
