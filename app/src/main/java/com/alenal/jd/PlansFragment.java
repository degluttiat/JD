package com.alenal.jd;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class PlansFragment extends Fragment implements View.OnClickListener {

    MyFragmentCallBack myFragmentCallBack;
    LinearLayout button1;
    LinearLayout button2;
    LinearLayout button3;
    LinearLayout button4;

    public static PlansFragment newInstance() {
        PlansFragment fragment = new PlansFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_plans, container, false);

        button1 = rootView.findViewById(R.id.button);
        button2 = rootView.findViewById(R.id.button2);
        button3 = rootView.findViewById(R.id.button3);
        button4 = rootView.findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button:
                myFragmentCallBack.onBtnClick(1);
                break;
            case R.id.button2:
                myFragmentCallBack.onBtnClick(2);
                break;
            case R.id.button3:
                myFragmentCallBack.onBtnClick(3);
                break;
            case R.id.button4:
                myFragmentCallBack.onBtnClick(4);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myFragmentCallBack = (MyFragmentCallBack) context;

    }

    public interface MyFragmentCallBack {
        void onBtnClick(int i);
    }
}
