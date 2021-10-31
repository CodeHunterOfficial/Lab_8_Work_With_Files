package com.example.lab_8_work_with_files;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Infles:MainActivity";
    private Button btnCopy;
    private TextView txtDirName;
    boolean resCopy;
    String[] wrong = {
            "images",
            "sounds",
            "webkit"
    };

    String COPY_DIR = "Infles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDirName = findViewById(R.id.edDirName);

        btnCopy = findViewById(R.id.btnCopy);

        btnCopy.setOnClickListener(v -> {
            if (txtDirName.getText().length() > 0) {
                COPY_DIR = String.valueOf(txtDirName.getText());
                final String dir = "/" + COPY_DIR + "/";
                final AssetManager am = getAssets(); //читаем файлы из Assets
                try {
                    String[] files = am.list("");
                    for (int i = 0; i < files.length; i++) {
                        if (!CheckMass(files[i], wrong)) {

                            dirChecker(dir); // checking directory
                            copy(files[i], dir);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    static public boolean CheckMass(String text, String[] arr) {
        boolean res = false;
        int strLenght = arr.length;
        for (int i = 0; i < strLenght; i++) {
            if (text.equals(arr[i])) {
                res = true;
                break;
            }
        }
        return res;
    }

    private void dirChecker(String dir) {
        File Directory = new File("/sdcard" + dir);
        Log.i(LOG_TAG, "/sdcard" + dir + " - dir check");
        if (!Directory.isDirectory()) {
            Directory.mkdirs();
        }
    }

    private boolean copy(String fileName, String dir) {
        try {
            AssetManager am = getAssets();
            File destinationFile = new File(Environment.getExternalStorageDirectory() + dir + fileName);  //путь
            InputStream in = am.open(fileName); // открываем файл
            FileOutputStream f = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
            resCopy = true;
        } catch (Exception e) {
            Log.d(LOG_TAG, e.getMessage());
            resCopy = false;
        }
        return resCopy;
    }
}