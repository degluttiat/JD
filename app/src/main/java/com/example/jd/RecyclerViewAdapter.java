package com.example.jd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<ItemClass> list;

    public RecyclerViewAdapter(ArrayList<ItemClass> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        ItemClass itemClass = list.get(i);
        viewHolder.textBreakfast.setText(itemClass.getTextBreakfast());
        viewHolder.textLunch.setText(itemClass.getTextLunch());
        viewHolder.textSupper.setText(itemClass.getTextSupper());
        viewHolder.itemImage.setImageResource(itemClass.getImage());
        viewHolder.linearLayout2.setVisibility(View.GONE);

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
        }

        @Override
        public void onClick(View v) {
            if (linearLayout2.getVisibility() == View.VISIBLE){
                linearLayout2.setVisibility(View.GONE);
            } else {
                TransitionManager.beginDelayedTransition(line);
                linearLayout2.setVisibility(View.VISIBLE);
            }

        }
    }
}
