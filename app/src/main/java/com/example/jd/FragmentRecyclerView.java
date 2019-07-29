package com.example.jd;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class FragmentRecyclerView extends Fragment implements View.OnClickListener {

    public static final String ARGUMENT_PAGE_NUMBER = "ARGUMENT_PAGE_NUMBER";
    private GraphView mGraph;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_recycler_view, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.content);

        boolean isPortrait = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;

        LinearLayoutManager manager = new
                LinearLayoutManager(getContext(),
                isPortrait ? LinearLayoutManager.VERTICAL :
                        LinearLayoutManager.HORIZONTAL,
                false);

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        int listNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getList(listNumber));
        recyclerView.setAdapter(adapter);

        mGraph = rootView.findViewById(R.id.graph);
        updateGraph();


        return rootView;
    }

    private void updateGraph() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        mGraph.addSeries(series);
    }

    private ArrayList<ItemClass> getList(int listNumber) {
        ArrayList<ItemClass> list = new ArrayList<>();
        switch (listNumber) {
            case 1:
                list.add(new ItemClass(getString(R.string.breakfast4_1), getString(R.string.lunch4_1), getString(R.string.supper4_1), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast4_2), getString(R.string.lunch4_2), getString(R.string.supper4_2), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast4_3), getString(R.string.lunch4_3), getString(R.string.supper4_3), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast4_4), getString(R.string.lunch4_4), getString(R.string.supper4_4), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast4_5), getString(R.string.lunch4_5), getString(R.string.supper4_5), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast4_6), getString(R.string.lunch4_6), getString(R.string.supper4_6), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast4_7), getString(R.string.lunch4_7), getString(R.string.supper4_7), R.drawable.apples));
                break;
            case 2:
                list.add(new ItemClass(getString(R.string.breakfast3_1), getString(R.string.lunch3_1), getString(R.string.supper3_1), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3_2), getString(R.string.lunch3_2), getString(R.string.supper3_2), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3_3), getString(R.string.lunch3_3), getString(R.string.supper3_3), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3_4), getString(R.string.lunch3_4), getString(R.string.supper3_4), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3_5), getString(R.string.lunch3_5), getString(R.string.supper3_5), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3_6), getString(R.string.lunch3_6), getString(R.string.supper3_6), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3_7), getString(R.string.lunch3_7), getString(R.string.supper3_7), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3_8), getString(R.string.lunch3_8), getString(R.string.supper3_8), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3_9), getString(R.string.lunch3_9), getString(R.string.supper3_9), R.drawable.apples));
                break;
            case 3:
                list.add(new ItemClass(getString(R.string.breakfast2_1), getString(R.string.lunch2_1), getString(R.string.supper2_1), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_2), getString(R.string.lunch2_2), getString(R.string.supper2_2), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_3), getString(R.string.lunch2_3), getString(R.string.supper2_3), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_4), getString(R.string.lunch2_4), getString(R.string.supper2_4), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_5), getString(R.string.lunch2_5), getString(R.string.supper2_5), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_6), getString(R.string.lunch2_6), getString(R.string.supper2_6), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_7), getString(R.string.lunch2_7), getString(R.string.supper2_7), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_8), getString(R.string.lunch2_8), getString(R.string.supper2_8), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_9), getString(R.string.lunch2_9), getString(R.string.supper2_9), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_10), getString(R.string.lunch2_10), getString(R.string.supper2_10), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_11), getString(R.string.lunch2_11), getString(R.string.supper2_11), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_12), getString(R.string.lunch2_12), getString(R.string.supper2_12), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2_13), getString(R.string.lunch2_13), getString(R.string.supper2_13), R.drawable.apples));
                break;
            case 4:
                list.add(new ItemClass(getString(R.string.breakfast1), getString(R.string.lunch1), getString(R.string.supper1), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast2), getString(R.string.lunch2), getString(R.string.supper2), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast3), getString(R.string.lunch3), getString(R.string.supper3), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast4), getString(R.string.lunch4), getString(R.string.supper4), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast5), getString(R.string.lunch5), getString(R.string.supper5), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast6), getString(R.string.lunch6), getString(R.string.supper6), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast7), getString(R.string.lunch7), getString(R.string.supper7), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast8), getString(R.string.lunch8), getString(R.string.supper8), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast9), getString(R.string.lunch9), getString(R.string.supper9), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast10), getString(R.string.lunch10), getString(R.string.supper10), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast11), getString(R.string.lunch11), getString(R.string.supper11), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast12), getString(R.string.lunch12), getString(R.string.supper12), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast13), getString(R.string.lunch13), getString(R.string.supper13), R.drawable.apples));
                list.add(new ItemClass(getString(R.string.breakfast14), getString(R.string.lunch14), getString(R.string.supper14), R.drawable.apples));
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
