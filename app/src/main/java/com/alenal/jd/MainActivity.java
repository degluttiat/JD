package com.alenal.jd;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PlansFragment.MyFragmentCallBack {

    private ViewPager mPager;
    private FragmentAdapter mFirstFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Меняем цвет текста в ActionBar*/
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#736969'>Japanese Diet </font>"));

        /*Убирает теневую линию между ActionBar и TabLayout*/
        getSupportActionBar().setElevation(0);

        mPager = findViewById(R.id.pager);
        mFirstFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mPager.setAdapter(mFirstFragmentAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        int intFragment = prefs.getInt("stopKey", 0);
        if (intFragment != 0) {
            onBtnClick(intFragment);
        }

    }

    @Override
    public void onBtnClick(int i) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("stopKey", i);
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FragmentRecyclerView.newInstance(i), "RecFragTag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                FragmentRecyclerView fragment = (FragmentRecyclerView) getSupportFragmentManager()
                        .findFragmentByTag("RecFragTag");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                for (int i = 0; i < 14; i++) {
                    if (prefs.contains("key" + i)) {
                        editor.remove("key" + i);
                        editor.commit();
                    }
                }
                if (fragment != null) {
                    fragment.updateGraph();
                    Toast.makeText(this, R.string.graph_cleared, Toast.LENGTH_SHORT).show();
                    fragment.createOrRefreshAdapter();
                }
                return true;
            case R.id.how_to_use:
                createAndShowDialog();

            case R.id.myProfile:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MyProfileFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void createAndShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View content = getLayoutInflater().inflate(R.layout.list_msg_dialog, null);

        final CheckBox checkBox = content.findViewById(R.id.checkBoxId);

        builder.setTitle(R.string.important);
        builder.setView(content);
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (checkBox.isChecked()) {
                            SharedPreferences prefs = PreferenceManager
                                    .getDefaultSharedPreferences(MainActivity.this);
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

}
