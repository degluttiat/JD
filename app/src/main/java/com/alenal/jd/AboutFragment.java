package com.alenal.jd;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutFragment extends Fragment {

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_about, container, false);

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("stopKey", 0);
        editor.apply();

        return rootView;
    }


}
