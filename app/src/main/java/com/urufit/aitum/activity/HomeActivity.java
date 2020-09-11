package com.urufit.aitum.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
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
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.desai.vatsal.mydynamiccalendar.EventModel;
import com.desai.vatsal.mydynamiccalendar.GetEventListListener;
import com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar;
import com.desai.vatsal.mydynamiccalendar.OnDateClickListener;
import com.google.android.material.navigation.NavigationView;
import com.urufit.aitum.R;
import com.urufit.aitum.adapter.TodoListAdapter;
import com.urufit.aitum.fragment.ManagerCalenderFragment;
import com.urufit.aitum.fragment.SettingsActivity;
import com.urufit.aitum.ui.SingletonSession;
import com.urufit.aitum.ui.VerticalScrollTextview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public AlertDialog.Builder builder;
    CardView card_monitoring, card_activity, card_medical, card_profile;
    private View navHeader;
    TextView txt_name = null, txt_user_name, txt_scope, username, about;
    ImageView nav_logo;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    String Role;
    ArrayList<String> roleList = new ArrayList<>();
    ArrayList<String> scopeList = new ArrayList<>();
    public String MY_PREFS_NAME = "User_Profile";
    private Uri fileUri;
    private Bitmap bitmap;
    //track Choosing Image Intent
    private static final int CHOOSING_IMAGE_REQUEST = 1234;
    private MyDynamicCalendar myCalendar;

    RecyclerView recyclerView;
    String Token;
    TodoListAdapter adapter;
    ArrayList<String> teamNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BindMethod();
    }

    private void BindMethod() {
        toolbar = findViewById(R.id.tool);
        toolbar.setTitle("ATIUM");
      //  toolbar.setTitleTextAppearance(getApplicationContext(),R.style.CustomFontStyle);
        setSupportActionBar(toolbar);
        txt_name = findViewById(R.id.txt_name);
        about = findViewById(R.id.about);
        username = findViewById(R.id.username);
        card_monitoring = findViewById(R.id.card_monitoring);
        card_activity = findViewById(R.id.card_activity);
        card_medical = findViewById(R.id.card_medical);
        card_profile = findViewById(R.id.card_player_prof);
        TextView dayTime = findViewById(R.id.day_time);
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scroll));

        VerticalScrollTextview scrollingText = (VerticalScrollTextview) findViewById(R.id.txt_up);
        VerticalScrollTextview scrollingText1 = (VerticalScrollTextview) findViewById(R.id.txt_up1);
        scrollingText.setSelected(true);
        scrollingText1.setSelected(true);
        scrollingText.setMovementMethod(new ScrollingMovementMethod());
        scrollingText1.setMovementMethod(new ScrollingMovementMethod());
        scrollingText.scroll();
        scrollingText1.scroll();

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

        card_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayerHomeActivity.class);
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

        builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        drawerLayout = findViewById(R.id.drawer_layout);
        //  navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        hidemenu();
        // downloadImage();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        myCalendar.addEvent("4-9-2020", "8:00", "8:15", "Today Event 1");
        myCalendar.addEvent("4-9-2020", "9:00", "9:15", "Today Event 1");
        myCalendar.addEvent("4-9-2020", "11:00", "12:15", "Today Event 1");
        myCalendar.addEvent("05-9-2020", "8:15", "8:30", "Today Event 2");
        myCalendar.addEvent("06-9-2020", "8:30", "8:45", "Today Event 3");
        myCalendar.addEvent("05-10-2016", "8:45", "9:00", "Today Event 4");
        myCalendar.addEvent("1-9-2020", "8:00", "8:30", "Today Event 5");
        myCalendar.addEvent("3-9-2020", "9:00", "10:00", "Today Event 6");
        myCalendar.addEvent("3-9-2020", "8:00", "10:00", "Today Event 6");

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

    }

    private void showAgendaView() {

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

    }

    private void getIndividualSchedules() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/users/" + SingletonSession.Instance().getEmail() + "/schedules").newBuilder();
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
                                String TestName = jsonActivitiesObject.getString("name");
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
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/users/" + SingletonSession.Instance().getEmail() + "/teams?type=member").newBuilder();
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
                        String TeamName = jsonObject.getString("name");
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
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/teams/" + teamName + "/schedules").newBuilder();
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
                                String TestName = jsonActivitiesObject.getString("name");
                                teamNameList.add(TestName);
                            }
                        }
                    }
                    adapter = new TodoListAdapter(getApplicationContext(), teamNameList);

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

    private void downloadImage() {
       /* TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance(), Region.getRegion(Regions.AP_SOUTH_1)))
                        .build();

        fileName=SingletonSession.Instance().getUserId()+".jpg";

        File file=new File(fileName);

        TransferObserver downloadObserver =
           //     transferUtility.download("profile_photos/"+SingletonSession.Instance().getUserId(),fileName);
                transferUtility.download( "com.urufit.datacollection", "profile_photos/" ,file);

        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {

                    Log.d("Succss", String.valueOf(state));
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                Log.d("Error", String.valueOf(ex));
            }

        });*/


        // if (fileUri != null) {

        final String fileName = SingletonSession.Instance().getUserId() + ".jpg";
        final File file = new File(Environment.getExternalStorageDirectory(), "read.me");
        Uri uri = Uri.fromFile(file);
        File auxFile = new File(uri.toString());

        try {
            //     final File localFile = File.createTempFile("images", getFileExtension(fileUri));

            TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(getApplicationContext())
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(new AmazonS3Client(AWSMobileClient.getInstance(), Region.getRegion(Regions.AP_SOUTH_1)))
                            .build();

            TransferObserver downloadObserver =
                    transferUtility.download("profile_photos/" + fileName, auxFile);

            downloadObserver.setTransferListener(new TransferListener() {

                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        Toast.makeText(getApplicationContext(), "Download Completed!", Toast.LENGTH_SHORT).show();

                        //   tvFileName.setText(fileName + "." + getFileExtension(fileUri));
                        Bitmap bmp = BitmapFactory.decodeFile(auxFile.getAbsolutePath());
                        //      nav_logo.setImageBitmap(bmp);
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                    //     tvFileName.setText("ID:" + id + "|bytesCurrent: " + bytesCurrent + "|bytesTotal: " + bytesTotal + "|" + percentDone + "%");
                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                }

            });
        } catch (Exception e) {

        }
      /*  else {
            Toast.makeText(this, "Upload file before downloading", Toast.LENGTH_LONG).show();
        }*/
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
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
                if (roleList != null) {
                //    numbersList = (ArrayList<String>) getIntent().getSerializableExtra("arraylist");
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
               // scopeList = (ArrayList<String>) getIntent().getSerializableExtra("scopelist");
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
                        SingletonSession.Instance().setScope(ScopeName);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (bitmap != null) {
            bitmap.recycle();
        }

        if (requestCode == CHOOSING_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    }

    private void hidemenu() {
        navigationView = findViewById(R.id.navigationView);
        navHeader = navigationView.getHeaderView(0);
        txt_user_name = (TextView) navHeader.findViewById(R.id.txt_user_name);
        txt_scope = (TextView) navHeader.findViewById(R.id.txt_scope);
        nav_logo = (ImageView) navHeader.findViewById(R.id.nav_logo);
        navigationView.setNavigationItemSelectedListener(this);

        String name = SingletonSession.Instance().getScope();
       String Scope = name.substring(0, 1).toUpperCase() + name.substring(1);
        txt_user_name.setText(SingletonSession.Instance().getUsername());
        txt_name.setText(SingletonSession.Instance().getUsername());
        txt_scope.setText(Scope);
        username.setText(SingletonSession.Instance().getUsername());
        about.setText(SingletonSession.Instance().getEmail());

        Menu nav_Menu = navigationView.getMenu();
        if ("user".equalsIgnoreCase(Role)) {
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
        if ("athlete".equalsIgnoreCase(Role)) {
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
        } else if (id == R.id.nav_daily_avtivity) {
            Intent homepage = new Intent(HomeActivity.this, Performance_Assessment.class);
            startActivity(homepage);
        } else if (id == R.id.nav_event_calendar) {
            Intent homepage = new Intent(HomeActivity.this, ManagerCalenderFragment.class);
            startActivity(homepage);
        } else if (id == R.id.nav_medical) {
            Intent homepage = new Intent(HomeActivity.this, Clinical_assement_follow_up.class);
            startActivity(homepage);
        } else if (id == R.id.nav_service) {
            Intent homepage = new Intent(HomeActivity.this, ServiceActivity.class);
            startActivity(homepage);
        } else if (id == R.id.nav_reports) {
            Intent homepage = new Intent(HomeActivity.this, ReportsActivity.class);
            startActivity(homepage);
        } else if (id == R.id.nav_survey) {
            Intent homepage = new Intent(HomeActivity.this, Survey_Test.class);
            startActivity(homepage);
        } else if (id == R.id.nav_setting) {
            Intent homepage = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(homepage);
        } else if (id == R.id.nav_logout) {
            builder.setTitle("ATIUM");
            builder.setMessage("Are you sure to logout ?.")
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