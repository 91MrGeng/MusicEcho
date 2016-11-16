package com.wm.remusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.wm.remusic.R;
import com.wm.remusic.adapter.MenuItemAdapter;
import com.wm.remusic.fragment.BitSetFragment;
import com.wm.remusic.fragment.MainFragment;
import com.wm.remusic.fragment.TimingFragment;
import com.wm.remusic.fragmentnet.TabNetPagerFragment;
import com.wm.remusic.handler.HandlerUtil;
import com.wm.remusic.service.MusicPlayer;
import com.wm.remusic.widget.CustomViewPager;
import com.wm.remusic.widget.SplashScreen;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ActionBar ab;
    private ImageView barnet, barmusic, search;
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ListView mLvLeftMenu;

    public void onCreate(Bundle savedInstanceState) {
        final SplashScreen splashScreen = new SplashScreen(this);
        splashScreen.show(R.drawable.art_login_bg,
                SplashScreen.SLIDE_LEFT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandlerUtil.getInstance(this).postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.removeSplashScreen();
            }
        },2000);

        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);

        setToolBar();

        barnet = (ImageView) findViewById(R.id.bar_net);
        barmusic = (ImageView) findViewById(R.id.bar_music);
        search = (ImageView) findViewById(R.id.bar_search);
        barmusic = (ImageView) findViewById(R.id.bar_music);

        drawerLayout = (DrawerLayout) findViewById(R.id.fd);
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);

        setViewPager();
        setUpDrawer();


//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.app_name, R.string.search);
//        drawerLayout.setDrawerListener(toggle);
//        toggle.syncState();
//
//        final NavigationView navigationview = (NavigationView) findViewById(R.id.nav);
//        navigationview.setClickable(true);
//      //  navigationview.setNavigationItemSelectedListener(this);
//        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
//        View headerView = navigationview.getHeaderView(2);
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.navigation_view);
//        relativeLayout.setSelected(true);
//        LinearLayout timePlay = (LinearLayout) findViewById(R.id.timing_play);
//        LinearLayout downBit = (LinearLayout) findViewById(R.id.down_bit);
//        TextView exit = (TextView) findViewById(R.id.action_exit);
//
//        timePlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimingFragment fragment = new TimingFragment();
//                fragment.show(getSupportFragmentManager(), "timing");
//                drawerLayout.closeDrawers();
//            }
//        });
//
//        downBit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }



    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle("");
    }

    private void setViewPager() {
        tabs.add(barnet);
        tabs.add(barmusic);
        final CustomViewPager customViewPager = (CustomViewPager) findViewById(R.id.main_viewpager);
        final MainFragment mainFragment = new MainFragment();
        final TabNetPagerFragment tabNetPagerFragment = new TabNetPagerFragment();
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        customViewPagerAdapter.addFragment(tabNetPagerFragment);
        customViewPagerAdapter.addFragment(mainFragment);
        customViewPager.setAdapter(customViewPagerAdapter);
        customViewPager.setCurrentItem(1);
        barmusic.setSelected(true);
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        barnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customViewPager != null) {
                    customViewPager.setCurrentItem(0);
                }
            }
        });
        barmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(1);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, NetSearchWordsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                MainActivity.this.startActivity(intent);
            }
        });
    }


    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        drawerLayout.closeDrawers();
                        break;
                    case 2:
                        TimingFragment fragment3 = new TimingFragment();
                        fragment3.show(getSupportFragmentManager(), "timing");
                        drawerLayout.closeDrawers();
                        break;
                    case 3:
                        BitSetFragment bfragment = new BitSetFragment();
                        bfragment.show(getSupportFragmentManager(), "bitset");
                        drawerLayout.closeDrawers();
                        break;
                    case 4:
                        if (MusicPlayer.isPlaying()) {
                            MusicPlayer.playOrPause();
                        }
                        unbindService();
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                }
            }
        });
    }


    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    static class CustomViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }


//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        switch (item.getItemId()) {
//
//            case R.id.timing_play:
//                TimingFragment fragment = new TimingFragment();
//                fragment.show(getSupportFragmentManager(), "timing");
//                break;
//            case R.id.action_exit:// 退出
//
//                if (MusicPlayer.isPlaying()) {
//                    MusicPlayer.playOrPause();
//                }
//                unbindService();
//                finish();
//
//            case R.id.down_bit:
//                BitSetFragment bfragment = new BitSetFragment();
//                bfragment.show(getSupportFragmentManager(), "bitset");
//
//                break;
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.fd);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home: //Menu icon
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    @Override
//    public void down(String key) {
//        // 字母索引被按下时回调
//        if (map.get(key) != null) {
//            lv.setSelectionFromTop(map.get(key), 0);
//
//            text_select.setText(key);
//        }
//        Iv_select_bg.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void up() {
//        // 字母索引被松开时回调
//        Iv_select_bg.setVisibility(View.GONE);
//        text_select.setText(null);
//    }
//
//    @Override
//    public void move(String key) {
//        // 字母索引被按下并移动时回调
//        down(key);
//    }


    long time = 0;

    /**
     * 双击返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);
//        moveTaskToBack(true);
        // System.exit(0);
        // finish();
    }
}
