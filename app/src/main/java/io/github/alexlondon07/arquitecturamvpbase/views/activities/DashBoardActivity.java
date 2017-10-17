package io.github.alexlondon07.arquitecturamvpbase.views.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import io.github.alexlondon07.arquitecturamvpbase.R;
import io.github.alexlondon07.arquitecturamvpbase.views.BaseActivity;
import io.github.alexlondon07.arquitecturamvpbase.views.adapter.DashBoardAdapter;

public class DashBoardActivity extends BaseActivity {

    private TabLayout dasBoard_tabLayout;
    private ViewPager dasBoard_viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        loadView();
        loadTabsAdapter();
    }

    private void loadTabsAdapter() {
        DashBoardAdapter dashBoardAdapter =  new DashBoardAdapter(getSupportFragmentManager());
        dasBoard_viewPager.setAdapter(dashBoardAdapter);
        dasBoard_tabLayout.setupWithViewPager(dasBoard_viewPager);
        dasBoard_tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorWhite));
        dasBoard_tabLayout.setTabTextColors(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorWhite)));
    }

    private void loadView() {
        dasBoard_tabLayout = (TabLayout) findViewById(R.id.dashBoard_tabLayout);
        dasBoard_viewPager = (ViewPager) findViewById(R.id.dashBoard_viewPager);
    }
}
