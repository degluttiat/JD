package com.example.jd;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<ItemClass> list;
    FragmentRecyclerView fragmentRecyclerView;

    public RecyclerViewAdapter(ArrayList<ItemClass> list, FragmentRecyclerView fragmentRecyclerView) {
        this.list = list;
        this.fragmentRecyclerView = fragmentRecyclerView;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        ItemClass itemClass = list.get(position);
        viewHolder.textBreakfast.setText(itemClass.getTextBreakfast());
        viewHolder.textLunch.setText(itemClass.getTextLunch());
        viewHolder.textSupper.setText(itemClass.getTextSupper());
        viewHolder.itemImage.setImageResource(itemClass.getImage());
        viewHolder.linearLayout2.setVisibility(View.GONE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(viewHolder.itemImage.getContext());
        String weihgt = prefs.getString("key" + position, "0.0");
        if (!weihgt.equals("0.0")) {
            viewHolder.editWeight.setText(weihgt);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textBreakfast;
        public TextView textLunch;
        public TextView textSupper;
        public ImageView itemImage;
        public LinearLayout linearLayout;
        public LinearLayout linearLayout2;
        public LinearLayout line;
        private final EditText editWeight;
        private final Button buttonOk;

        public ViewHolder(View itemView) {
            super(itemView);
            textBreakfast = itemView.findViewById(R.id.textBreakfast);
            textLunch = itemView.findViewById(R.id.textLunch);
            textSupper = itemView.findViewById(R.id.textSupper);
            itemImage = itemView.findViewById(R.id.itemImage);
            linearLayout = itemView.findViewById(R.id.linear);
            linearLayout.setOnClickListener(this);
            linearLayout2 = itemView.findViewById(R.id.linear2);
            line = itemView.findViewById(R.id.line);
            editWeight = itemView.findViewById(R.id.editWeight);
            buttonOk = itemView.findViewById(R.id.buttonOk);
            buttonOk.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear:
                    if (linearLayout2.getVisibility() == View.VISIBLE) {
                        linearLayout2.setVisibility(View.GONE);
                    } else {
                        TransitionManager.beginDelayedTransition(line);
                        linearLayout2.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.buttonOk:
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    String editText = editWeight.getText().toString();
                    if (!editText.equals("")) {
                        editor.putString("key" + getAdapterPosition(), editText);
                        editor.commit();
                        fragmentRecyclerView.updateGraph();
                    } else {
                        if (prefs.contains("key" + getAdapterPosition())){
                            editor.remove("key" + getAdapterPosition());
                            editor.commit();
                            fragmentRecyclerView.updateGraph();
                        }
                    }
                    break;

            }

        }
    }
}
