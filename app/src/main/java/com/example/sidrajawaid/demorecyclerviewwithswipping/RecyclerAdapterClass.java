package com.example.sidrajawaid.demorecyclerviewwithswipping;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapterClass extends RecyclerView.Adapter <RecyclerAdapterClass.MyViewHolder>

{
    Context context;
    ArrayList<ModelClass> arrayList;
    View view;
    ArrayList saveditems=new ArrayList();
    public RecyclerAdapterClass(Context context,ArrayList<ModelClass>modelClassArrayList) {
        this.context=context;
        this.arrayList=modelClassArrayList;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text1;
        TextView text2;
        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            text1=itemView.findViewById(R.id.text1);
            text2=itemView.findViewById(R.id.text2);
        }
    }
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewlayout,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelClass modelclass=arrayList.get(position);
        holder.image.setImageResource(modelclass.getImageview());
        holder.text1.setText(modelclass.getTextview1());
        holder.text2.setText(modelclass.getTextview2());

    }
    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size():0;
    }
    public void setArrayList(ArrayList<ModelClass> arrayList)
    {
        this.saveditems=arrayList;
        notifyDataSetChanged();
    }
    public void dismissView(int pos)
    {
        arrayList.remove(pos);
        this.notifyItemRemoved(pos);
        //this.notifyItemChanged(pos);
        this.notifyItemRangeChanged(pos,arrayList.size());
    }
    public void addView(int pos)
    {
        Log.i("RecyclerAdapterClass","data is "+arrayList.get(pos).getImageview()+" "+arrayList.get(pos).getTextview1()+" "+arrayList.get(pos).getTextview2());
        //saveditems=new ArrayList<>();
        saveditems.add(arrayList.get(pos));
        arrayList.remove(pos);
        this.notifyDataSetChanged();
        this.notifyItemRangeChanged(pos,arrayList.size());
    }
    public ArrayList<ModelClass> getArrayList() {
        //notifyDataSetChanged();
            return saveditems;
    }

}
