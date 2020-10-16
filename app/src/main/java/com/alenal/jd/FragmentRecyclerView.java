package com.alenal.jd;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class FragmentRecyclerView extends Fragment implements View.OnClickListener {

    public static final String ARGUMENT_PAGE_NUMBER = "ARGUMENT_PAGE_NUMBER";
    private LineChart chart;
    private RecyclerView recyclerView;
    private Button btnBuyList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_recycler_view, container, false);

        recyclerView = rootView.findViewById(R.id.content);

        boolean isPortrait = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;

        LinearLayoutManager manager = new
                LinearLayoutManager(getContext(),
                isPortrait ? LinearLayoutManager.VERTICAL :
                        LinearLayoutManager.HORIZONTAL,
                false);

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        createOrRefreshAdapter();

        chart = rootView.findViewById(R.id.chart);

        setDescription(0);
        updateGraph();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (!prefs.contains("checked")) {
            createAndShowDialog();
        }

        btnBuyList = rootView.findViewById(R.id.buyList);
        btnBuyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndShowBuyListDialog();
            }
        });

        return rootView;
    }

    private void setDescription(float x) {
        Description description = new Description();
        description.setTextSize(10);
        if (x > 0) {
            description.setText(getString(R.string.lost) + String.valueOf(x) + getString(R.string.kilograms));
        } else if (x < 0) {
            description.setText(getString(R.string.gained) + Math.abs(x) + getString(R.string.kilograms));
        } else {
            description.setText("");
        }
        chart.setDescription(description);
        //chart.setDrawGridBackground(true);
    }

    public void createAndShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(recyclerView.getContext());
        View content = getLayoutInflater().inflate(R.layout.list_msg_dialog, null);

        final CheckBox checkBox = content.findViewById(R.id.checkBoxId);

        builder.setTitle(R.string.important);
        builder.setView(content);
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (checkBox.isChecked()) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("checked", true);
                            editor.commit();
                        }
                        dialog.cancel();
                    }
                });
        final AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void createOrRefreshAdapter() {
        int listNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getList(listNumber),
                this);
        recyclerView.setAdapter(adapter);
    }

    public void updateGraph() {
        List<Entry> entries = new ArrayList<>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(chart.getContext());

        for (int i = 0; i < 14; i++) {
            if (prefs.contains("key" + i)) {
                float num = Float.parseFloat(prefs.getString("key" + i, "0.0"));
                if (num > 0.0) {
                    entries.add(new Entry(i, num));
                }
            }
        }

        if (!entries.isEmpty()) {
            float x = entries.get(0).getY() - entries.get(entries.size() - 1).getY();
            setDescription(x);
        }

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.chartWeight));

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int v = (int) value + 1;
                return String.valueOf(v);
            }

        };
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.setDrawBorders(true);
        chart.setNoDataText("Chart is empty");
        chart.invalidate(); // refresh

    }

    private void createAndShowBuyListDialog() {
        int listNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(recyclerView.getContext());
        final String buyList = getBuyList(listNumber);

        builder.setTitle("Buy List");
        builder.setMessage(buyList);
        builder.setNegativeButton(R.string.share,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, buyList);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        dialog.cancel();

                    }
                });
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        final AlertDialog dialog = builder.create();

        dialog.show();
    }

    private String getBuyList(int listNumber) {

        String buyList = "";
        switch (listNumber){
            case 1:
                buyList = getString(R.string.seven_day_buy_list);
                break;
            case 2:
                buyList = getString(R.string.nine_day_buy_list);
                break;
            case 3:
                buyList = getString(R.string.thirteen_day_buy_list);
                break;
            case 4:
                buyList = getString(R.string.fourteen_day_buy_list);
                break;
        }
        return buyList;
    }

    private ArrayList<ItemClass> getList(int listNumber) {
        ArrayList<ItemClass> list = new ArrayList<>();
        switch (listNumber) {
            case 1:
                list.add(new ItemClass(getString(R.string.breakfast4_1), getString(R.string.lunch4_1), getString(R.string.supper4_1), R.drawable.day31));
                list.add(new ItemClass(getString(R.string.breakfast4_2), getString(R.string.lunch4_2), getString(R.string.supper4_2), R.drawable.day32));
                list.add(new ItemClass(getString(R.string.breakfast4_3), getString(R.string.lunch4_3), getString(R.string.supper4_3), R.drawable.day33));
                list.add(new ItemClass(getString(R.string.breakfast4_4), getString(R.string.lunch4_4), getString(R.string.supper4_4), R.drawable.day34));
                list.add(new ItemClass(getString(R.string.breakfast4_5), getString(R.string.lunch4_5), getString(R.string.supper4_5), R.drawable.day35));
                list.add(new ItemClass(getString(R.string.breakfast4_6), getString(R.string.lunch4_6), getString(R.string.supper4_6), R.drawable.day36));
                list.add(new ItemClass(getString(R.string.breakfast4_7), getString(R.string.lunch4_7), getString(R.string.supper4_7), R.drawable.day37));
                break;
            case 2:
                list.add(new ItemClass(getString(R.string.breakfast3_1), getString(R.string.lunch3_1), getString(R.string.supper3_1), R.drawable.day21));
                list.add(new ItemClass(getString(R.string.breakfast3_2), getString(R.string.lunch3_2), getString(R.string.supper3_2), R.drawable.day22));
                list.add(new ItemClass(getString(R.string.breakfast3_3), getString(R.string.lunch3_3), getString(R.string.supper3_3), R.drawable.day23));
                list.add(new ItemClass(getString(R.string.breakfast3_4), getString(R.string.lunch3_4), getString(R.string.supper3_4), R.drawable.day24));
                list.add(new ItemClass(getString(R.string.breakfast3_5), getString(R.string.lunch3_5), getString(R.string.supper3_5), R.drawable.day25));
                list.add(new ItemClass(getString(R.string.breakfast3_6), getString(R.string.lunch3_6), getString(R.string.supper3_6), R.drawable.day26));
                list.add(new ItemClass(getString(R.string.breakfast3_7), getString(R.string.lunch3_7), getString(R.string.supper3_7), R.drawable.day27));
                list.add(new ItemClass(getString(R.string.breakfast3_8), getString(R.string.lunch3_8), getString(R.string.supper3_8), R.drawable.day28));
                list.add(new ItemClass(getString(R.string.breakfast3_9), getString(R.string.lunch3_9), getString(R.string.supper3_9), R.drawable.day29));
                break;
            case 3:
                list.add(new ItemClass(getString(R.string.breakfast2_1), getString(R.string.lunch2_1), getString(R.string.supper2_1), R.drawable.day1));
                list.add(new ItemClass(getString(R.string.breakfast2_2), getString(R.string.lunch2_2), getString(R.string.supper2_2), R.drawable.day2));
                list.add(new ItemClass(getString(R.string.breakfast2_3), getString(R.string.lunch2_3), getString(R.string.supper2_3), R.drawable.day3));
                list.add(new ItemClass(getString(R.string.breakfast2_4), getString(R.string.lunch2_4), getString(R.string.supper2_4), R.drawable.day4));
                list.add(new ItemClass(getString(R.string.breakfast2_5), getString(R.string.lunch2_5), getString(R.string.supper2_5), R.drawable.day5));
                list.add(new ItemClass(getString(R.string.breakfast2_6), getString(R.string.lunch2_6), getString(R.string.supper2_6), R.drawable.day6));
                list.add(new ItemClass(getString(R.string.breakfast2_7), getString(R.string.lunch2_7), getString(R.string.supper2_7), R.drawable.day7));
                list.add(new ItemClass(getString(R.string.breakfast2_8), getString(R.string.lunch2_8), getString(R.string.supper2_8), R.drawable.day8));
                list.add(new ItemClass(getString(R.string.breakfast2_9), getString(R.string.lunch2_9), getString(R.string.supper2_9), R.drawable.day9));
                list.add(new ItemClass(getString(R.string.breakfast2_10), getString(R.string.lunch2_10), getString(R.string.supper2_10), R.drawable.day10));
                list.add(new ItemClass(getString(R.string.breakfast2_11), getString(R.string.lunch2_11), getString(R.string.supper2_11), R.drawable.day11));
                list.add(new ItemClass(getString(R.string.breakfast2_12), getString(R.string.lunch2_12), getString(R.string.supper2_12), R.drawable.day12));
                list.add(new ItemClass(getString(R.string.breakfast2_13), getString(R.string.lunch2_13), getString(R.string.supper2_13), R.drawable.day13));
                break;
            case 4:
                list.add(new ItemClass(getString(R.string.breakfast1), getString(R.string.lunch1), getString(R.string.supper1), R.drawable.day91));
                list.add(new ItemClass(getString(R.string.breakfast2), getString(R.string.lunch2), getString(R.string.supper2), R.drawable.day92));
                list.add(new ItemClass(getString(R.string.breakfast3), getString(R.string.lunch3), getString(R.string.supper3), R.drawable.day93));
                list.add(new ItemClass(getString(R.string.breakfast4), getString(R.string.lunch4), getString(R.string.supper4), R.drawable.day94));
                list.add(new ItemClass(getString(R.string.breakfast5), getString(R.string.lunch5), getString(R.string.supper5), R.drawable.day95));
                list.add(new ItemClass(getString(R.string.breakfast6), getString(R.string.lunch6), getString(R.string.supper6), R.drawable.day96));
                list.add(new ItemClass(getString(R.string.breakfast7), getString(R.string.lunch7), getString(R.string.supper7), R.drawable.day97));
                list.add(new ItemClass(getString(R.string.breakfast8), getString(R.string.lunch8), getString(R.string.supper8), R.drawable.day98));
                list.add(new ItemClass(getString(R.string.breakfast9), getString(R.string.lunch9), getString(R.string.supper9), R.drawable.day99));
                list.add(new ItemClass(getString(R.string.breakfast10), getString(R.string.lunch10), getString(R.string.supper10), R.drawable.day910));
                list.add(new ItemClass(getString(R.string.breakfast11), getString(R.string.lunch11), getString(R.string.supper11), R.drawable.day911));
                list.add(new ItemClass(getString(R.string.breakfast12), getString(R.string.lunch12), getString(R.string.supper12), R.drawable.day912));
                list.add(new ItemClass(getString(R.string.breakfast13), getString(R.string.lunch13), getString(R.string.supper13), R.drawable.day913));
                list.add(new ItemClass(getString(R.string.breakfast14), getString(R.string.lunch14), getString(R.string.supper14), R.drawable.day914));
                break;
        }
        return list;
    }

    public static FragmentRecyclerView newInstance(int position) {
        FragmentRecyclerView fragmentRecyclerView = new FragmentRecyclerView();


        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, position);
        fragmentRecyclerView.setArguments(arguments);

        return fragmentRecyclerView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linear:
                break;
        }

    }
}
