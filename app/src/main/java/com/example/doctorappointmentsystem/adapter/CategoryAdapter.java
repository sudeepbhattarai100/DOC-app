package com.example.doctorappointmentsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentsystem.R;
import com.example.doctorappointmentsystem.activity.BookingActivity;
import com.example.doctorappointmentsystem.model.category;
import com.example.doctorappointmentsystem.url.url;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context mcontext;
    List<category> categoryList;

    public CategoryAdapter(Context mcontext, List<category> categoryList){
        this.mcontext = mcontext;
        this.categoryList = categoryList;
    }


    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.categories,parent,false);
        return new CategoryAdapter.CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        final category cat = categoryList.get(position);
        holder.catName.setText(cat.getCategoryName());
        String imgPath = url.imagePath + cat.getCategoryImage();
        Picasso.get().load(imgPath).into(holder.catImage);
        holder.catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext, BookingActivity.class);
                i.putExtra("category",cat.getCategoryName());
                mcontext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        CircleImageView catImage;
        TextView catName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            catImage = itemView.findViewById(R.id.categoryImage);
            catName = itemView.findViewById(R.id.categoryName);


        }
    }
}
