package com.example.sidrajawaid.demorecyclerviewwithswipping;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedViews extends Fragment {
    RecyclerView recyclerView;
    ArrayList data_list;
    public SavedViews() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_saved_views, container, false);
        recyclerView=view.findViewById(R.id.rv2);
        data_list=(ArrayList) getArguments().getSerializable("array");
        RecyclerAdapterClass rac=new RecyclerAdapterClass(getContext(),data_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(rac);
            RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
         return view;
    }

}
