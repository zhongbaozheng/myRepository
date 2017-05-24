package cn.meiqu.lainmonitor;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import cn.meiqu.baseproject.API;
import cn.meiqu.baseproject.baseUi.BaseActivity;
import cn.meiqu.baseproject.httpGet.HttpGetController;
import cn.meiqu.baseproject.util.LogUtil;
import cn.meiqu.lainmonitor.view.SmoothDrawerLayout;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {
    String className = getClass().getName();
    String action_getHomePage = className + API.getHomePage;
    String action_getHomeChildPage = className + API.getHomeChildPage;
    private SmoothDrawerLayout mDrawerLayout;
    private NavigationView mNavView;

    private void assignViews() {
        initTitle("温湿度");
        mDrawerLayout = (SmoothDrawerLayout) findViewById(R.id.drawer_layout);
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
        mDrawerLayout.addDrawerListener(this);
//        mNavView.getMenu().getItem(1).getSubMenu().getItem(0).set
//        mNavView.getMenu().add(R.id.menu1, 1, 1, "test");
//        mNavView.getMenu().getItem(1).getSubMenu().add("dddd");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTransparent();
        assignViews();
        initFragment();
        //
        initReceiver(new String[]{action_getHomePage, action_getHomeChildPage});
        HttpGetController.getInstance().getHomePage(className);
    }

    @Override
    public void initFragment() {
//        findViewById(R.id.frame_fragment).setAlpha(0.1f);
//        findViewById(R.id.frame_fragment).animate().alpha(1.0f).setDuration(500).start();
//        showFirst(new FragmentTemp());
//        showFirst(new FragmentServer());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        for (int i = 0; i < mNavView.getMenu().size(); i++) {
            for (int j = 0; j < mNavView.getMenu().getItem(i).getSubMenu().size(); j++) {
                if (mNavView.getMenu().getItem(i).getSubMenu().getItem(j).isChecked()) {
                    mNavView.getMenu().getItem(i).getSubMenu().getItem(j).setCheckable(false);
                    break;
                }
            }
        }
        item.setCheckable(true);
        getSupportActionBar().setTitle(item.getTitle().toString().trim());
        int id = item.getItemId();
        LogUtil.log("item.getItemId=" + id);
        initFragment();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        mDrawerLayout.getChildAt(0).setTranslationX(slideOffset * drawerView.getWidth() / 2);
    }

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onHttpHandle(String action, String data) {

    }

}
