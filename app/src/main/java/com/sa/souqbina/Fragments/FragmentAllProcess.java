package com.sa.souqbina.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sa.souqbina.Activity.MainActivity;
import com.sa.souqbina.Adapter.AllProcessAdapter;
import com.sa.souqbina.R;

public class FragmentAllProcess extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    AllProcessAdapter adapter;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_process_tablayout, container, false);
        tabLayout =view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.upcoming)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.ongoing)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.completed)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
         adapter = new AllProcessAdapter(getActivity(),getChildFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }


}
