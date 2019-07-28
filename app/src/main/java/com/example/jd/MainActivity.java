package com.example.jd;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

public class MainActivity extends AppCompatActivity implements PlansFragment.MyFragmentCallBack{

    private ViewPager mPager;
    private FragmentAdapter mFirstFragmentAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Меняем цвет текста в ActionBar*/
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#736969'>Japanese Diet </font>"));

        /*Убирает теневую линию между ActionBar и TabLayout*/
        getSupportActionBar().setElevation(0);

        mPager = findViewById(R.id.pager);
        mFirstFragmentAdapter  = new FragmentAdapter(getSupportFragmentManager());
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
}
