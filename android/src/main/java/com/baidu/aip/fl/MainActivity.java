/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.aip.fl;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.aip.FaceSDKManager;
import com.baidu.aip.fl.exception.FaceError;
import com.baidu.aip.fl.model.AccessToken;
import com.baidu.aip.fl.utils.OnResultListener;
import com.baidu.idl.facesdk.FaceSDK;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.xiaoming.fn.baidu.face.BaiduFace;
import com.xiaoming.fn.baidu.face.R;
import com.xiaoming.fn.baidu.face.RNBaiduFaceModule;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mTrackBtn;
    private Button mAttrBtn;
    private Button mDetectBtn;
    private BaiduFace b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        addListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        b.start();
        Log.d("xm", "onResume: ");
    }

    private void initView() {
        mTrackBtn = (Button) findViewById(R.id.track_btn);
        mAttrBtn = (Button) findViewById(R.id.attr_btn);
        mDetectBtn = (Button) findViewById(R.id.detect_btn);
    }

    private void addListener() {
        mTrackBtn.setOnClickListener(this);
        mAttrBtn.setOnClickListener(this);
        mDetectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            return;
        }

        int id = v.getId();
        if (id == R.id.track_btn) {
            Intent itTrack = new Intent(MainActivity.this, TrackActivity.class);
            startActivity(itTrack);
        } else if (id == R.id.attr_btn) {
//            Intent itAttr = new Intent(MainActivity.this, AttrActivity.class);
//            startActivity(itAttr);
            b.start();
        } else if (id == R.id.detect_btn) {// TODO 实时人脸检测
//            Intent itDetect = new Intent(MainActivity.this, DetectActivity.class);
//            startActivity(itDetect);
            b.start();
        }

    }
}
