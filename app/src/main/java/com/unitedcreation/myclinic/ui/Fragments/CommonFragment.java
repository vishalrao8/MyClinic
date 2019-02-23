package com.unitedcreation.myclinic.ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.utils.Adapters.HospitalRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommonFragment extends Fragment {
    String gov_hospital_names[]={"Jeevan","STM","Cryosave","Stem cyte india"};
    String private_hospital_names[]={"LifeCell","Cryo-Cell International","Relicord","Lifecell Babycord Stem Cells Bank","ReeLabs"};
    String text="";
    public CommonFragment(String str){
        text=str;
    }
    RecyclerView mRecyclerView;
    HospitalRecyclerAdapter hospitalRecyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_common,container,false);
         mRecyclerView = (RecyclerView) view.findViewById(R.id.common_rv);
         switch (text){
             case "government":
                 hospitalRecyclerAdapter=new HospitalRecyclerAdapter(gov_hospital_names);
                 break;
             case "private":
                 hospitalRecyclerAdapter=new HospitalRecyclerAdapter(private_hospital_names);
         }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(hospitalRecyclerAdapter);
        return view;
    }
}
