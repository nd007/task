package com.example.mymachinetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymachinetest.Model.dataModel;
import com.example.mymachinetest.Model.petListmodel;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {

Context context;
List<petListmodel> petListModelArray;

    public DataListAdapter(Context context, List<petListmodel> petListModelArray) {
        this.context = context;
        this.petListModelArray = petListModelArray;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DataListAdapter.MyViewHolder holder, int position) {
         petListmodel posts=petListModelArray.get(position);
         holder.title.setText(posts.getPetName());
         holder.dob.setText(posts.getDateOfBirth());
         holder.pet_id.setText(posts.getId());
         holder.name.setText(posts.getPetParentName());
//        Glide.with(context).load(posts.getThumbnailURL()).apply(RequestOptions.centerCropTransform()).into(holder.imageView);
//        Glide.
//                with(context)
//                .load(context.getResources().getDrawable(R.drawable.human_icon.png))
//                .into(holder.petImage);
//holder.petImage.setImageDrawable();

    }

    @Override
    public int getItemCount() {
        return petListModelArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView title,dob,pet_id,name;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            petImage=(ImageView)itemView.findViewById(R.id.imageview);
            title=(TextView) itemView.findViewById(R.id.title);
            dob=(TextView) itemView.findViewById(R.id.dob);
            pet_id=(TextView) itemView.findViewById(R.id.pet_id);
            name=(TextView) itemView.findViewById(R.id.name);

        }
    }
}
