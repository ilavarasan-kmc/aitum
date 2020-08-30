package com.urufit.aitum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.urufit.aitum.R;
import com.urufit.aitum.model.ServiceModel;
import com.urufit.aitum.ui.Toolbar_customs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Wellbeing extends AppCompatActivity {

    Toolbar toolbar;
    private static final int SURVEY_REQUEST = 1337;
    String Token;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_welbeing);
 //       Spinner spinner = (Spinner) findViewById(R.id.spinner_test);
       webView=findViewById(R.id.webView);
       // Button btn_schedule=findViewById(R.id.btn_schedule);

        toolbar = findViewById(R.id.toolbar_customs);
        toolbar.setTitle("Wellbeing Report");
        setSupportActionBar(toolbar);

        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
                loadWellnessDashboard();
            }
            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void loadWellnessDashboard() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/testClient/users/manager@atium.in/scopes/manager/dashboards/wellness").newBuilder();
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

                String mMessage = response.body().string();
                Log.w("msg", mMessage);
                try {
                    JSONObject json = new JSONObject(mMessage);
                    String EmbUrl=json.getString("EmbedUrl");
                   String htmlcon="<iframe  src="+EmbUrl+"></iframe>";
                    Wellbeing.this.runOnUiThread(new Runnable() {
                        public void run() {
                       //     webView.loadUrl(EmbUrl);
                            webView.loadData("<iframe src=\"http://www.google.com\"></iframe>", "text/html",
                                    "utf-8");
                            webView.getSettings().setJavaScriptEnabled(true);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
}
