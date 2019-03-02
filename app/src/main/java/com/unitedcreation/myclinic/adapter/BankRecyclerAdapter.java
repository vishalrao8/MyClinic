package com.unitedcreation.myclinic.adapter;

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

public class BankRecyclerAdapter extends RecyclerView.Adapter <BankRecyclerAdapter.ViewHolder> {

    private String hospital_list[] = null;
    private String lats[] = null;
    private String[] longs = null;
    private Context context;

    public BankRecyclerAdapter(String[] array1, String[] array2, String[] array3){

        hospital_list = array1;
        lats = array2;
        longs = array3;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        //Inflating Layout item_bank for each entry on database
        //each entry has their own item_bank layout with unique position for differentiating
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
/**
 * here we are binding values to each view defined below in ViewHolder class
 */
        holder.mHospitalName.setText(hospital_list[position]);
        holder.mRatingBar.setRating(5 - position);
        holder.mHospitalCard.setOnClickListener(v -> {

            Intent intent = new Intent(context, MapsActivity.class);
            intent.putExtra(StringUtils.NAME, hospital_list[position]);
            intent.putExtra(StringUtils.LATITUDE, lats[position]);
            intent.putExtra(StringUtils.LONGITUTE, longs[position]);
            context.startActivity(intent);

        });
    }

    /**
     * here we are creating a class to handle layout events for each item_bank view
     *
     * We are defining Hospital Name ,Card of Hospital and its Rating bar
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mHospitalName;
        ConstraintLayout mHospitalCard;
        RatingBar mRatingBar;

        ViewHolder(@NonNull View itemView) {
/**
 * here we are linking each variables with appropriate views in item_bank layout
 */
            super(itemView);
            mHospitalName=itemView.findViewById(R.id.card_hospital_name);
            mRatingBar=itemView.findViewById(R.id.card_rating);
            mHospitalCard=itemView.findViewById(R.id.hospital_item);

        }
    }

    @Override
    public int getItemCount() {
        /**
         * this returns number of views to be displayed
         */
        return hospital_list.length;
    }
}
