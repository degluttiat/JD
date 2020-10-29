package com.alenal.jd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        setWeightString(viewHolder, position);
    }

    private void setWeightString(@NonNull ViewHolder viewHolder, int position) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences
                (viewHolder.itemImage.getContext());
        String weight = prefs.getString("key" + position, "0.0");
        if (!weight.equals("0.0")) {
            viewHolder.editWeight.setText(weight);
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

            editWeight.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        onBtnOkClick(v);
                    }
                    return false;
                }
            });

        }

        @SuppressLint("ApplySharedPref")
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
                    onBtnOkClick(v);
                    break;
            }

        }

        private void onBtnOkClick(View v) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
            SharedPreferences.Editor editor = prefs.edit();
            String editText = editWeight.getText().toString();
            if (!editText.isEmpty()) {
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
            InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }

            linearLayout2.setVisibility(View.GONE);
            Toast.makeText(v.getContext(), R.string.graph_updated, Toast.LENGTH_SHORT).show();
        }
    }
}
