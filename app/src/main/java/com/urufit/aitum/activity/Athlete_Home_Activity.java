package com.urufit.aitum.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.android.material.navigation.NavigationView;
import com.urufit.aitum.R;
import com.urufit.aitum.adapter.ScheduleAdapter;
import com.urufit.aitum.adapter.TodoAdapter;
import com.urufit.aitum.fragment.ManagerCalenderFragment;
import com.urufit.aitum.fragment.SettingsActivity;
import com.urufit.aitum.model.ScheduleModel;
import com.urufit.aitum.model.TodoModel;
import com.urufit.aitum.ui.RadarMarkerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Athlete_Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public AlertDialog.Builder builder;
    CardView card_monitoring, card_activity, card_medical;
    private View navHeader;
    TextView txt_name = null, nav_drver_id,txt_scope;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    String Token,Role;
    private static final int SURVEY_REQUEST = 1337;
    ArrayList<String> numbersList = new ArrayList<>();
    ArrayList<String> scopeList = new ArrayList<>();
    private RadarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_home);
        BindMethod();
    }

    private void BindMethod() {
        toolbar = findViewById(R.id.tool);
        toolbar.setTitle("ATIUM");
        setSupportActionBar(toolbar);
        chart = findViewById(R.id.radarchart);
     //   CardView cardtodo=(CardView)findViewById(R.i/d.card_todo) ;
 //       CardView card_survey=(CardView)findViewById(R.id.card_survey) ;
        builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        drawerLayout = findViewById(R.id.drawer_layout);
        //  navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Athlete_Home_Activity.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

     /*   cardtodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TodoActivity.class);
                startActivity(intent);
            }
        });
        card_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_survey = new Intent(Athlete_Home_Activity.this, Welness_test.class);
                String Email = getIntent().getStringExtra("Email");
                i_survey.putExtra("Email",Email);
                startActivity(i_survey);
            }
        });*/

        /*card_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_survey = new Intent(Athlete_Home_Activity.this, SurveyActivity.class);
                i_survey.putExtra("json_survey", loadSurveyJson("example_survey_4.json"));
                startActivityForResult(i_survey, SURVEY_REQUEST);
            }
        });*/

        TodoModel[] myListData = new TodoModel[] {
                new TodoModel("Daily Update Questinnaire"),
                new TodoModel("Take Test"),
                new TodoModel("Medical Checkup")
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        TodoAdapter adapter = new TodoAdapter(Arrays.asList(myListData),getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        /*ScheduleModel[] myListData = new ScheduleModel[] {
                new ScheduleModel("WellBeing","1"),
                new ScheduleModel("Load Quantification","2"),
                new ScheduleModel("Kinanthropometry","3"),
                new ScheduleModel("Technical Assessment","4"),
                new ScheduleModel("Rehab Knee","5"),
                new ScheduleModel("Hamstring","6"),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ScheduleAdapter adapter = new ScheduleAdapter(Arrays.asList(myListData));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/

        hidemenu();

        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
            }
            @Override
            public void onError(Exception e) {

            }
        });


        // Radar chart

        chart.setBackgroundColor(Color.rgb(60, 65, 82));

        chart.getDescription().setEnabled(false);

        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        setData();

        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
     //   xAxis.setTypeface(tfLight);
        xAxis.setTextSize(10f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new ValueFormatter() {

        //    private final String[] mActivities = new String[]{"Burger", "Steak", "Salad", "Pasta", "Pizza"};
        private final String[] mActivities = new String[]{"Power", "Sprint", "COD", "Strength"};

            @Override
            public String getFormattedValue(float value) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = chart.getYAxis();
      //  yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(10f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
   //     l.setTypeface(tfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);

    }
    private void setData() {

        float mul = 80;
        float min = 20;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
       // data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_athlete_menus, menu);
        MenuItem item = menu.findItem(R.id.menu_change_user); Intent intent = getIntent();
        String result = intent.getStringExtra("Role");
        if("athlete".equalsIgnoreCase(result)){
            item.setVisible(false);
        }else {
            item.setVisible(true);
        }
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
                numbersList = (ArrayList<String>) getIntent().getSerializableExtra("arraylist");
                MyListAdapter adapter=new MyListAdapter(this, numbersList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                builder.setView(dialogLayout);
                builder.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String RoleName= String.valueOf(listView.getItemAtPosition(position));
                        if("athlete".equalsIgnoreCase(RoleName)){
                            Intent intent=new Intent(getApplicationContext(), Athlete_Home_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("arraylist", numbersList);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                break;

            case R.id.menu_scope_user:
                AlertDialog.Builder builderScope = new AlertDialog.Builder(this);
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.d("****", "****************** WE HAVE ANSWERS ******************");
                Log.v("ANSWERS JSON", answers_json);
                Log.d("****", "*****************************************************");

                //do whatever you want with them...
            }
        }
    }


    //json stored in the assets folder. but you can get it from wherever you like.
    private String loadSurveyJson(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void hidemenu() {
        navigationView = findViewById(R.id.navigationView);
        navHeader = navigationView.getHeaderView(0);
        nav_drver_id = (TextView) navHeader.findViewById(R.id.nau_username_txt);
        txt_scope = (TextView) navHeader.findViewById(R.id.txt_scope);
        navigationView.setNavigationItemSelectedListener(this);

        String name =getIntent().getStringExtra("Name");
        Role =getIntent().getStringExtra("Role");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NavItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_name", name);
        editor.apply();
        SharedPreferences preferences = getSharedPreferences("NavItems", MODE_PRIVATE);
        String LocalName=preferences.getString("user_name",null);
        nav_drver_id.setText(LocalName);
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
        if (id == R.id.navigation_home_athlete) {
            Intent intent = new Intent(Athlete_Home_Activity.this, Athlete_Home_Activity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_calender) {
            Intent homepage = new Intent(Athlete_Home_Activity.this, ManagerCalenderFragment.class);
            startActivity(homepage);
        } else if (id == R.id.nav_athlete_setting) {
            Intent homepage = new Intent(Athlete_Home_Activity.this, SettingsActivity.class);
            startActivity(homepage);
        } else if (id == R.id.nav_logout) {
            builder.setIcon(R.drawable.ic_launcher_background);
            builder.setTitle("ATIUM");
            builder.setMessage("Are you sure to log out?.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
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
