package com.bytedance.videoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class LocalVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_video);

        File dir = new File("D:/Android_projects/wyuxing-Chapter-7-master/Chapter-7/assignment/VideoPlayer/app/src/main/res/raw/bytedance.mp4");
        Log.d("File path ", dir.getPath());
        Uri uri = Uri.fromFile(dir);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "video/*");
        startActivity(intent);
    }
}
