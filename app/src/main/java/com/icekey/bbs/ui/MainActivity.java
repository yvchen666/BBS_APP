package com.icekey.bbs.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.icekey.bbs.R;
import com.icekey.bbs.ui.fragment.FrameHome;
import com.icekey.bbs.ui.fragment.FrameMine;
import com.icekey.bbs.ui.fragment.FrameTopic;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity {
    long first_time = 0;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
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
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.tool_bar_2);
        toolbar.setNavigationIcon(R.drawable.ic_book_list);
        // ??????navigation button ????????????
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // ?????? toolbar ?????????
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        // ?????? Title
        toolbar.setTitle("??????");
        //  ??????Toolbar title????????????
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //?????? Toolbar menu
        toolbar.inflateMenu(R.menu.setting_menu);
        // ???????????????????????????
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
        // ??????menu item ????????????
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_setting:
                        //????????????
                        break;
                }
                return false;
            }
        });
        setHalfTransparent();
        frameHome = new FrameHome();
        frameTopic = new FrameTopic();
        frameMine = new FrameMine();
        fragments = new Fragment[]{frameHome, frameTopic, frameMine};
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, frameHome).show(frameHome).commit();
        bottomNavigationViewEx = findViewById(R.id.bnve);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        fragment = findViewById(R.id.main_fragment);


//        setSupportActionBar(toolbar);//???toolbar???ActionBar??????
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(toggle);//???????????????
        toggle.syncState();


        /*---------------------------???????????????????????????-----------------------------*/
        //??????xml?????????view
        View headerView = navigationView.getHeaderView(0);
        //????????????????????????????????????
        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);

        //???????????????????????????
        ImageView imageView = headerView.findViewById(R.id.iv_head);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.cardview_dark_background);
        //??????item???????????????
        navigationView.setItemTextColor(csl);
        //????????????????????????????????????  ?????????null???????????????????????????
        navigationView.setItemIconTintList(csl);

        //????????????????????????
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //??????
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_LONG).show();
                //???????????????????????????
//                menuItem.setChecked(true);
                //???????????????
//                drawer.closeDrawers();
                return false;
            }
        });

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
                        toolbar.setTitle("??????");
                        lastfragment = 0;
                    }
                    return true;
//                case R.id.i_topic:
//                    Log.d("item.getItemId()", " " + lastfragment);
//                    if (lastfragment != 1) {
//                        switchFragment(lastfragment, 1);
//                        toolbar.setTitle("??????");
//                        lastfragment = 1;
//                    }
//                    return true;
                case R.id.i_mine:
                    Log.d("item.getItemId()", " " + lastfragment);
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        toolbar.setTitle("??????");
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
        //????????????Fragment
        transaction.hide(fragments[lastfragment]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.main_fragment, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    protected void setHalfTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21??????5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= 19) {//19??????4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public boolean onKeyUp(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            long second_time = System.currentTimeMillis();

            if (second_time - first_time > 2000) {
                Toasty.warning(MainActivity.this, "????????????????????????").show();
                first_time = second_time;
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyUp(keycode, event);
    }
}