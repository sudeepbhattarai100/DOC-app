package com.example.doctorappointmentsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.activity.BookingActivity;
import com.example.doctorappointmentsystem.activity.DoctorInfo;
import com.example.doctorappointmentsystem.activity.finalBooking;
import com.example.doctorappointmentsystem.model.doctor;
import com.example.doctorappointmentsystem.url.url;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends  RecyclerView.Adapter<DoctorAdapter.DocterViewHolder> {

    Context mcontext;
    List<doctor> doctorList;

    public DoctorAdapter(Context mcontext, List<doctor> doctorList){
        this.mcontext = mcontext;
        this.doctorList = doctorList;
    }


    @NonNull
    @Override
    public DoctorAdapter.DocterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.doctors,parent,false);
        return new DoctorAdapter.DocterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DocterViewHolder holder, int position) {
        doctor doc = doctorList.get(position);
        holder.txtdoctorname.setText(doc.getFirstName());
        holder.txtDoctorQualification.setText((doc.getQualification()));
        String imgPath = url.imagePath + doc.getProfileImage();
        Picasso.get().load(imgPath).into(holder.docterImg);
        final String id = doc.get_id();
       holder.doctorProf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(mcontext, finalBooking.class);
               i.putExtra("docId", id);
               mcontext.startActivity(i);
           }
       });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class DocterViewHolder extends RecyclerView.ViewHolder{
        CircleImageView docterImg;
        TextView txtdoctorname, txtDoctorQualification;
        LinearLayout doctorProf;


        public DocterViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorProf = itemView.findViewById(R.id.doctorProf);
            docterImg = itemView.findViewById(R.id.doctorImage);
            txtdoctorname = itemView.findViewById(R.id.doctorName);
            txtDoctorQualification= itemView.findViewById(R.id.doctorQualification);


        }
    }


}
