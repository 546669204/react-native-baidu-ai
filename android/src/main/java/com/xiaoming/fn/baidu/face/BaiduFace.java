package com.xiaoming.fn.baidu.face;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.baidu.aip.ImageFrame;
import com.baidu.aip.face.CameraImageSource;
import com.baidu.aip.face.FaceDetectManager;
import com.baidu.aip.face.FaceFilter;
import com.baidu.aip.face.PreviewView;
import com.baidu.aip.face.camera.ICameraControl;
import com.baidu.idl.facesdk.FaceInfo;
import com.baidu.idl.facesdk.FaceTracker;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.lang.ref.WeakReference;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class BaiduFace extends FrameLayout implements LifecycleEventListener {

    public static FaceDetectManager faceDetectManager;
    public Boolean isShowFrame = false;
    private PreviewView previewView;
    private ThemedReactContext _context ;
    private FaceTracker mTracker;
    // textureView用于绘制人脸框等。
    private TextureView textureView;
    private Paint paint = new Paint();
    private int mRound = 2;
//    private DetectRegionProcessor cropProcessor = new DetectRegionProcessor();


    public BaiduFace(ThemedReactContext context) {
        super(context);
        this._context = context;
        context.addLifecycleEventListener(this);
        previewView = new com.baidu.aip.face.TexturePreviewView(this._context);
        textureView = new TextureView(this._context);

        RelativeLayout mCameraView = new RelativeLayout(this._context);
        mCameraView.setGravity(17);
        mCameraView.setBackgroundColor(255);

        mCameraView.layout(0,0,0,0);
        mCameraView.addView((View) previewView);
        mCameraView.addView((View) textureView);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);

        this.addView(mCameraView);


        faceDetectManager = new FaceDetectManager(context.getApplicationContext());
        final CameraImageSource cameraImageSource = new CameraImageSource(context.getApplicationContext());
        cameraImageSource.setPreviewView(previewView);
        cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_FACING_FRONT);

        faceDetectManager.setImageSource(cameraImageSource);
        ICameraControl control = cameraImageSource.getCameraControl();
        control.setPreviewView(previewView);

//        faceDetectManager.addPreProcessor(cropProcessor);
        previewView.setScaleType(PreviewView.ScaleType.FIT_HEIGHT);


        faceDetectManager.setOnFaceDetectListener(new FaceDetectManager.OnFaceDetectListener() {
            @Override
            public void onDetectFace(final int retCode, FaceInfo[] infos, ImageFrame frame) {
                if(isShowFrame){
                    if (infos != null && infos[0] != null) {
                        showFrame(infos[0]);
                    } else {
                        showFrame(null);
                    }
                }


                if(infos == null || infos.length<=0){
                    return ;
                }
                WritableMap event = Arguments.createMap();
                event.putInt("retCode", retCode);
                WritableArray m_infos = Arguments.createArray();


                event.putInt("mWidth",infos[0].mWidth);
                event.putInt("mAngle",infos[0].mAngle);
                event.putInt("mCenter_x",infos[0].mCenter_x);
                event.putInt("mCenter_y",infos[0].mCenter_y);


                ReactContext reactContext = (ReactContext)getContext();
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        getId(),
                        BaiduFaceManager.Events.EVENT_ON_FACE_DETECT.toString(),
                        event);
            }
        });
        faceDetectManager.setOnTrackListener(new FaceFilter.OnTrackListener() {
            @Override
            public void onTrack(final FaceFilter.TrackedModel trackedModel) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });

            }
        });
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
    }

    public void start(){
        faceDetectManager.start();
    }
    public void stop(){
        faceDetectManager.stop();
    }



    public void setIsFineAlign(Boolean bool){
        mTracker.set_isFineAlign(bool);
    }
    public void setIsVerifyLive(Boolean bool){
        mTracker.set_isVerifyLive(bool);
    }
    public void setDetectMethodType(int i){
        mTracker.set_DetectMethodType(i);
    }
    public void setIsCheckQuality(Boolean bool){
        mTracker.set_isCheckQuality(bool);
    }
    public void setNotFaceThr(Float value){
        mTracker.set_notFace_thr(value);
    }
    public void setMinFaceSize(int value){
        mTracker.set_min_face_size(value);
    }
    public void setCropFaceSize(int value){
        mTracker.set_cropFaceSize(value);
    }
    public void setIllumThr(Float value){
        mTracker.set_illum_thr(value);
    }
    public void setBlurThr(Float value){
        mTracker.set_blur_thr(value);
    }
    public void setOccluThr(Float value){
        mTracker.set_occlu_thr(value);
    }
    public void setMaxRegImgNum(int value){
        mTracker.set_max_reg_img_num(value);
    }
    public void setEulurAngleThr(int yaw, int roll, int pitch){
        mTracker.set_eulur_angle_thr(yaw,roll,pitch);
    }

    @Override
    public void onHostResume() {
        if (hasCameraPermissions()) {

            //防止其他Activity resume触发刷新
//            Activity activity  = mReactContext.getCurrentActivity();
//            if (activity != null && activity == _context){
                Log.d("xm", "onHostResume: ");
                start();
//            }

        } else {
            Log.e("xm", "没有摄像机权限" );
        }

    }

    @Override
    public void onHostPause() {
//        stop();
    }

    @Override
    public void onHostDestroy() {
        //友情提示：rn的ReactContext传给了不同activity，如果不作处理，
        //其他RN Activity onDestroy时候会使得监听注销，页面无法刷新.
//        Activity activity  = _context.getCurrentActivity();
//        if (activity != null && activity == _context){
            stop();
            _context.removeLifecycleEventListener(this);
//        }

    }
    private boolean hasCameraPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
            return result == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }
    private void showFrame(FaceInfo info) {
        Canvas canvas = textureView.lockCanvas();
        if (canvas == null) {
            return;
        }

        // 清空canvas
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (info != null) {
            Log.d("liujinhui", " has frame");
            RectF rectCenter = new RectF(info.mCenter_x - 2 - info.mWidth * 3 / 5,
                    info.mCenter_y - 2 - info.mWidth * 3 / 5,
                    info.mCenter_x + 2 + info.mWidth * 3 / 5,
                    info.mCenter_y + 2 + info.mWidth * 3 / 5);
            previewView.mapFromOriginalRectEx(rectCenter);
            // 绘制框
            paint.setStrokeWidth(mRound);
            paint.setAntiAlias(true);
            canvas.drawRect(rectCenter, paint);
            if (info.landmarks.length > 0) {
                int len = info.landmarks.length;
                for (int i = 0; i < len; i += 2) {
                    RectF rectPoint = new RectF(info.landmarks[i] - mRound, info.landmarks[i + 1] - mRound,
                            info.landmarks[i] + mRound, info.landmarks[i + 1] + mRound);
                    previewView.mapFromOriginalRectEx(rectPoint);

                    paint.setStrokeWidth(rectPoint.width() * 2 / 3);
                    canvas.drawCircle(rectPoint.centerX(), rectPoint.centerY(), rectPoint.width() / 2, paint);

                }
            }
        } else {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }
        textureView.unlockCanvasAndPost(canvas);
    }
}
