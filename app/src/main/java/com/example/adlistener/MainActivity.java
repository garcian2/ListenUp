package com.example.adlistener;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.facebook.ads.*;

import com.example.adlistener.fragments.advertiserPage;
import com.example.adlistener.fragments.listenerPage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AudioManager amanager;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Network Ads on App Start
        AudienceNetworkAds.initialize(this);

        //Initialize audio listener
        amanager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        checkPermissions();

        List<Fragment> list = new ArrayList<>();
        list.add(new listenerPage());
        list.add(new advertiserPage());

        pager = findViewById(R.id.viewPager);
        pagerAdapter = new SlidepagerAdapter(getSupportFragmentManager(), list);

        pager.setAdapter(pagerAdapter);
    }

    private void checkPermissions(){
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED)){
            Intent intent= new Intent (Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            finish();
        }
    }
}