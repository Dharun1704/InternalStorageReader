package com.example.internalstoragereader;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> fileList=new ArrayList<>();
    public  ListView myDirectory;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDirectory= (ListView) findViewById(R.id.myDirectory);
        getWindow().setNavigationBarColor(Color.parseColor("#252525"));


        String[] permissionArrays = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int REQUEST_CODE = 101;

        requestPermissions(permissionArrays,REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 ) {
            boolean isPermitted = false;
            for (int i = 0; i < grantResults.length; i++) {
                String permission = permissions[i];
                isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }

            if (isPermitted) {
                File root=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                ListDir(root);
            }
            else
                finish();
        }
    }

    void ListDir(File f){
        File[] files = f.listFiles();
        fileList.clear();
        if (files != null) {
            for (File file : files) {
                fileList.add(file.getPath());
            }
        }

        Adapter directoryList=new Adapter(this,fileList);
        myDirectory.setAdapter(directoryList);

        myDirectory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ListView listView = view.findViewById(R.id.dNameList);
                ImageView Arrow = view.findViewById(R.id.arrow);
                if (listView.getVisibility() == View.GONE)
                {
                    listView.setVisibility(View.VISIBLE);
                    Arrow.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.arrow_down));
                }
                else
                {
                    listView.setVisibility(View.GONE);
                    Arrow.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.arrow_right));
                }
            }
        });
    }
}