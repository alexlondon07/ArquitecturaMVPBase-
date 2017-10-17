package io.github.alexlondon07.arquitecturamvpbase.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import io.github.alexlondon07.arquitecturamvpbase.helper.Constants;
import io.github.alexlondon07.arquitecturamvpbase.views.fragments.CustomerFragment;
import io.github.alexlondon07.arquitecturamvpbase.views.fragments.ProductFragment;

/**
 * Created by alexlondon07 on 10/14/17.
 */

public class DashBoardAdapter extends FragmentStatePagerAdapter {

    public DashBoardAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new ProductFragment();
            case 1:
                return new CustomerFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return Constants.TITTLE_PRODUCT;
            case 1:
                return Constants.TITTLE_CUSTOMER;
            default:
                return Constants.EMPTY;
        }
    }
}
