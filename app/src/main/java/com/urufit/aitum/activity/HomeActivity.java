package com.urufit.aitum.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.google.android.material.navigation.NavigationView;
import com.urufit.aitum.R;
import com.urufit.aitum.fragment.ManagerCalenderFragment;
import com.urufit.aitum.fragment.SettingsActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener  {


    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public AlertDialog.Builder builder;
    CardView card_monitoring,card_activity,card_medical,card_profile;
    private View navHeader;
    TextView txt_name=null,nav_drver_id,txt_scope;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    String Role;
    ArrayList<String> numbersList = new ArrayList<>();
    ArrayList<String> scopeList = new ArrayList<>();
    public  String MY_PREFS_NAME="User_Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BindMethod();
    }

    private void BindMethod()
    {
        toolbar = findViewById(R.id.tool);
        toolbar.setTitle("ATIUM");
        setSupportActionBar(toolbar);
        txt_name=findViewById(R.id.txt_name);
        card_monitoring=findViewById(R.id.card_monitoring);
        card_activity=findViewById(R.id.card_activity);
        card_medical=findViewById(R.id.card_medical);
        card_profile=findViewById(R.id.card_player_prof);
        TextView dayTime=findViewById(R.id.day_time);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            dayTime.setText("Good Morning");
            //Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            dayTime.setText("Good Afternoon");
      //      Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            dayTime.setText("Good Evening");
          //  Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            dayTime.setText("Good Night");
         //   Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
        }
        card_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Monitoring.class);
                startActivity(intent);
            }
        });

        card_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Performance_Assessment.class);
                startActivity(intent);
            }
        });

        card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PlayerHomeActivity.class);
                startActivity(intent);
            }
        });

        card_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Clinical_assement_follow_up.class);
                startActivity(intent);
            }
        });

        builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        drawerLayout = findViewById(R.id.drawer_layout);
      //  navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        hidemenu();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_athlete_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_change_user:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.alert_dialog, null);
               ListView listView = dialogLayout.findViewById(R.id.list_View);
               if(numbersList!=null) {
                   numbersList = (ArrayList<String>) getIntent().getSerializableExtra("arraylist");
                   MyListAdapter adapter = new MyListAdapter(this, numbersList);
                   listView.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
                   builder.setView(dialogLayout);
                   builder.show();
                   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           String RoleName = String.valueOf(listView.getItemAtPosition(position));
                           if ("athlete".equalsIgnoreCase(RoleName)) {
                               Intent intent = new Intent(getApplicationContext(), Athlete_Home_Activity.class);
                               intent.putExtra("arraylist", numbersList);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(intent);
                               finish();
                           } else {
                               Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                               intent.putExtra("arraylist", numbersList);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(intent);
                               finish();
                           }
                       }
                   });
               }
                break;
            case R.id.menu_scope_user:
                AlertDialog.Builder builderScope = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflaterScope = getLayoutInflater();
                View view = inflaterScope.inflate(R.layout.alert_dialog_scope, null);
                ListView listViewScope = view.findViewById(R.id.list_View);
                scopeList = (ArrayList<String>) getIntent().getSerializableExtra("scopelist");
                MyListScopeAdapter adapterScope=new MyListScopeAdapter(this, scopeList);
                listViewScope.setAdapter(adapterScope);
                adapterScope.notifyDataSetChanged();
                builderScope.setView(view);
                listViewScope.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String ScopeName= String.valueOf(listViewScope.getItemAtPosition(position));
                       txt_scope.setText(ScopeName);
                    }
                });
                builderScope.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                builderScope.setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                builderScope.show();

                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }
    public static class MyListScopeAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final ArrayList<String> maintitle;

        public MyListScopeAdapter(Activity context, ArrayList<String> maintitle) {
            super(context, R.layout.item_view_user_list, maintitle);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.maintitle=maintitle;

        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.item_view_user_list, null,true);
            TextView titleText = (TextView) rowView.findViewById(R.id.title);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            String value=maintitle.get(position);
            titleText.setText(value);

            return rowView;

        };
    }

    public class MyListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final ArrayList<String> maintitle;

        public MyListAdapter(Activity context, ArrayList<String> maintitle) {
            super(context, R.layout.item_view_user_list, maintitle);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.maintitle=maintitle;

        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.item_view_user_list, null,true);
            TextView titleText = (TextView) rowView.findViewById(R.id.title);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            String value=maintitle.get(position);
            titleText.setText(value);

            return rowView;

        };
    }

    private void hidemenu() {
        navigationView = findViewById(R.id.navigationView);
        navHeader = navigationView.getHeaderView(0);
        nav_drver_id = (TextView) navHeader.findViewById(R.id.nau_username_txt);
        txt_scope = (TextView) navHeader.findViewById(R.id.txt_scope);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String LocalName=pref.getString("Name",null);
        String Scope=pref.getString("Scope",null);
        nav_drver_id.setText(LocalName);
        txt_name.setText(LocalName);
        txt_scope.setText(Scope);

        Menu nav_Menu = navigationView.getMenu();
        if("user".equalsIgnoreCase(Role)){
            nav_Menu.findItem(R.id.nav_home).setVisible(true);
            nav_Menu.findItem(R.id.nav_event_calendar).setVisible(true);
            nav_Menu.findItem(R.id.nav_daily_avtivity).setVisible(true);
            nav_Menu.findItem(R.id.nav_medical).setVisible(true);
            nav_Menu.findItem(R.id.nav_survey).setVisible(true);
            nav_Menu.findItem(R.id.nav_setting).setVisible(true);
            nav_Menu.findItem(R.id.nav_reports).setVisible(false);
            nav_Menu.findItem(R.id.nav_service).setVisible(false);
            nav_Menu.findItem(R.id.nav_reports).setVisible(false);
        }if("athlete".equalsIgnoreCase(Role)){
            nav_Menu.findItem(R.id.nav_home).setVisible(true);
            nav_Menu.findItem(R.id.nav_event_calendar).setVisible(true);
            nav_Menu.findItem(R.id.nav_daily_avtivity).setVisible(true);
            nav_Menu.findItem(R.id.nav_medical).setVisible(true);
            nav_Menu.findItem(R.id.nav_survey).setVisible(true);
            nav_Menu.findItem(R.id.nav_setting).setVisible(true);
            nav_Menu.findItem(R.id.nav_reports).setVisible(false);
            nav_Menu.findItem(R.id.nav_service).setVisible(false);
            nav_Menu.findItem(R.id.nav_reports).setVisible(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }


    boolean doubleBackToExitPressedOnce = false;

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_monitoring) {
            Intent homepage = new Intent(HomeActivity.this, Monitoring.class);
            startActivity(homepage);
        }else if (id == R.id.nav_daily_avtivity) {
            Intent homepage = new Intent(HomeActivity.this, Performance_Assessment.class);
            startActivity(homepage);
        } else if (id == R.id.nav_event_calendar) {
            Intent homepage = new Intent(HomeActivity.this, ManagerCalenderFragment.class);
            startActivity(homepage);
        }
        else if (id == R.id.nav_medical) {
            Intent homepage = new Intent(HomeActivity.this, Clinical_assement_follow_up.class);
            startActivity(homepage);
        }else if (id == R.id.nav_service) {
            Intent homepage = new Intent(HomeActivity.this, ServiceActivity.class);
            startActivity(homepage);
        }
        else if (id == R.id.nav_reports) {
            Intent homepage = new Intent(HomeActivity.this, ReportsActivity.class);
            startActivity(homepage);
        }
        else if (id == R.id.nav_survey) {
            Intent homepage = new Intent(HomeActivity.this, sur_test.class);
            startActivity(homepage);
        }
        else if (id == R.id.nav_setting){
            Intent homepage = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(homepage);
        }else if (id == R.id.nav_logout) {
            builder.setIcon(R.drawable.ic_launcher_background);
            builder.setTitle("Exit");
            builder.setMessage("Are you sure?.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences myPrefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = myPrefs.edit();
                            editor.clear();
                            editor.apply();
                            dialog.cancel();
                           logout();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            alert.show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
}