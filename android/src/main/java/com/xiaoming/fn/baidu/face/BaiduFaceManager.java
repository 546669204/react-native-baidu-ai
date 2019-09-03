package com.xiaoming.fn.baidu.face;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

public class BaiduFaceManager extends SimpleViewManager<BaiduFace> {
    public static final String REACT_CLASS = "RNBaiduFace";
    public enum Events {
        EVENT_ON_TRAXK("onTrack"),
        EVENT_ON_FACE_DETECT("onFaceDetect");

        private final String mName;

        Events(final String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }
    @Override
    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        for (Events event : Events.values()) {
            builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
        }
        return builder.build();
    }
    @Override
    public String getName() {
        return REACT_CLASS;
    }
    @Override
    public BaiduFace createViewInstance(ThemedReactContext context) {
        return new BaiduFace(context);
    }
    @Override
    public void onDropViewInstance(BaiduFace view) {
        view.stop();
        super.onDropViewInstance(view);
    }

    @ReactProp(name = "isFineAlign",defaultBoolean = false)
    public void setIsFineAlign(BaiduFace view, Boolean  value) {
        view.setIsFineAlign(value);
    }
    @ReactProp(name = "isVerifyLive",defaultBoolean = false)
    public void setIsVerifyLive(BaiduFace view, Boolean  value) {
        view.setIsVerifyLive(value);
    }
    @ReactProp(name = "detectMethodType",defaultInt = 1)
    public void setDetectMethodType(BaiduFace view, int  value) {
        view.setDetectMethodType(value);
    }
    @ReactProp(name = "isCheckQuality",defaultBoolean = true)
    public void setIsCheckQuality(BaiduFace view, Boolean  value) {
        view.setIsCheckQuality(value);
    }
    @ReactProp(name = "notFaceThr",defaultFloat = 0.6f)
    public void setNotFaceThr(BaiduFace view, float  value) {
        view.setNotFaceThr(value);
    }
    @ReactProp(name = "minFaceSize",defaultInt = 120)
    public void setMinFaceSize(BaiduFace view, int  value) {
        view.setMinFaceSize(value);
    }
    @ReactProp(name = "cropFaceSize",defaultInt = 400)
    public void setCropFaceSize(BaiduFace view, int  value) {
        view.setCropFaceSize(value);
    }
    @ReactProp(name = "illumThr",defaultInt = 40)
    public void setIllumThr(BaiduFace view, float  value) {
        view.setIllumThr(value);
    }
    @ReactProp(name = "blurThr",defaultFloat = 0.7f)
    public void setBlurThr(BaiduFace view, float  value) {
        view.setBlurThr(value);
    }
    @ReactProp(name = "occluThr",defaultFloat = 0.5f)
    public void setOccluThr(BaiduFace view, float  value) {
        view.setOccluThr(value);
    }
    @ReactProp(name = "maxRegImgNum",defaultInt = 1)
    public void setMaxRegImgNum(BaiduFace view, int  value) {
        view.setMaxRegImgNum(value);
    }
    @ReactProp(name = "eulurAngleThr")
    public void setEulurAngleThr(BaiduFace view, @Nullable ReadableArray value) {
        view.setEulurAngleThr(value.getInt(0),value.getInt(1),value.getInt(2));
    }
    @ReactProp(name = "isShowFrame",defaultBoolean = false)
    public void setIsShowFrame(BaiduFace view, Boolean  value) {
        view.isShowFrame = value;
    }
}
