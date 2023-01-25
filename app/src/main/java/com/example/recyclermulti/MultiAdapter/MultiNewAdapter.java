package com.example.recyclermulti.MultiAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclermulti.databinding.RvMultiTypeBinding;
import com.example.recyclermulti.databinding.RvThreeTypeBinding;
import com.example.recyclermulti.models.MultiEtModel.MultiModel;
import com.example.recyclermulti.models.MultiEtModel.MultiModelThree;

import java.util.List;

public class MultiNewAdapter extends RecyclerView.Adapter<MultiNewAdapter.MyHolder> {
    Context context;
   public List<MultiModelThree> list;

    public MultiNewAdapter(Context context, List<MultiModelThree> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(RvThreeTypeBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.binding.tilSocialGr.setHint("Type");
        holder.binding.tilNo.setHint("No");
        holder.binding.tilNoDependent.setHint("Families dependent");


        holder.binding.btnAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.add(new MultiModelThree());
                notifyItemInserted(list.size());
            }
        });

        holder.binding.btnRemoveAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() != 1) {
                    list.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                } else {
                    Toast.makeText(context, "minimum should be one", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    public void updateList(MultiModelThree updatelist){
        list.add(updatelist);
        notifyItemInserted(list.size());
    }
    public void removeList(MultiModelThree multiModel){
        list.remove(list.size()-1);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RvThreeTypeBinding binding;
        public MyHolder(RvThreeTypeBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
