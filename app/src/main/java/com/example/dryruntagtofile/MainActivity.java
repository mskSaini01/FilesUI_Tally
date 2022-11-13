package com.example.dryruntagtofile;

import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Components
        MaterialButton filesBtn = findViewById(R.id.filesButton);
        MaterialButton tagsBtn = findViewById(R.id.tagsButton);

        //Click on FILES button
        filesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                    String path = Environment.getExternalStorageDirectory().getPath();
                    intent.putExtra("path", path);
                    startActivity(intent);
                }else{ //permission denied
                    requestPermission();
                }
            }
        });

        //Click on TAGS button
        tagsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    Intent intent = new Intent(MainActivity.this, TagsListActivity.class);
                    startActivity(intent);
                }else{ //permission denied
                    requestPermission();
                }
            }
        });
    }

    private boolean checkPermission(){
        return Environment.isExternalStorageManager();
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "Storage permissions required", Toast.LENGTH_LONG).show();
            return ;
        }
        Intent intent = new Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
        this.startActivity(intent);
        // ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1000);
    }
}