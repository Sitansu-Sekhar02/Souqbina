package com.sa.souqbina.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.sa.souqbina.Fragments.FragmentAllProcess;
import com.sa.souqbina.Fragments.ProfileFragment;
import com.sa.souqbina.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static int backPressed = 0;
    public static ImageView iv_menu,iv_share,iv_home;
    boolean doubleBackToExitPressedOnce = false;

    DrawerLayout drawer;
    public static NavigationView navigationView;
    public static TextView tvHeaderText;
    public static TextView tvCount;
    LinearLayout nav_header;
    public static TextView iv_Notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initialize();

        drawer.closeDrawer(GravityCompat.START);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
           /* if (preferences.get("login").equalsIgnoreCase("yes")) {

                menu.findItem(R.id.logout).setTitle("Login");
            } else {
                menu.findItem(R.id.logout).setTitle("Logout");
            }*/
            navigationView.setNavigationItemSelectedListener(this);
        }


        replaceFragmentWithAnimation(new FragmentAllProcess());


    }

    private void initialize() {

        iv_menu = findViewById(R.id.iv_menu);
        iv_share=findViewById(R.id.iv_share);
        tvCount=findViewById(R.id.tvCount);
        iv_home=findViewById(R.id.ivHome);
        iv_Notification=findViewById(R.id.ivNotification);
        tvHeaderText = findViewById(R.id.tvHeaderText);

        iv_menu.setOnClickListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //navigationview
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        nav_header=headerView.findViewById(R.id.Profilebutton);
        nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                replaceFragmentWithAnimation(new ProfileFragment());
            }
        });

    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                drawer.openDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        backPressed = backPressed + 1;
        if (backPressed == 1) {
            Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            new CountDownTimer(5000, 1000) { // adjust the milli seconds here
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() { backPressed = 0;
                }
            }.start();
        }
        if (backPressed == 2) {
            backPressed = 0;
            finishAffinity();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}