package com.example.easyrestapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyrestapp.databinding.FragmentOpenTableBinding;
import com.example.easyrestapp.databinding.FragmentTablesBinding;

import java.util.ArrayList;
import java.util.List;


public class OpenTableFragment extends Fragment {



    FragmentOpenTableBinding binding;
    List<Table> tables;
    int currentTable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tables=new ArrayList<>();
        for (int i=0;i<20;i++){
            tables.add(new Table(String.valueOf(i), "Note " + i, String.valueOf(i+1), i*10.0, (int)(i*10.0)/(i+1)));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentOpenTableBinding.inflate(inflater, container, false);
        View v=binding.getRoot();
        currentTable=OpenTableFragmentArgs.fromBundle(getArguments()).getChosenTable();
        Log.d("tag"," "+currentTable);

        return v;
    }
}