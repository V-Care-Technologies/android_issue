package com.example.recyclermulti;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclermulti.databinding.RvCheckboxItemBinding;
import com.example.recyclermulti.helper.GlobalData;
import com.example.recyclermulti.helper.PrefUtils;
import com.example.recyclermulti.models.res.OptionsItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.MyHolder> {
    Context context;
    List<OptionsItem> itemList;
    String isLanguageEnglish="";
    String answer_str="";

    public CheckboxAdapter(Context context, List<OptionsItem> itemList, String isLanguageEnglish, String answer_str) {
        this.context = context;
        this.itemList = itemList;
        this.isLanguageEnglish = isLanguageEnglish;
        this.answer_str = answer_str;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(RvCheckboxItemBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {




        if(!answer_str.isEmpty()) {
           /* String abc =answer_str;
            String[] parts = abc.split("|");
            List<String> stringlist = Arrays.asList(parts);
            for (int i = 0; i < itemList.size(); i++) {
                if(itemList.get(i).getId().equals(stringlist.get(i))){
                    itemList.get(holder.getAdapterPosition()).setChecked(true);
                }
            }*/
        }
        holder.binding.cbTemplate.setChecked(itemList.get(holder.getAdapterPosition()).isChecked());

        if(isLanguageEnglish.equals("1")){
            holder.binding.cbTemplate.setText(itemList.get(holder.getAdapterPosition()).getOptions());
        }else{
            holder.binding.cbTemplate.setText(itemList.get(holder.getAdapterPosition()).getOptionsHn());
        }





        holder.binding.cbTemplate.setTag(position);

        holder.binding.cbTemplate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (itemList.get(holder.getAdapterPosition()).isChecked()) {
                    itemList.get(holder.getAdapterPosition()).setChecked(false);

                } else {
                    itemList.get(holder.getAdapterPosition()).setChecked(true);
                }
                Log.d("checked", new Gson().toJson(itemList));

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        RvCheckboxItemBinding binding;

        public MyHolder(RvCheckboxItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
