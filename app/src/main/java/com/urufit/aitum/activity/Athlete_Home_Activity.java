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
import com.desai.vatsal.mydynamiccalendar.EventModel;
import com.desai.vatsal.mydynamiccalendar.GetEventListListener;
import com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar;
import com.desai.vatsal.mydynamiccalendar.OnDateClickListener;
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
import com.urufit.aitum.model.TodoModel;
import com.urufit.aitum.ui.RadarMarkerView;
import com.urufit.aitum.ui.SingletonSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Athlete_Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public AlertDialog.Builder builder;
    CardView card_monitoring, card_activity, card_medical, card_profile;
    private View navHeader;
    TextView txt_name , txt_user_name, txt_scope,txt_username,txt_email;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    String Token, Role;
    private static final int SURVEY_REQUEST = 1337;
    ArrayList<String> roleList = new ArrayList<>();
    ArrayList<String> scopeList = new ArrayList<>();
    private RadarChart chart;
    ScheduleAdapter adapter;
    RecyclerView recyclerView;
    List<TodoModel> myListData;
    private MyDynamicCalendar myCalendar;
    ArrayList<String>teamNameList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_home);
        BindMethod();
    }

    private void BindMethod() {
        toolbar = findViewById(R.id.tool);
        toolbar.setTitle("ATIUM");
        toolbar.setTitleTextAppearance(getApplicationContext(),R.style.CustomFontStyle);
        setSupportActionBar(toolbar);
        chart = findViewById(R.id.radarchart);
        card_monitoring = findViewById(R.id.card_monitoring);
        card_activity = findViewById(R.id.card_activity);
        card_medical = findViewById(R.id.card_medical);
        card_profile = findViewById(R.id.card_player_prof);
        txt_username = findViewById(R.id.txt_username);
        txt_email = findViewById(R.id.txt_email);
        txt_username.setText(SingletonSession.Instance().getUsername());
        txt_email.setText(SingletonSession.Instance().getEmail());
        TextView dayTime = findViewById(R.id.day_time);
        builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Athlete_Home_Activity.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            dayTime.setText("Good Morning");
            //Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            dayTime.setText("Good Afternoon");
            //      Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            dayTime.setText("Good Evening");
            //  Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            dayTime.setText("Good Night");
            //   Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
        }

        card_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Monitoring.class);
                startActivity(intent);
            }
        });

        card_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Performance_Assessment.class);
                startActivity(intent);
            }
        });

        card_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Clinical_assement_follow_up.class);
                startActivity(intent);
            }
        });

        hidemenu();

        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
                getIndividualSchedules();
                getTeamName();
            }

            @Override
            public void onError(Exception e) {

            }
        });

        // Radar chart

       chart.setBackgroundColor(Color.rgb(60, 65, 82));
    //   chart.setBackgroundColor(Color.rgb(153, 0, 204));


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

        //My Callender

        myCalendar = (MyDynamicCalendar) findViewById(R.id.myCalendar);

        // myCalendar.showMonthViewWithBelowEvents();
        myCalendar.showAgendaView();

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("date", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });

        myCalendar.setCalendarBackgroundColor("#eeeeee");
//        myCalendar.setCalendarBackgroundColor(R.color.gray);

        myCalendar.setHeaderBackgroundColor("#000066");
//        myCalendar.setHeaderBackgroundColor(R.color.black);

        myCalendar.setHeaderTextColor("#ffffff");
//        myCalendar.setHeaderTextColor(R.color.white);

        myCalendar.setNextPreviousIndicatorColor("#ffffff");
//        myCalendar.setNextPreviousIndicatorColor(R.color.black);

        myCalendar.setWeekDayLayoutBackgroundColor("#ffffff");
//        myCalendar.setWeekDayLayoutBackgroundColor(R.color.black);

        myCalendar.setWeekDayLayoutTextColor("#660000");
//        myCalendar.setWeekDayLayoutTextColor(R.color.black);

//        myCalendar.isSaturdayOff(true, "#ffffff", "#ff0000");
//        myCalendar.isSaturdayOff(true, R.color.white, R.color.red);

//        myCalendar.isSundayOff(true, "#658745", "#254632");
//        myCalendar.isSundayOff(true, R.color.white, R.color.red);

        myCalendar.setExtraDatesOfMonthBackgroundColor("#e6e6e6");
//        myCalendar.setExtraDatesOfMonthBackgroundColor(R.color.black);

        myCalendar.setExtraDatesOfMonthTextColor("#756325");
//        myCalendar.setExtraDatesOfMonthTextColor(R.color.black);

//        myCalendar.setDatesOfMonthBackgroundColor(R.drawable.event_view);
        myCalendar.setDatesOfMonthBackgroundColor("#ffffff");
//        myCalendar.setDatesOfMonthBackgroundColor(R.color.black);

        myCalendar.setDatesOfMonthTextColor("#745632");
//        myCalendar.setDatesOfMonthTextColor(R.color.black);

//        myCalendar.setCurrentDateBackgroundColor("#123412");
//        myCalendar.setCurrentDateBackgroundColor(R.color.black);

        myCalendar.setCurrentDateTextColor("#00e600");
//        myCalendar.setCurrentDateTextColor(R.color.black);

        myCalendar.setEventCellBackgroundColor("#ffffff");
//        myCalendar.setEventCellBackgroundColor(R.color.black);

        myCalendar.setEventCellTextColor("#ff9933");
//        myCalendar.setEventCellTextColor(R.color.black);

        myCalendar.addEvent("9-9-2020", "8:00", "8:15", "Today Event 1");
        myCalendar.addEvent("9-9-2020", "9:00", "9:15", "Today Event 1");
        myCalendar.addEvent("8-9-2020", "11:00", "12:15", "Today Event 1");
        myCalendar.addEvent("10-9-2020", "8:15", "8:30", "Today Event 2");
        myCalendar.addEvent("10-9-2020", "8:30", "8:45", "Today Event 3");
        myCalendar.addEvent("11-10-2016", "8:45", "9:00", "Today Event 4");
        myCalendar.addEvent("12-9-2020", "8:00", "8:30", "Today Event 5");
        myCalendar.addEvent("7-9-2020", "9:00", "10:00", "Today Event 6");
        myCalendar.addEvent("13-9-2020", "8:00", "10:00", "Today Event 6");

        myCalendar.getEventList(new GetEventListListener() {
            @Override
            public void eventList(ArrayList<EventModel> eventList) {

                Log.e("tag", "eventList.size():-" + eventList.size());
                for (int i = 0; i < eventList.size(); i++) {
                    Log.e("tag", "eventList.getStrName:-" + eventList.get(i).getStrName());
                }

            }
        });

//        myCalendar.updateEvent(0, "5-10-2016", "8:00", "8:15", "Today Event 111111");

//        myCalendar.deleteEvent(2);

//        myCalendar.deleteAllEvent();

        myCalendar.setBelowMonthEventTextColor("#425684");
//        myCalendar.setBelowMonthEventTextColor(R.color.black);

        myCalendar.setBelowMonthEventDividerColor("#635478");
//        myCalendar.setBelowMonthEventDividerColor(R.color.black);

        myCalendar.setHolidayCellBackgroundColor("#654248");
//        myCalendar.setHolidayCellBackgroundColor(R.color.black);

        myCalendar.setHolidayCellTextColor("#d590bb");
//        myCalendar.setHolidayCellTextColor(R.color.black);

        myCalendar.setHolidayCellClickable(false);
        myCalendar.addHoliday("2-11-2016");
        myCalendar.addHoliday("8-11-2016");
        myCalendar.addHoliday("12-11-2016");
        myCalendar.addHoliday("13-11-2016");
        myCalendar.addHoliday("8-10-2016");
        myCalendar.addHoliday("10-12-2016");


//        myCalendar.setCalendarDate(5, 10, 2016);

    }

    private void getIndividualSchedules() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/users/"+SingletonSession.Instance().getEmail()+"/schedules").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                String mMessage = e.getMessage().toString();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(mMessage);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonEvents = jsonObject.getJSONArray("events");
                        for (int j = 0; j < jsonEvents.length(); j++) {
                            JSONObject jsonEventsObject = jsonEvents.getJSONObject(j);
                            JSONArray jsonactivities = jsonEventsObject.getJSONArray("activities");
                            for (int k = 0; k < jsonactivities.length(); k++) {
                                JSONObject jsonActivitiesObject = jsonactivities.getJSONObject(k);
                                String TestName=jsonActivitiesObject.getString("name");
                                teamNameList.add(TestName);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getTeamName() {

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/users/"+SingletonSession.Instance().getEmail()+"/teams?type=member").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(mMessage);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String TeamName=jsonObject.getString("name");
                        getTeamSchedule(TeamName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getTeamSchedule(String teamName) {

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/teams/"+teamName+"/schedules").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(mMessage);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonEvents = jsonObject.getJSONArray("events");
                        for (int j = 0; j < jsonEvents.length(); j++) {
                            JSONObject jsonEventsObject = jsonEvents.getJSONObject(j);
                            JSONArray jsonactivities = jsonEventsObject.getJSONArray("activities");
                            for (int k = 0; k < jsonactivities.length(); k++) {
                                JSONObject jsonActivitiesObject = jsonactivities.getJSONObject(k);
                                String TestName=jsonActivitiesObject.getString("name");
                                teamNameList.add(TestName);
                            }
                        }
                    }
                    adapter = new ScheduleAdapter(getApplicationContext(),teamNameList);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
        MenuItem item = menu.findItem(R.id.menu_change_user);
        String Role = SingletonSession.Instance().getRole();
        if ("athlete".equalsIgnoreCase(Role)) {
            item.setVisible(false);
        } else {
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
              //  numbersList = (ArrayList<String>) getIntent().getSerializableExtra("arraylist");
                roleList = SingletonSession.Instance().getRoleList();
                MyListAdapter adapter = new MyListAdapter(this, roleList);
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
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("arraylist", roleList);
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
              //  scopeList = (ArrayList<String>) getIntent().getSerializableExtra("scopelist");
                scopeList = SingletonSession.Instance().getScopeList();
                MyListScopeAdapter adapterScope = new MyListScopeAdapter(this, scopeList);
                listViewScope.setAdapter(adapterScope);
                adapterScope.notifyDataSetChanged();
                builderScope.setView(view);
                listViewScope.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String ScopeName = String.valueOf(listViewScope.getItemAtPosition(position));
                        txt_scope.setText(ScopeName);
                    }
                });
                builderScope.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderScope.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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

            this.context = context;
            this.maintitle = maintitle;

        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.item_view_user_list, null, true);
            TextView titleText = (TextView) rowView.findViewById(R.id.title);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            String value = maintitle.get(position);
            titleText.setText(value);

            return rowView;

        }

        ;
    }

    public static class MyListScopeAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final ArrayList<String> maintitle;

        public MyListScopeAdapter(Activity context, ArrayList<String> maintitle) {
            super(context, R.layout.item_view_user_list, maintitle);
            // TODO Auto-generated constructor stub

            this.context = context;
            this.maintitle = maintitle;

        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.item_view_user_list, null, true);
            TextView titleText = (TextView) rowView.findViewById(R.id.title);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            String value = maintitle.get(position);
            titleText.setText(value);

            return rowView;

        }

        ;
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
        txt_user_name = (TextView) navHeader.findViewById(R.id.txt_user_name);
        txt_scope = (TextView) navHeader.findViewById(R.id.txt_scope);
        navigationView.setNavigationItemSelectedListener(this);

       /* String name = getIntent().getStringExtra("Name");
        Role = getIntent().getStringExtra("Role");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NavItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_name", name);
        editor.apply();*/

        txt_user_name.setText(SingletonSession.Instance().getUsername());
        txt_scope.setText(SingletonSession.Instance().getScope());
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
        } else if (id == R.id.navigation_schedule) {
            Intent homepage = new Intent(Athlete_Home_Activity.this, ScheduleActivity.class);
            startActivity(homepage);
        } else if (id == R.id.nav_athlete_setting) {
            Intent homepage = new Intent(Athlete_Home_Activity.this, SettingsActivity.class);
            startActivity(homepage);
        } else if (id == R.id.nav_logout) {
            builder.setTitle("ATIUM");
            builder.setMessage("Are you sure to logout ?.")
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
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN);
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                }
            });
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
