package com.example.myapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;

import com.example.myapp.Common.Utility;
import com.example.myapp.R;
import com.example.myapp.databinding.ActivityMainBinding;
import com.example.myapp.fragmants.SignatureFragment;
import com.example.myapp.fragmants.StartSurveyFragment;

public class SignatureActivity extends AppCompatActivity {

    private  String unique_id;
    private  ActivityMainBinding activityMainBinding;



    public SignatureActivity()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

//        final Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//
//            }
//        }, 1000);
//
////        addFragment(SignatureFragment());

        unique_id=getIntent().getExtras().getString("unique_id");

        addFragment(new SignatureFragment(unique_id), getSupportFragmentManager(), R.id.layout_fragment);

    }



    public static void addFragment(Fragment fragment, FragmentManager fragmentManager, int resId) {
        try {
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(resId, fragment).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        if (key_code== KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            return false;
        }
        return true;
    }
}