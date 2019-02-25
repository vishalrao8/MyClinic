package com.unitedcreation.myclinic.ui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.Adapters.HospitalRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommonFragment extends Fragment {
    String gov_hospital_names[]={"Jeevan","STM","Cryosave","Stem cyte india"};
    String gov_hospital_lat[]={"13.116800","28.632430","28.632430","12.930584"};
    String gov_hospital_long[]={"80.275833","77.218790","77.218790","77.692057"};
    String private_hospital_names[]={"LifeCell","Cryo-Cell International","Relicord","Lifecell Babycord Stem Cells Bank","ReeLabs"};
    String private_hospital_lat[]={"12.937658","19.40728","43.002701","36.1999","72.833291"};
    String private_hospital_long[]={"77.627754","-99.271225","-82.802469","-78.7222","18.936011"};
    String text="";
    public CommonFragment(String str){
        text=str;
    }
    RecyclerView mRecyclerView;
    HospitalRecyclerAdapter hospitalRecyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bank_category,container,false);
         mRecyclerView = (RecyclerView) view.findViewById(R.id.common_rv);
         switch (text){
             case "government":
                 hospitalRecyclerAdapter=new HospitalRecyclerAdapter(gov_hospital_names,gov_hospital_lat,gov_hospital_long);
                 break;
             case "private":
                 hospitalRecyclerAdapter=new HospitalRecyclerAdapter(private_hospital_names,private_hospital_lat,private_hospital_long);
         }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(hospitalRecyclerAdapter);
        return view;
    }
}
