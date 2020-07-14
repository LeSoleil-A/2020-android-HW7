package com.bytedance.videoplayer;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.Locale;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ImageActivity extends AppCompatActivity {

    private final static int REQUEST_CAMERA = 123;
    private final static String IMAGE_URL = "https://bkimg.cdn.bcebos.com/pic/0e2442a7d933c895d143f183a45a64f082025bafaab8?x-bce-process=image/watermark,g_7,image_d2F0ZXIvYmFpa2U4MA==,xp_5,yp_5";
    private final static String IMAGE_URL2 = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2270565650,665135006&fm=11&gp=0.jpg";
    private ImageView mImageView;
    private File imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        initButton();
    }

    private void initButton() {
        mImageView = findViewById(R.id.imageView);

        findViewById(R.id.buttonCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSystemCamera();
            }
        });

        findViewById(R.id.buttonLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }

    private void loadImage() {
////        RequestOptions cropOptions = new RequestOptions();
////        cropOptions = cropOptions.circleCrop();
        Glide.with(ImageActivity.this)
                .load(IMAGE_URL)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.group_1)
                .error(R.drawable.icon_failure)
                .fallback(R.drawable.ic_launcher_background)
////                .thumbnail(Glide.with(this).load(IMAGE_URL2))
                .transition(withCrossFade(1000))
                .into(mImageView);
    }

    private void openSystemCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageName = String.format(Locale.getDefault(), "img_%d.jpg", System.currentTimeMillis());
        imagePath = new File(getExternalFilesDir(""), imageName);
        Uri outUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imagePath);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Glide.with(this).load(imagePath).into(mImageView);
        }
    }
}
