package com.example.recyclermulti.MultiAdapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclermulti.databinding.RvMultiTypeBinding;
import com.example.recyclermulti.models.MultiEtModel.MultiModel;

import java.util.List;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MyHolder> {
    Context context;
    public List<MultiModel> list;

    public MultiAdapter(Context context, List<MultiModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(RvMultiTypeBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.binding.tilSocialGr.setHint(list.get(holder.getAdapterPosition()).getSocial_gr());
        holder.binding.tilNo.setHint(list.get(holder.getAdapterPosition()).getNo());


        holder.binding.etNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                list.get(position).setNo_value(holder.binding.etNo.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.binding.etSocialGr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(position).setSocial_gr_value(holder.binding.etSocialGr.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        holder.binding.btnAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.add(new MultiModel((String) holder.binding.tilSocialGr.getHint(), (String) holder.binding.tilNo.getHint()));
                notifyItemInserted(holder.getAdapterPosition());
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


        list.get(holder.getAdapterPosition()).setSocial_gr_value("");
        list.get(holder.getAdapterPosition()).setNo_value("");

    }

    public void updateList(MultiModel updatelist) {
        list.add(updatelist);
        notifyItemInserted(list.size());
    }

    public void removeList(MultiModel multiModel) {
        list.remove(list.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RvMultiTypeBinding binding;

        public MyHolder(RvMultiTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
