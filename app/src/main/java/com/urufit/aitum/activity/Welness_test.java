package com.urufit.aitum.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.google.android.material.slider.RangeSlider;
import com.trafi.ratingseekbar.RatingSeekBar;
import com.urufit.aitum.R;
import com.urufit.aitum.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Welness_test extends AppCompatActivity {

    TextView ratingLabel;
    private Question q_data;
    private TextView textview_q_title;
    private Button button_continue;
    LinearLayout linearLayout;
    String Token;
    String JSON_STR;
    Toolbar toolbar;
    ArrayList<Double> arrayList = new ArrayList<Double>();
    Button btnSubmit;
    JSONObject obj;
    String email;
    double IntValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy);
        linearLayout = findViewById(R.id.linearlayout);
        btnSubmit = findViewById(R.id.button_continue);

        toolbar = (Toolbar) findViewById(R.id.tool); // get the reference of Toolbar
        toolbar.setTitle("Wellness Questionnaire");
        setSupportActionBar(toolbar);


        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
                fetchActivities();
            }

            @Override
            public void onError(Exception e) {

            }
        });

        AWSMobileClient.getInstance().getUserAttributes(new com.amazonaws.mobile.client.Callback<Map<String, String>>() {
            @Override
            public void onResult(Map<String, String> result) {
                String name = result.get("given_name");
                email = result.get("email");
            }

            @Override
            public void onError(Exception e) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendWellnessQuestions();
            }
        });
    }

    private void sendWellnessQuestions() {

        JSONObject json = new JSONObject();
        try {
            json.put("name", "wellness");
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonEntries = new JSONObject();
            jsonEntries.put("form", "wellness");
            JSONObject jsonval = new JSONObject();
            jsonval.put("sleep_quality", arrayList.get(0));
            jsonval.put("sleep_duration", arrayList.get(1));
            jsonval.put("soreness", arrayList.get(2));
            jsonval.put("mood", arrayList.get(3));
            jsonval.put("hunger", arrayList.get(4));
            jsonval.put("fatigue", arrayList.get(5));
            jsonval.put("motivation", arrayList.get(6));
            //  jsonEntries.put("",jsonEntries);
            jsonEntries.put("data", jsonval.toString());
            jsonArray.put(jsonEntries);
            json.put("entries", jsonArray);
            Log.d("JSONDATA", jsonArray.toString());
            JSON_STR = json.toString();


        } catch (JSONException e) {
            e.printStackTrace();
        }
  /*      try {
            obj = new JSONObject(JSON_STR);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/users/" + email + "/activities/wellness").newBuilder();
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
                Toast.makeText(Welness_test.this, mMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                Log.d("Response", mMessage);
                int resCode = response.code();
//                finish();
                Welness_test.this.runOnUiThread(new Runnable() {
                    public void run() {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Welness_test.this);
                        builder.setTitle("Complete !!!");
                        builder.setMessage("Thanks for your Rating !!!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                });

            }
        });
    }


    private void fetchActivities() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/testClient/activities").newBuilder();
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
                Welness_test.this.runOnUiThread(new Runnable() {
                    public void run() {
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    JSONArray jsonArray = new JSONArray(mMessage);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONObject object = jsonObject.getJSONObject("forms");
                    JSONObject welness = object.getJSONObject("wellness");
                    String temp = welness.getString("template");

                    JSONObject jsonObject1 = new JSONObject(temp);
                    Log.d("", jsonObject1.toString());

                    JSONArray jsonArray1 = jsonObject1.getJSONArray("pages");
                    JSONObject jsonques = jsonArray1.getJSONObject(0);
                    JSONArray jsonElements = jsonques.getJSONArray("elements");
                    arrayList.clear();
                    for (int i = 0; i < jsonElements.length(); i++) {
                        arrayList.add(i, 0.0);
                        JSONObject JsonQuestion = jsonElements.getJSONObject(i);
                        LayoutInflater inflater = getLayoutInflater();
                        View view = inflater.inflate(R.layout.activity_welness_test, null);
                        textview_q_title = (TextView) view.findViewById(R.id.textview_q_title);
                    //    RatingSeekBar ratingSeekBar = (RatingSeekBar) view.findViewById(R.id.rating_seek_bar_one);
                        RangeSlider rangeSlider=view.findViewById(R.id.rangeSlider);
                        ratingLabel = (TextView) view.findViewById(R.id.rating_label_one);
                        int finalI1 = i;
                        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
                            @Override
                            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                                Log.d("total", String.valueOf(value));
                                Log.d("ListSize", String.valueOf(fromUser));
                                double d = value;
                                DecimalFormat f = new DecimalFormat("##.00");
                                System.out.println(f.format(d));
                                IntValue= Double.parseDouble(f.format(d));
                                arrayList.set(finalI1, IntValue);
                            }
                        });
                        int finalI = i;
                        Welness_test.this.runOnUiThread(new Runnable() {
                            public void run() {
                                linearLayout.addView(view, finalI);
                            }
                        });
                        textview_q_title.setText(JsonQuestion.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
