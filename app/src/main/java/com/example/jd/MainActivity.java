package com.example.jd;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

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
    }

    @Override
    public void onBtnClick(int i) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FragmentRecyclerView.newInstance(i))
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
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                for (int i = 0; i < 14; i++) {
                    if (prefs.contains("key" + i)){
                        editor.remove("key" + i);
                        editor.commit();
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
