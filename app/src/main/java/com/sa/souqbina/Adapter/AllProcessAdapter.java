package com.sa.souqbina.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sa.souqbina.Fragments.CompletedOrderFragment;
import com.sa.souqbina.Fragments.OngoingOrderFragment;
import com.sa.souqbina.Fragments.UpcomingOrderFragment;

public class AllProcessAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public AllProcessAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                UpcomingOrderFragment upcomingFragment = new UpcomingOrderFragment();
                return upcomingFragment;
            case 1:
                OngoingOrderFragment ongoingProcessFragment = new OngoingOrderFragment();
                return ongoingProcessFragment;
            case 2:
                CompletedOrderFragment completedprocesFragment = new CompletedOrderFragment();
                return completedprocesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
