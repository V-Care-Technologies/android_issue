package com.example.recyclermulti;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclermulti.R;
import com.example.recyclermulti.databinding.RvDependentItemBinding;
import com.example.recyclermulti.databinding.RvTemplateItemBinding;
import com.example.recyclermulti.helper.GlobalData;
import com.example.recyclermulti.helper.PrefUtils;
import com.example.recyclermulti.models.res.OptionsItem;
import com.example.recyclermulti.models.res.QuestionsDataItem;
import com.example.recyclermulti.models.res.dependentquestionres.DependentQuestionResponse;

import java.util.List;

public class DependentNewAdapter extends RecyclerView.Adapter<DependentNewAdapter.MyHolder> {
    List<QuestionsDataItem> itemList;
    Context context;
    String selected_id = "";
    int p_position;
    String isLanguageEnglish="";

    public DependentNewAdapter(List<QuestionsDataItem> itemList, Context context, String selected_id, int p_position, String isLanguageEnglish) {
        this.itemList = itemList;
        this.context = context;
        this.selected_id = selected_id;
        this.p_position = p_position;
        this.isLanguageEnglish = isLanguageEnglish;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(RvDependentItemBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


        holder.binding.tvTitleText.setVisibility(View.VISIBLE);
        Float textsize =
                Float.valueOf(context.getResources().getDimensionPixelSize(com.intuit.ssp.R.dimen._5ssp));
        Typeface typeface = ResourcesCompat.getFont(context, com.example.recyclermulti.R.font.inter_regular);
        if (itemList.get(holder.getAdapterPosition()).getType().equals("1")) {
            holder.binding.rgTemplate.setVisibility(View.VISIBLE);
            holder.binding.rgTemplate.removeAllViews();

            if (isLanguageEnglish.equals("1")) {
                holder.binding.tvTitleText.setText(itemList.get(holder.getAdapterPosition()).getQuestionHn());

            }else{
                holder.binding.tvTitleText.setText(itemList.get(holder.getAdapterPosition()).getQuestionHn());

            }



            for (int i = 0; i < itemList.get(holder.getAdapterPosition()).getOptions().size(); i++) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setId(Integer.parseInt(itemList.get(holder.getAdapterPosition()).getOptions().get(i).getId()));

                if (isLanguageEnglish.equals("2")) {
                    radioButton.setText(itemList.get(holder.getAdapterPosition()).getOptions().get(i).getOptionsHn());

                }else{
                    radioButton.setText(itemList.get(holder.getAdapterPosition()).getOptions().get(i).getOptions());


                }


                radioButton.setTag(itemList.get(holder.getAdapterPosition()).getOptions().get(i).isDependent());
                radioButton.setButtonTintList(ColorStateList.valueOf(context.getColor(R.color.black)));
                radioButton.setTypeface(typeface);
                radioButton.setTextSize(textsize);
                holder.binding.rgTemplate.addView(radioButton, new RadioGroup.LayoutParams(
                                RadioGroup.LayoutParams.WRAP_CONTENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT
                        )
                );
            }


        } else if (itemList.get(holder.getAdapterPosition()).getType().equals("2")) {

            holder.binding.tvTitleText.setVisibility(View.VISIBLE);

            if (isLanguageEnglish.equals("2")) {
                holder.binding.tvTitleText.setText(itemList.get(holder.getAdapterPosition()).getQuestionHn());

            }else{
                holder.binding.tvTitleText.setText(itemList.get(holder.getAdapterPosition()).getQuestion());

            }

            if (itemList.get(holder.getAdapterPosition()).getQuestion().isEmpty()) {
                holder.binding.tvTitleText.setVisibility(View.GONE);
            } else {
                holder.binding.tvTitleText.setVisibility(View.VISIBLE);

            }
            holder.binding.rvCbtemplate.setVisibility(View.VISIBLE);
            CheckboxAdapter checkboxAdapter = new CheckboxAdapter(context, itemList.get(holder.getAdapterPosition()).getOptions(),isLanguageEnglish,"");
            holder.binding.rvCbtemplate.setAdapter(checkboxAdapter);
            holder.binding.rvCbtemplate.setLayoutManager(new LinearLayoutManager(context));
        } else if (itemList.get(holder.getAdapterPosition()).getType().equals("3")) {
            holder.binding.tvTitleText.setVisibility(View.VISIBLE);
            if (isLanguageEnglish.equals("2")) {
                holder.binding.tvTitleText.setText(itemList.get(holder.getAdapterPosition()).getQuestionHn());

            }else{
                holder.binding.tvTitleText.setText(itemList.get(holder.getAdapterPosition()).getQuestion());

            }
            holder.binding.etTemplate.setVisibility(View.VISIBLE);
            holder.binding.etTemplate.setInputType(InputType.TYPE_CLASS_TEXT);

        } else if (itemList.get(holder.getAdapterPosition()).getType().equals("4")) {
            holder.binding.tvTitleText.setVisibility(View.VISIBLE);

            if (isLanguageEnglish.equals("2")) {
                holder.binding.tvTitleText.setText(itemList.get(holder.getAdapterPosition()).getQuestionHn());

            }else{
                holder.binding.tvTitleText.setText(itemList.get(holder.getAdapterPosition()).getQuestion());

            }
            holder.binding.etTemplate.setVisibility(View.VISIBLE);
            holder.binding.etTemplate.setInputType(InputType.TYPE_CLASS_NUMBER);

        } else {


        }


        holder.binding.etTemplate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemList.get(holder.getAdapterPosition()).setPassData(holder.binding.etTemplate.getText().toString());
                itemList.get(holder.getAdapterPosition()).setPassId(itemList.get(holder.getAdapterPosition()).getId());

                if (isLanguageEnglish.equals("2")) {
                    itemList.get(holder.getAdapterPosition()).setQuestionName(itemList.get(holder.getAdapterPosition()).getQuestionHn());

                }else{
                    itemList.get(holder.getAdapterPosition()).setQuestionName(itemList.get(holder.getAdapterPosition()).getQuestion());

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        holder.binding.etOtherInformation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemList.get(holder.getAdapterPosition()).setPassData(holder.binding.etOtherInformation.getText().toString());
                itemList.get(holder.getAdapterPosition()).setPassId(itemList.get(holder.getAdapterPosition()).getId());

                if (isLanguageEnglish.equals("2")) {
                    itemList.get(holder.getAdapterPosition()).setQuestionName(itemList.get(holder.getAdapterPosition()).getQuestionHn());

                }else{
                    itemList.get(holder.getAdapterPosition()).setQuestionName(itemList.get(holder.getAdapterPosition()).getQuestion());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        itemList.get(holder.getAdapterPosition()).setPassData("");
        itemList.get(holder.getAdapterPosition()).setPassId("");


        holder.binding.rgTemplate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rgTemplate, int i) {


                itemList.get(holder.getAdapterPosition()).setPassId(itemList.get(holder.getAdapterPosition()).getId());


                if (isLanguageEnglish.equals("2")) {
                    itemList.get(holder.getAdapterPosition()).setQuestionName(itemList.get(holder.getAdapterPosition()).getQuestionHn());

                }else{
                    itemList.get(holder.getAdapterPosition()).setQuestionName(itemList.get(holder.getAdapterPosition()).getQuestion());

                }
                itemList.get(holder.getAdapterPosition()).setSelected_option_id(String.valueOf(rgTemplate.getCheckedRadioButtonId()));


                for (int j = 0; j < itemList.get(holder.getAdapterPosition()).getOptions().size(); j++) {

                    if (String.valueOf(rgTemplate.getCheckedRadioButtonId())
                            .equals(itemList.get(holder.getAdapterPosition()).getOptions().get(j).getId())
                    ) {
                        if (isLanguageEnglish.equals("2")) {
                            itemList.get(holder.getAdapterPosition()).setPassData(itemList.get(holder.getAdapterPosition()).getOptions().get(j).getOptionsHn().toString());

                        }else{
                            itemList.get(holder.getAdapterPosition()).setPassData(itemList.get(holder.getAdapterPosition()).getOptions().get(j).getOptions().toString());

                        }
                    }
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RvDependentItemBinding binding;

        public MyHolder(RvDependentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
