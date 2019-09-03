
package com.xiaoming.fn.baidu.face;

import android.content.Intent;
import android.util.Log;

import com.baidu.aip.FaceSDKManager;
import com.baidu.aip.fl.APIService;
import com.baidu.aip.fl.MainActivity;
import com.baidu.aip.fl.exception.FaceError;
import com.baidu.aip.fl.model.AccessToken;
import com.baidu.aip.fl.utils.OnResultListener;
import com.baidu.idl.facesdk.FaceSDK;
import com.baidu.idl.facesdk.FaceTracker;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.Map;

import static com.xiaoming.fn.baidu.face.BaiduFace.faceDetectManager;

public class RNBaiduFaceModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  // 为了apiKey,secretKey为您调用百度人脸在线接口的，如注册，识别等。
  // 为了的安全，建议放在您的服务端，端把人脸传给服务器，在服务端端进行人脸注册、识别放在示例里面是为了您快速看到效果
  public static String apiKey = "";
  public static String secretKey = "";
  public static String licenseID = "";
  public static String licenseFileName = "";


  /**
   * groupId，标识一组用户（由数字、字母、下划线组成），长度限制128B，可以自行定义，只要注册和识别都是同一个组。
   * 详情见 http://ai.baidu.com/docs#/Face-API/top
   * <p>
   * 人脸识别 接口 https://aip.baidubce.com/rest/2.0/face/v2/identify
   * 人脸注册 接口 https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add
   */

  public static String groupID = "";


  public RNBaiduFaceModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
      return "RNBaiduFace";
  }

  @ReactMethod
  public void init(ReadableMap map , final Promise promise){
    apiKey = map.getString("apiKey");
    secretKey = map.getString("secretKey");
    licenseID = map.getString("licenseID");
    licenseFileName = map.getString("licenseFileName");
    groupID = map.getString("groupID");

    initLib(map);

    APIService.getInstance().init(reactContext);
    APIService.getInstance().setGroupId(groupID);

    // 用ak，sk获取token, 调用在线api，如：注册、识别等。为了ak、sk安全，建议放您的服务器，
    APIService.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
      @Override
      public void onResult(AccessToken result) {
        Log.i("wtf", "AccessToken->" + result.getAccessToken());
        FaceSDKManager.getInstance().getFaceTracker(reactContext).set_track_by_detection_interval(800);
        FaceSDK.setNumberOfThreads(4);

        promise.resolve(result.getAccessToken());

      }

      @Override
      public void onError(FaceError error) {
        promise.reject(error.getErrorMessage());
        Log.e("xx", "AccessTokenError:" + error);
        error.printStackTrace();
      }
    }, reactContext, apiKey, secretKey);
  }
  @ReactMethod
  public void openNativeVC() {
    Intent intent = new Intent();
    intent.setClass(reactContext, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    reactContext.startActivity(intent);
  }
  @ReactMethod
  public void start() {
    BaiduFace.faceDetectManager.start();
  }
  /**
   * 初始化SDK
   */
  private void initLib(ReadableMap map) {
    // 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
    // 应用上下文
    // 申请License取得的APPID
    // assets目录下License文件名
    FaceSDKManager.getInstance().init(reactContext, licenseID, licenseFileName);
//    setFaceConfig(map);

    FaceTracker tracker = FaceSDKManager.getInstance().getFaceTracker(reactContext);
    // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整

    ReadableMap face = map.getMap("faceConfig");
    // 模糊度范围 (0-1) 推荐小于0.7

    tracker.set_blur_thr((float) face.getDouble("blur"));
    // 光照范围 (0-1) 推荐大于40
    tracker.set_illum_thr((float) face.getDouble("illum"));
    // 裁剪人脸大小
    tracker.set_cropFaceSize(face.getInt("cropFaceSize"));
    // 人脸yaw,pitch,row 角度，范围（-45，45），推荐-15-15
    tracker.set_eulur_angle_thr(face.getInt("pitch"), face.getInt("roll"),
            face.getInt("yaw"));

    // 最小检测人脸（在图片人脸能够被检测到最小值）80-200， 越小越耗性能，推荐120-200
    tracker.set_min_face_size(face.getInt("minFaceSize"));
    //
    tracker.set_notFace_thr((float) face.getDouble("notFace"));
    // 人脸遮挡范围 （0-1） 推荐小于0.5
    tracker.set_occlu_thr((float) face.getDouble("occlu"));
    // 是否进行质量检测
    tracker.set_isCheckQuality(face.getBoolean("isCheckQuality"));
    // 是否进行活体校验
    tracker.set_isVerifyLive(face.getBoolean("isVerifyLive"));

    // 是否播放声音


  }

}