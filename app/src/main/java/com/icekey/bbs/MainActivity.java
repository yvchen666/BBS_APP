package com.icekey.bbs;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.icekey.bbs.fragment.FrameHome;
import com.icekey.bbs.fragment.FrameMine;
import com.icekey.bbs.fragment.FrameTopic;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class MainActivity extends AppCompatActivity {
    private FrameHome frameHome;
    private FrameTopic frameTopic;
    private FrameMine frameMine;
    private Fragment[] fragments;
    private int lastfragment = 0;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private View fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideTitle();
        initView();

    }

    private void hideTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void initView() {
        setHalfTransparent();
        frameHome = new FrameHome();
        frameTopic = new FrameTopic();
        frameMine = new FrameMine();
        fragments = new Fragment[]{frameHome, frameTopic, frameMine};
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, frameHome).show(frameHome).commit();
        bottomNavigationViewEx = findViewById(R.id.bnve);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        fragment = findViewById(R.id.main_fragment);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.i_home:
                    Log.d("item.getItemId()", " " + lastfragment);
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.i_topic:
                    Log.d("item.getItemId()", " " + lastfragment);
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.i_mine:
                    Log.d("item.getItemId()", " " + lastfragment);
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

        private void switchFragment(int lastfragment, int index) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //隐藏上个Fragment
            transaction.hide(fragments[lastfragment]);
            if (!fragments[index].isAdded()) {
                transaction.add(R.id.main_fragment, fragments[index]);
            }
            transaction.show(fragments[index]).commitAllowingStateLoss();
        }

        protected void setHalfTransparent() {
            if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }