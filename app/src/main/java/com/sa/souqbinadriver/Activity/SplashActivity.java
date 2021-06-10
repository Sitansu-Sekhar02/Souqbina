package com.sa.souqbinadriver.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.sa.souqbinadriver.Login.LoginActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.model.CreatePlanModel;


public class SplashActivity extends AppCompatActivity {

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.ColorStatusBar));
        }
*/
        GlobalVariables.LANGUAGE mCurrentLocale = GlobalFunctions.getLanguage(this);

        Log.d("Splash Screen", "Language Selected (SplashScreen) : "+mCurrentLocale);

       /* Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 6000);*/
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */


            @Override
            public void run() {
                Intent i = null;
                i = new Intent(SplashActivity.this, LoginActivity.class);
               // i = new Intent(SplashActivity.this, MainActivity.class);

                if(GlobalFunctions.isLoggedIn(SplashActivity.this))
                {
                    i = new Intent(SplashActivity.this, MainActivity.class);
                }
//                else {i = new Intent(SplashScreen.this, LoginActivity.class);}
               /* else if(globalFunctions.getSharedPreferenceString(globalVariables.SHARED_PREFERENCE_TERMS_AND_CONDITIONS_SELECTED) != null){

                    if(globalFunctions.getSharedPreferenceString(globalVariables.SHARED_PREFERENCE_TERMS_AND_CONDITIONS_SELECTED).equalsIgnoreCase("checked") ){
                        i = new Intent(SplashActivity.this, TermsAndConditionActivity.class);
                    }else{
                        i = new Intent(SplashActivity.this, TermsAndConditionActivity.class);
                    }
                }else{
                    i = new Intent(SplashActivity.this, TermsAndConditionActivity.class);
                }*/

                startActivity(i);

                finish();
            }
        }, 5000);
    }
}
