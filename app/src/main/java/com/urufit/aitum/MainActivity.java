package com.urufit.aitum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.google.android.material.navigation.NavigationView;
import com.urufit.aitum.activity.LoginActivity;
import com.urufit.aitum.ui.Toolbar_customs;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends Toolbar_customs implements NavigationView.OnNavigationItemSelectedListener {

    public void bottomView() {
        //

       /* bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(ManagerDashboardFragment.newInstance());*/
   /* public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_manager_calender:
                            openFragment(ManagerCalenderFragment.newInstance());
                            return true;
                        case R.id.navigation_manager_dashboard:
                            openFragment(ManagerDashboardFragment.newInstance());
                            return true;
                        case R.id.navigation_chat:
                            openFragment(ChatFragment.newInstance());
                            return true;

                        case R.id.navigation_settings:
                            openFragment(ManagerSettingsFragment.newInstance());
                            return true;
                    }
                    return false;
                }
            };*/
    }

    TextView tvHeaderName, tvHeaderEmail;
    String mRole;
    NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Lato-Regular.ttf", true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

      //  String name=getIntent().getStringExtra("Name");
    //    String Role=getIntent().getStringExtra("Role");

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
    //    tvHeaderName = (TextView) headerView.findViewById(R.id.nav_name);
      //  tvHeaderEmail = (TextView) headerView.findViewById(R.id.nav_email);
        tvHeaderName.setText(getIntent().getStringExtra("Name"));
        tvHeaderEmail.setText(getIntent().getStringExtra("Email"));


        //hidemenu();
   /*     AWSMobileClient.getInstance().getUserAttributes(new Callback<Map<String, String>>() {
            @Override
            public void onResult(Map<String, String> result) {

                String name = result.get("given_name");
                Log.d("NAME=", name);
                tvHeaderName.setText(name);
                String email = result.get("email");
                tvHeaderEmail.setText(email);
                Log.d("EMAIL=", email);
                mRole = result.get("custom:Role");
                Log.d("Role=", mRole);

                //    hidemenu();
            }

            @Override
            public void onError(Exception e) {
            }
        });*/
        //add this line to display menu1 when the activity is loaded
        //displaySelectedScreen(R.id.nav_dashboard);
    }

   /* private void hidemenu() {
        mRole=getIntent().getStringExtra("Role");
   *//*     if (mRole.equals("user")) {

            //  navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);

        }
        if (mRole.equals("manager")) {
            //  navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.bottom_navigation_menu);
        }
        if (mRole.equals("admin")) {
            //  navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_menu_admin);
        }*//*
         if (mRole.equalsIgnoreCase("user")) {
            NavigationView navigationView = findViewById(R.id.navigation);
            Menu menuNav = navigationView.getMenu();
             menuNav.findItem(R.id.navigation_chat).setVisible(false);
             menuNav.findItem(R.id.nav_service).setVisible(false);
        } else if (mRole.equalsIgnoreCase("manager")) {
            NavigationView navigationView = findViewById(R.id.navigation);
            Menu menuNav = navigationView.getMenu();
            MenuItem nav_metric = menuNav.findItem(R.id.nav_activity);
            MenuItem nav_dev = menuNav.findItem(R.id.nav_medical);
            MenuItem nav_survey = menuNav.findItem(R.id.nav_survey);
            MenuItem nav_service = menuNav.findItem(R.id.nav_service);
            nav_service.setVisible(true);
            nav_metric.setVisible(false);
            nav_dev.setVisible(false);
            nav_survey.setVisible(false);
        }else if (mRole.equalsIgnoreCase("admin")) {
            NavigationView navigationView = findViewById(R.id.navigation);
            Menu menuNav = navigationView.getMenu();
            MenuItem nav_metric = menuNav.findItem(R.id.nav_activity);
            MenuItem nav_dev = menuNav.findItem(R.id.nav_medical);
            MenuItem nav_survey = menuNav.findItem(R.id.nav_survey);
            MenuItem nav_chat = menuNav.findItem(R.id.navigation_chat);
            MenuItem nav_service = menuNav.findItem(R.id.nav_service);
            nav_metric.setVisible(false);
            nav_dev.setVisible(false);
            nav_survey.setVisible(false);
            nav_chat.setVisible(false);
            nav_service.setVisible(false);
        }
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int fragments = getFragmentManager().getBackStackEntryCount();
            if (fragments > 0) {
                super.onBackPressed();
            } else {
                if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                    super.onBackPressed();
                } else {
                    Toast.makeText(getBaseContext(), "Press once again to exit!",
                            Toast.LENGTH_SHORT).show();
                }
                back_pressed = System.currentTimeMillis();
            }
        }
    }

    private void logout() {
        AWSMobileClient.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        SharedPreferences preferences = getSharedPreferences("LOGIN_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        finish();
    }

   /* private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_dashboard:
                fragment = new ManagerDashboardFragment();
                break;
            case R.id.nav_calender:
                fragment = new ManagerCalenderFragment();
                break;
  *//*          case R.id.nav_activity:
                fragment = new ActivityFragment();
                break;*//*
            case R.id.nav_medical:
                fragment = new MediacalFragment();
                break;
            case R.id.nav_survey:
                fragment = new SurveyFragment();
                break;

                case R.id.nav_service:
                fragment = new ServiceFragment();
                break;
            case R.id.nav_settings:
                fragment = new ManagerSettingsFragment();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
     //   displaySelectedScreen(item.getItemId());
        return true;
    }
}

 /*   @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
}*/
