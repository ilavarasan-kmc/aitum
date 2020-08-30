package com.urufit.aitum.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.urufit.aitum.R;
import com.urufit.aitum.adapter.ExpandableListAdapter;
import com.urufit.aitum.ui.SingletonSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddScheduleActivity extends AppCompatActivity {

    private List<String> parent_title;
    private List<String> parent_title_size = new ArrayList<>();
    private HashMap<String, List<String>> child_title;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    String Token, email;
    HashSet<String> set;
    ArrayList<String> withoutDupicates;
    TextInputEditText title;
    Toolbar toolbar;
    int CalenderId;
    String ChildValue;

    private static final String TAG = "Sample";

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";
    private TextView textView;

    private SwitchDateTimeDialogFragment dateTimeFragment;
    private SwitchDateTimeDialogFragment endDateTimeFragment;
    TextInputEditText start_date ;
    TextInputEditText end_date;

    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        toolbar = (Toolbar) findViewById(R.id.toolbar_customs); // get the reference of Toolbar
        toolbar.setTitle("Add Schedule");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        MaterialButton btn_submit = findViewById(R.id.btn_submit);
      start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        TextInputEditText start_time = findViewById(R.id.start_time);
        TextInputEditText end_time = findViewById(R.id.end_time);
        title = findViewById(R.id.edt_title);
        parent_title = new ArrayList<>();
        child_title = new HashMap<>();
        expandableListAdapter = new ExpandableListAdapter(parent_title, child_title, this);
        expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                Toast.makeText(AddScheduleActivity.this, parent_title.get(i) + " expanded", Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                //     Toast.makeText(AddScheduleActivity.this, "hlo", Toast.LENGTH_SHORT).show();
                Toast.makeText(AddScheduleActivity.this, parent_title.get(i) + " -> " + child_title.get(parent_title.get(i)).get(i1), Toast.LENGTH_SHORT).show();
                ChildValue = child_title.get(parent_title.get(i)).get(i1);
                Log.d("Child_value--->", ChildValue);
                return true;
            }
        });
        String[] Events = new String[]{"Personal", "Team"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item,
                Events);

        AutoCompleteTextView event_type = findViewById(R.id.event_type);
        event_type.setAdapter(adapter);

        event_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalenderId = position;
            }
        });


        AWSMobileClient.getInstance().getUserAttributes(new com.amazonaws.mobile.client.Callback<Map<String, String>>() {
            @Override
            public void onResult(Map<String, String> result) {
                email = result.get("email");
            }

            @Override
            public void onError(Exception e) {

            }
        });
        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
                fetchActivityList();
            }

            @Override
            public void onError(Exception e) {

            }
        });

        /*start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(AddScheduleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                start_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();

            }
        });*/


        /*end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(AddScheduleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                end_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        start_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        end_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });*/


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSchedule();
            }
        });


        //Date picker

        if (savedInstanceState != null) {
            // Restore value from saved state
            start_date.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
            end_date.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
        }

        // Construct SwitchDateTimePicker
        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
            );
        }

        // Optionally define a timezone
        dateTimeFragment.setTimeZone(TimeZone.getDefault());

        // Init format
        //  final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setHighlightAMPMSelection(false);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                start_date.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                start_date.setText("");
            }
        });

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Re-init each time
                dateTimeFragment.startAtCalendarView();
                dateTimeFragment.setDefaultDateTime(new GregorianCalendar().getTime());
                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });

        //End date time picker

        // Construct SwitchDateTimePicker
        endDateTimeFragment= (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(endDateTimeFragment == null) {
            endDateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
            );
        }

        // Optionally define a timezone
        endDateTimeFragment.setTimeZone(TimeZone.getDefault());

        // Init format
        //  final SimpleDateFormat myDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm", java.util.Locale.getDefault());
        final SimpleDateFormat myEndDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", java.util.Locale.getDefault());
        // Assign unmodifiable values
        endDateTimeFragment.set24HoursMode(false);
        endDateTimeFragment.setHighlightAMPMSelection(false);
        endDateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        endDateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            endDateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        endDateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                end_date.setText(myEndDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                end_date.setText("");
            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Re-init each time
                endDateTimeFragment.startAtCalendarView();
                endDateTimeFragment.setDefaultDateTime(new GregorianCalendar().getTime());
                endDateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });


    }


    private void addSchedule() {
        JSONObject json = new JSONObject();
        try {
            json.put("created_by", email);
            json.put("start", start_date);
            json.put("end", end_date);

            JSONArray jsonEvents = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("calendarId", CalenderId);
            jsonObject.put("title", title.getText().toString());
            JSONArray jsonActivities = new JSONArray();
            JSONObject object = new JSONObject();
            object.put("name", ChildValue);
            jsonEvents.put(jsonObject);
            jsonActivities.put(object);
            jsonObject.put("activities", jsonActivities);
            json.put("events", jsonEvents);

            Log.d("json", json.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api-dev.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/users/" + SingletonSession.Instance().getEmail() + "/schedules").newBuilder();
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .post(RequestBody
                        .create(MediaType
                                        .parse("application/json"),
                                json.toString()
                        ))
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
                Log.d("Response", mMessage);
                AddScheduleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddScheduleActivity.this, "Events added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });
    }

    private void fetchActivityList() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api-dev.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/activities").newBuilder();
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
                Toast.makeText(getApplicationContext(), mMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                Log.d("Response", mMessage);
                try {
                    JSONArray jsonArray = new JSONArray(mMessage);
                    List<String> endurance = new ArrayList<>();
                    List<String> kinanthropometry = new ArrayList<>();
                    List<String> technical_assessment = new ArrayList<>();
                    List<String> cod = new ArrayList<>();
                    List<String> sprint = new ArrayList<>();
                    List<String> power = new ArrayList<>();
                    List<String> strength = new ArrayList<>();
                    List<String> load_quantification = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        parent_title_size.add(jsonObject.getString("type"));
                        set = new HashSet<>(parent_title_size);
                        withoutDupicates = new ArrayList<>(set);
                        String well = jsonObject.getString("type");
                        List<String> wellbeing = new ArrayList<>();
                        //     List<String> endurance=new ArrayList<>();
                        wellbeing.add(jsonObject.getString("name"));
                        if ("wellbeing".equalsIgnoreCase(well)) {
                            // parent one
                            wellbeing = new ArrayList<>();
                            wellbeing.add(jsonObject.getString("name"));
                            child_title.put("wellbeing", wellbeing);
                        }
                        if ("endurance".equalsIgnoreCase(well)) {
                            endurance.add(jsonObject.getString("name"));
                            child_title.put("endurance", endurance);
                        }
                        if ("kinanthropometry".equalsIgnoreCase(well)) {
                            kinanthropometry.add(jsonObject.getString("name"));
                            child_title.put("kinanthropometry", kinanthropometry);
                        }
                        if ("technical_assessment".equalsIgnoreCase(well)) {
                            technical_assessment.add(jsonObject.getString("name"));
                            child_title.put("technical_assessment", technical_assessment);
                        }
                        if ("cod".equalsIgnoreCase(well)) {
                            cod.add(jsonObject.getString("name"));
                            child_title.put("cod", cod);
                        }
                        if ("sprint".equalsIgnoreCase(well)) {
                            sprint.add(jsonObject.getString("name"));
                            child_title.put("sprint", sprint);
                        }
                        if ("power".equalsIgnoreCase(well)) {
                            power.add(jsonObject.getString("name"));
                            child_title.put("power", power);
                        }
                        if ("strength".equalsIgnoreCase(well)) {
                            strength.add(jsonObject.getString("name"));
                            child_title.put("strength", strength);
                        }
                        if ("load_quantification".equalsIgnoreCase(well)) {
                            load_quantification.add(jsonObject.getString("name"));
                            child_title.put("load_quantification", load_quantification);
                        }
                        ArrayList<String> duplicates = new ArrayList<String>();

                        Iterator<String> dupIter = parent_title_size.iterator();
                        while (dupIter.hasNext()) {
                            String dupWord = dupIter.next();
                            if (parent_title_size.contains(dupWord)) {
                                duplicates.add(dupWord);
                            } else {
                                withoutDupicates.add(dupWord);
                            }
                        }
                    }
                    for (int i = 0; i < withoutDupicates.size(); i++) {
                        parent_title.add(withoutDupicates.get(i));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AddScheduleActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        expandableListAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current textView
        savedInstanceState.putCharSequence(STATE_TEXTVIEW, textView.getText());
        super.onSaveInstanceState(savedInstanceState);
    }
}
