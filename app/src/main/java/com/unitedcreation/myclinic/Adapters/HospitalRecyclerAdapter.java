package com.unitedcreation.myclinic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.ui.MapsActivity;
import com.unitedcreation.myclinic.utils.StringUtils;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class HospitalRecyclerAdapter extends RecyclerView.Adapter <HospitalRecyclerAdapter.ViewHolder> {
    String hospital_list[]=null;
    String lats[]=null;
    String[] longs=null;
    Context context;
    public HospitalRecyclerAdapter(String[] array1,String[] array2,String[] array3){
        hospital_list=array1;
        lats=array2;
        longs=array3;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mHospitalName.setText(hospital_list[position]);
        holder.mRatingBar.setRating(5-position);
        holder.mHospitalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MapsActivity.class);
                intent.putExtra(StringUtils.NAME,hospital_list[position]);
                intent.putExtra(StringUtils.LATITUDE,lats[position]);
                intent.putExtra(StringUtils.LONGITUTE,longs[position]);
                context.startActivity(intent);
            }
        });

    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mHospitalName;
        ConstraintLayout mHospitalCard;
        RatingBar mRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHospitalName=itemView.findViewById(R.id.card_hospital_name);
            mRatingBar=itemView.findViewById(R.id.card_rating);
            mHospitalCard=itemView.findViewById(R.id.hospital_item);
        }
    }

    @Override
    public int getItemCount() {
        return hospital_list.length;
    }
}
