package com.urufit.aitum.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.urufit.aitum.R;

public class ReportsActivity extends AppCompatActivity {
    private static final int PICKFILE_RESULT_CODE = 8778;
    private Button btnChooseFile,btn_Report;
    private TextView tvItemPath;

    private Uri fileUri;
    private String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    //    Button btn_choose=findViewById(R.id.btn_choose_file);
        btnChooseFile =findViewById(R.id.btn_choose_file);
        btn_Report =findViewById(R.id.btn_send_report);
        tvItemPath =findViewById(R.id.tv_file_path);
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

        btn_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReportsActivity.this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ReportsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    filePath = fileUri.getPath();
                    tvItemPath.setText(filePath);
                }

                break;
        }
    }
}
