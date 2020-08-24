package com.urufit.aitum.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.urufit.aitum.R;
import com.urufit.aitum.activity.HomeActivity;
import com.urufit.aitum.activity.LoginActivity;


public class Toolbar_customs extends AppCompatActivity {
    public AlertDialog.Builder builder;
    public LinearLayout reson_lay;
    public Button send_msg_btn;
    public TextInputEditText reson_edit;
    public String sResonMessage;
    //  public User user;
    public String sUserID;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public Bundle bundle, bundle2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
          /*  case R.id.toolbar_logout:
                logoutMethod();
                break;*/

            case R.id.toolbar_notification:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
               // builder.setTitle("With RatingBar");
                View dialogLayout = inflater.inflate(R.layout.alert_dialog, null);
             //   final RatingBar ratingBar = dialogLayout.findViewById(R.id.ratingBar);
                builder.setView(dialogLayout);
              /*  builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Rating is " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                    }
                });*/
                builder.show();
                break;
            case R.id.toolbar_home:
                Intent homepage = new Intent(this, HomeActivity.class);
                startActivity(homepage);
                break;

          /*  case R.id.toolbar_referesh:
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;*/

            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }


    public void logoutMethod() {
        builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        // builder.setIcon(R.drawable.logo_only);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure ? ..")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences myPrefs = getSharedPreferences("Amaze_travels", MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.clear();
                        editor.apply();
                        dialog.cancel();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
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

}

