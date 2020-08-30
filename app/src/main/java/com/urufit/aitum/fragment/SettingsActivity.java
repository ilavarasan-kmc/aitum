package com.urufit.aitum.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.urufit.aitum.R;
import com.urufit.aitum.databinding.FragmentManagerSettingsBinding;
import com.urufit.aitum.model.SettingsModel;
import com.urufit.aitum.ui.ImagePickerActivity;
import com.urufit.aitum.ui.SingletonSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.urufit.aitum.ui.ImagePickerActivity.fileName;

public class SettingsActivity extends AppCompatActivity {
    FragmentManagerSettingsBinding binding;
    String Token;
    TextInputEditText develop_edt;
    SettingsModel viewModel =new SettingsModel();
    String email,sub_name ;
    Uri fileUri;
    public static final int REQUEST_IMAGE = 100;
    private static final int SELECT_PICTURE = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.fragment_manager_settings);
        binding= DataBindingUtil.setContentView(this,R.layout.fragment_manager_settings);

        AWSMobileClient.getInstance().getUserAttributes(new com.amazonaws.mobile.client.Callback<Map<String, String>>() {
            @Override
            public void onResult(Map<String, String> result) {
                sub_name = result.get("sub");
            }

            @Override
            public void onError(Exception e) {

            }
        });

        SharedPreferences sp = getSharedPreferences("PREFS_EMAIL" , Context.MODE_PRIVATE);
        email = sp.getString("Email","");
        Log.d("Email Settings: =",email);


       binding.setViewModel(viewModel);

       //  develop_edt = (TextInputEditText) findViewById(R.id.txt_name);
      //  binding.txtName.setText("name");
        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);fetchUsersdata();
          //      getUserData();
            }
            @Override
            public void onError(Exception e) {

            }
        });
        binding.buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        binding.buttonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAddress();
            }
        });
        binding.buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStats();
            }
        });

        binding.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    selectImage();
                onProfileImageClick();
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
             //   Uri uri = data.getParcelableExtra("path");
                 fileUri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                    // loading profile image from local cache
                    loadProfile(fileUri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProfile(String url) {

        Glide.with(this).load(url)
                .into(binding.imgProfile);
        binding.imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));

        uploadPhotosToServer(url);
    }

    private void uploadPhotosToServer(String url) {
        if (fileUri != null) {
            final String fileName = url;

         /*   if (!validateInputFileName(fileName)) {
                return;
            }
*/
          /*  final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "/" + fileName);

            Log.d("new_file",file.toString());
*/
            // createFile(getApplicationContext(), fileUri, file);
            final File file = new File(fileName);

            TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(getApplicationContext())
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(new AmazonS3Client(AWSMobileClient.getInstance(), Region.getRegion(Regions.AP_SOUTH_1)))
                            .build();

          //  File f = new File(fileUri.getPath());

            //get file
            File photo = new File(fileUri.getPath());

//file name
          String  fileNamePhoto = photo.getName();

//resave file with new name
            File newFile = new File(SingletonSession.Instance().getUserId() +".jpg");
            photo.renameTo(newFile);

            Log.d("ila",newFile.toString());

            TransferObserver uploadObserver =
                    transferUtility.upload(
                            "com.urufit.datacollection",
                            "profile_photos/" +newFile,
                            photo);

            uploadObserver.setTransferListener(new TransferListener() {

                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                        Log.d("",state.toString());

                        //   file.delete();
                    } else if (TransferState.FAILED == state) {
                        Toast.makeText(getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
                        Log.d("",state.toString());
                        //      file.delete();
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;
                    Log.d("", String.valueOf(percentDone));
                    //    tvFileName.setText("ID:" + id + "|bytesCurrent: " + bytesCurrent + "|bytesTotal: " + bytesTotal + "|" + percentDone + "%");
                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                }

            });
        }

    }

    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(SettingsActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(SettingsActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }



    private void updateProfile() {

        OkHttpClient client = new OkHttpClient();
        //String email="codefordina@gmail.com";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",binding.txtName.getText().toString());
            jsonObject.put("gender","Male");
            jsonObject.put("email",binding.txtEmail.getText().toString());
            jsonObject.put("dob",binding.edtDob.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://y16dosf4mh.execute-api.ap-south-1.amazonaws.com/main/v1/users/"+email+"/info").newBuilder();
        String url = urlBuilder.build().toString();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        okhttp3.Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .post(body)
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
                Log.d("Response came",mMessage);

            }
        });
    }

    private void updateAddress() {
        OkHttpClient client = new OkHttpClient();
     //   String email="codefordina@gmail.com";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("primary_ph",binding.txtMobilePrimary.getText().toString());

            JSONObject jsonAddress=new JSONObject();
            jsonAddress.put("line1",binding.txtAddress.getText().toString());
            jsonAddress.put("state",binding.txtState.getText().toString());
            jsonAddress.put("city",binding.txtCity.getText().toString());
            jsonAddress.put("country",binding.edtCountry.getText().toString());
            jsonObject.put("address",jsonAddress);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://y16dosf4mh.execute-api.ap-south-1.amazonaws.com/main/v1/users/"+email+"/contact").newBuilder();
        String url = urlBuilder.build().toString();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        okhttp3.Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .post(body)
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
                Log.d("Response came",mMessage);
            }
        });
    }

    private void updateStats() {
    }

    /*private void getUserData() {
        String email="codefordina@gmail.com";
        RequestQueue queue = Volley.newRequestQueue(this);
      String  url ="https://y16dosf4mh.execute-api.ap-south-1.amazonaws.com/main/v1/users/"+email;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+Token);
                return params;
            }
        };
        queue.add(postRequest);
    }*/

    private void fetchUsersdata() {
        OkHttpClient client = new OkHttpClient();
       // String email="codefordina@gmail.com";

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://y16dosf4mh.execute-api.ap-south-1.amazonaws.com/main/v1/users/"+email).newBuilder();
        String url = urlBuilder.build().toString();

        okhttp3.Request request = new Request.Builder()
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
                Log.d("Response came",mMessage);
                if (mMessage!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(mMessage);
                     //   SettingsModel settingsModel=new SettingsModel();
                    //    settingsModel.setName(jsonObject.getString("name"));

                        new Thread()
                        {
                            public void run()
                            {
                                SettingsActivity.this.runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        //Do your UI operations like dialog opening or Toast here
                                        String name = null;
                                        try {
                                            name = jsonObject.getString("name");
                                            binding.txtName.setText(name);
                                            binding.txtEmail.setText(jsonObject.getString("email"));
                                            binding.txtMobilePrimary.setText(jsonObject.getString("primary_ph"));
                                            binding.edtDob.setText(jsonObject.getString("dob"));

                                           /* String dob=jsonObject.getString("dob");

                                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                                            try {
                                                Date date1 = df.parse(dob);
                                                DateFormat outputFormatter1 = new SimpleDateFormat("dd-MMM-yyyy");
                                                String output1 = outputFormatter1.format(date1); //
                                                Log.d("date format",output1);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
*/

                                            JSONObject jsonAddress=jsonObject.getJSONObject("address");
                                            binding.txtAddress.setText(jsonAddress.getString("line1"));
                                            binding.txtCity.setText(jsonAddress.getString("city"));
                                            binding.txtState.setText(jsonAddress.getString("state"));
                                            binding.edtCountry.setText(jsonAddress.getString("country"));

                                            JSONObject jsonStats=jsonObject.getJSONObject("stats");
                                            binding.edtHeight.setText(jsonStats.getString("height"));
                                            binding.edtWeight.setText(jsonStats.getString("weight"));
                                            binding.edtArms.setText(jsonStats.getString("arms"));
                                            binding.edtChest.setText(jsonStats.getString("chest"));
                                            binding.edtHips.setText(jsonStats.getString("hips"));
                                            binding.edtThigs.setText(jsonStats.getString("thighs"));
                                            binding.edtCalves.setText(jsonStats.getString("calves"));
                                            binding.edtWaist.setText(jsonStats.getString("waist"));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });
                            }
                        }.start();

                     viewModel.notifyChange();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}