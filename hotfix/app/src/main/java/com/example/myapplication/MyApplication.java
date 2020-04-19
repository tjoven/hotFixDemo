package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import java.io.File;

import androidx.annotation.RequiresApi;

public class MyApplication extends Application {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        String path = getExternalFilesDir("fix").getPath()+"/path.jar";
        Hotfix.fix(this,new File(path));
    }

}
