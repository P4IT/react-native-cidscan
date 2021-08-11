package com.reactlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.codecorp.camera.CameraType;
import com.codecorp.camera.Focus;
import com.codecorp.camera.Resolution;
import com.codecorp.symbology.SymbologyType;
import com.codecorp.util.Size;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.util.ReactFindViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import app.captureid.captureidlibrary.BoundedLayout;
import app.captureid.captureidlibrary.CaptureID;
import app.captureid.captureidlibrary.result.ResultListener;
import app.captureid.captureidlibrary.result.ResultObject;


public class CIDScan extends ReactContextBaseJavaModule {
    private static final String TAG = CIDScan.class.getSimpleName();

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    private CaptureID _captureid;
    private FrameLayout fl_cameraHostView;
    private Callback _licenseCallback;
    private Callback _captureIDCallback;

    public CIDScan(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "CIDScan";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }

    /**
     * Return a [WritableNativeMap] from a [PluginResultObject - CIDScan]
     *
     * @param resultObject
     * @return
     */
    private WritableNativeMap getNativeArray(ResultObject resultObject) {
        WritableNativeMap res = new WritableNativeMap();
        try {
            WritableArray ar = jsonToReactArray(resultObject.toJSON());
            res.putArray("result", ar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static WritableMap jsonToReactMap(JSONObject jsonObject) throws JSONException {
        WritableMap writableMap = Arguments.createMap();
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof Float || value instanceof Double) {
                writableMap.putDouble(key, jsonObject.getDouble(key));
            } else if(value instanceof Boolean) {
                writableMap.putBoolean(key, jsonObject.getBoolean(key));
            } else if (value instanceof Number) {
                writableMap.putInt(key, jsonObject.getInt(key));
            } else if (value instanceof String) {
                writableMap.putString(key, jsonObject.getString(key));
            } else if (value instanceof JSONObject) {
                writableMap.putMap(key, jsonToReactMap(jsonObject.getJSONObject(key)));
            } else if (value instanceof JSONArray) {
                writableMap.putArray(key, jsonToReactArray(jsonObject.getJSONArray(key)));
            } else if (value instanceof SymbologyType) {
                writableMap.putString(key, jsonObject.getString(key));
            } else if (value == JSONObject.NULL) {
                writableMap.putNull(key);
            }
        }
        return writableMap;
    }

    public static WritableArray jsonToReactArray(JSONArray jsonArray) throws JSONException {
        WritableArray writableArray = Arguments.createArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof Float || value instanceof Double) {
                writableArray.pushDouble(jsonArray.getDouble(i));
            } else if (value instanceof Number) {
                writableArray.pushInt(jsonArray.getInt(i));
            } else if (value instanceof String) {
                writableArray.pushString(jsonArray.getString(i));
            } else if (value instanceof JSONObject) {
                writableArray.pushMap(jsonToReactMap((JSONObject)value));
            } else if (value instanceof JSONArray) {
                writableArray.pushArray(jsonToReactArray(jsonArray.getJSONArray(i)));
            } else if (value == JSONObject.NULL) {
                writableArray.pushNull();
            }
        }
        return writableArray;
    }

    /**
     * Send a result back to JavaScript.
     * @param reactContext
     * @param eventName
     * @param params
     */
    private void sendDecoderEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params){
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    private ResultListener _captureid_listener = new ResultListener() {
        @Override
        public void onResult(ResultObject resultObject) {
            if(resultObject.getFunctionName().equalsIgnoreCase("onActivationResult")) {
                _licenseCallback.invoke(null, getNativeArray(resultObject));
            } else {
                Log.d(TAG, "result");
            }
        }
    };
    /**
     * init the CIDScan
     */
    @ReactMethod
    public void initCaptureID(Callback captureIDCallback) {
        _captureIDCallback = captureIDCallback;
        Activity activity = getCurrentActivity();
        _captureid = CaptureID.getSharedLibrary(activity);
        _captureid.addListener(_captureid_listener);
        ResultObject resultObject = new ResultObject("initCaptureID", true);
        _captureIDCallback.invoke(null, getNativeArray(resultObject));
    }

    /**
     * Activate the normal License -> can also be used for the EDK - Key
     *
     * @param key      [String] TachyonIQ license key
     * @param licenseCallback [Callback]
     */
    @ReactMethod
    public void activateEDKLicense(String key, String customerID, Callback licenseCallback) {
        _licenseCallback = licenseCallback;
        _captureid.activateEDKLicense(key, customerID);
//        mCaptureID.activateLicense(key, new ResultHandler() {
//            @Override
//            public void sendResult(PluginResultObject pluginResultObject) {
//                //todo error callback
//                callback.invoke(null, getNativeArray(pluginResultObject));
//            }
//        });
    }

    /**
     * Change the sound 1-4
     *
     * @param value [String]
     */
    @ReactMethod
    public void changeBeepPlayerSound(String value) {
        _captureid.changeBeepPlayerSound(value);
    }

    /**
     * Stop the cameraPreview and stopDecoding
     */
    @ReactMethod
    public void closeCamera() {
        _captureid.getCameraScanner().getDecoder().closeCamera();
    }

    /**
     * Close the shared object. The app need to be restarted if you close the sharedObject.
     */
    @ReactMethod
    public void closeSharedLibrary() {
        _captureid.closeSharedLibrary();
    }

    /**
     * Get the current Size of the DecoderVideo
     *
     * @param success [Callback]
     */
    @ReactMethod
    public void currentSizeOfDecoderVideo(Callback success) {
        Size size = _captureid.getCameraScanner().getDecoder().currentSizeOfDecoderVideo();
        String value = size.toString();
        ResultObject result = new ResultObject("currentSizeOfDecoderVideo");
        result.setStringValue(value);
        success.invoke(null, this.getNativeArray(result));
    }

    /**
     * Set the decoder Timelimit
     *
     * @param value [Int]
     */
    @ReactMethod
    public void decoderTimeLimintInMilliseconds(int value) {
        _captureid.getCameraScanner().getDecoder().decoderTimeLimitInMilliseconds(value);
    }

    /**
     * Return the decoderVersion
     *
     * @param success [Callback]
     */
    @ReactMethod
    public void decoderVersion(Callback success) {
        String value = _captureid.getCameraScanner().getDecoder().decoderVersion();
        ResultObject res = new ResultObject("decoderVersion");
        res.setStringValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    /**
     * Return the DecoderVersionLevel
     *
     * @param success [Callback]
     */
    @ReactMethod
    public void decoderVersionLevel(Callback success) {
        String value = _captureid.getCameraScanner().getDecoder().decoderVersionLevel();
        ResultObject res = new ResultObject("decoderVersionLevel");
        res.setStringValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    /**
     * Function is not supported
     *
     * @param success [Callback]
     */
    @ReactMethod
    public void doDecode(Callback success) {
        ResultObject res = new ResultObject("doDecode");
        res.setStringValue("Not Supported");
        success.invoke("Not Supported", this.getNativeArray(res));
    }

    /**
     * Enable the beep, when the decoder detect a barocde.
     *
     * @param value [Boolean]
     */
    @ReactMethod
    public void enableBeepPlayer(boolean value) {
        _captureid.enableBeepPlayer(value);
    }

    @ReactMethod
    public void enableFixedExposureMode(boolean value) {
        _captureid.getCameraScanner().getDecoder().enableFixedExposureMode(value);
    }

    @ReactMethod
    public void enableScannedImageCapture(boolean value) {
        _captureid.getCameraScanner().getDecoder().enableScannedImageCapture(value);
    }

    @ReactMethod
    public void enableVibrateOnScan(boolean value) {
        _captureid.enableVibrateOnScan(value);
    }

    @ReactMethod
    public void ensureRegionOfInterest(boolean value) {
        _captureid.getCameraScanner().getDecoder().ensureRegionOfInterest(value);
    }

    @ReactMethod
    public void generateDeviceID(final Callback success) {
        _captureid.getCameraScanner().getDecoder().generateDeviceID();
    }

    @ReactMethod
    public void getCameraPreview() {
        View view = _captureid.getCameraScanner().getDecoder().getCameraPreview();
    }

    @ReactMethod
    public void getExposureTime(Callback success) {
        ResultObject res = new ResultObject("getExposureTime");
        long value = _captureid.getCameraScanner().getDecoder().getFixedExposureTime();
        res.setLongValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getFocusDistance(Callback success) {
        ResultObject res = new ResultObject("getFocusDistance");
        float[] value = _captureid.getCameraScanner().getDecoder().getFocusDistance();
        ArrayList list = new ArrayList<>();
        for (float f : value) {
            list.add(f);
        }
        res.setObjectValue(list);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getLicensedSymbologies(Callback success) {
        ResultObject res = new ResultObject("getLicensedSymbologies");
        HashSet<SymbologyType> list = _captureid.getCameraScanner().getDecoder().getLicensedSymbologies();
        ArrayList resultList = new ArrayList<>();
        for (Object o : list) {
            resultList.add(o);
        }
        res.setObjectValue(resultList);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getMaxZoom(Callback success) {
        ResultObject res = new ResultObject("getMaxZoom");
        float value = _captureid.getCameraScanner().getDecoder().getMaxZoom();
        res.setFloatValue(Float.parseFloat(String.valueOf(value)));
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getSDKVersion(Callback success) {
        ResultObject res = new ResultObject("getSDKVersion");
        String value = _captureid.getCameraScanner().getDecoder().getSdkVersion();
        res.setStringValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getSensitivityBoost(Callback success) {
        ResultObject res = new ResultObject("getSensitivityBoost");
        ArrayList value = _captureid.getCameraScanner().getDecoder().getSensitivityBoost();
        res.setObjectValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getSizeForROI(Callback success) {
        ResultObject res = new ResultObject("getSizeForROI");
        Size value = _captureid.getCameraScanner().getDecoder().getSizeForROI();
        res.setObjectValue(new ArrayList<>());
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getSupportedCameraTypes(Callback success) {
        ResultObject res = new ResultObject("getSupportedCameraTypes");
        CameraType[] value = _captureid.getCameraScanner().getDecoder().getSupportedCameraTypes();
        res.setObjectValue(new ArrayList<>());
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getSupportedFocusModes(Callback success) {
        ResultObject res = new ResultObject("getSupportedFocusModes");
        Focus[] value = _captureid.getCameraScanner().getDecoder().getSupportedFocusModes();
        res.setObjectValue(new ArrayList<>());
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getSupportedWhiteBalance(Callback success) {
        ResultObject res = new ResultObject("getSupportedWhiteBalance");
        String[] value = _captureid.getCameraScanner().getDecoder().getSupportedWhiteBalance();
        ArrayList resultList = new ArrayList<>();
        for (String s : value) {
            resultList.add(s);
        }
        res.setObjectValue(resultList);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void getZoomRatios(Callback success) {
        ResultObject res = new ResultObject("getZoomRatios");
        float[] value = _captureid.getCameraScanner().getDecoder().getZoomRatios();
        ArrayList resultList = new ArrayList<>();
        for (float f : value) {
            resultList.add(f);
        }
        res.setObjectValue(resultList);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void hasTorch(Callback success) {
        ResultObject res = new ResultObject("hasTorch");
        boolean value = _captureid.getCameraScanner().getDecoder().hasTorch();
        res.setBoolValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void isLicenseActivated(Callback success) {
        ResultObject res = new ResultObject("isLicenseActivated");
        boolean value = _captureid.getCameraScanner().getDecoder().isLicenseActivated();
        res.setBoolValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void isLicenseExpired(Callback success) {
        ResultObject res = new ResultObject("isLicenseExpired");
        boolean value = _captureid.getCameraScanner().getDecoder().isLicenseExpired();
        res.setBoolValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void isZoomSupported(Callback success) {
        ResultObject res = new ResultObject("isZoomSupported");
        boolean value = _captureid.getCameraScanner().getDecoder().isZoomSupported();
        res.setBoolValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void libraryVersion(Callback success) {
        ResultObject res = new ResultObject("libraryVersion");
        String value = _captureid.getCameraScanner().getDecoder().libraryVersion();
        res.setStringValue(value);
        success.invoke(null, this.getNativeArray(res));
    }

    @ReactMethod
    public void lowContrastDecodingEnabled(Boolean value) {
        _captureid.getCameraScanner().getDecoder().lowContrastDecodingEnabled(value);
    }

    @ReactMethod
    public void playBeepSound() {
        _captureid.getCameraScanner().getDecoder().playBeepSound();
    }

    @ReactMethod
    public void regionOfInterestHeight(int value) {
        _captureid.getCameraScanner().getDecoder().regionOfInterestHeight(value);
    }

    @ReactMethod
    public void regionOfInterestLeft(int value) {
        _captureid.getCameraScanner().getDecoder().regionOfInterestLeft(value);
    }

    @ReactMethod
    public void regionOfInterestTop(int value) {
        _captureid.getCameraScanner().getDecoder().regionOfInterestTop(value);
    }

    @ReactMethod
    public void regionOfInterestWidth(int value) {
        _captureid.getCameraScanner().getDecoder().regionOfInterestWidth(value);
    }

    @ReactMethod
    public void setAutoFocusResetByCount(boolean value) {
        _captureid.getCameraScanner().getDecoder().setAutoFocusResetByCount(value);
    }

    @ReactMethod
    public void setCameraType(String value) {
        _captureid.getCameraScanner().getDecoder().setCameraType(CameraType.valueOf(value));
    }

    @ReactMethod
    public void setCameraZoom(boolean enable, float zoom) {
        _captureid.getCameraScanner().getDecoder().setCameraZoom(enable, zoom);
    }

    @ReactMethod
    public void setDecoderResolution(String res) {
        _captureid.getCameraScanner().getDecoder().setDecoderResolution(Resolution.valueOf(res));
    }

    @ReactMethod
    public void setDecoderToleranceLevel(int value) {
        _captureid.getCameraScanner().setDecoderToleranceLevel(value);
    }

    @ReactMethod
    public void setEncodingCharsetName(String charsetName) {
        _captureid.getCameraScanner().getDecoder().setEncodingCharsetName(charsetName);
    }

    @ReactMethod
    public void setContinuousMode(boolean enable) {
        _captureid.enableContinuousScan(enable);
    }

    @ReactMethod
    public void setExactlyNBarcodes(Boolean value) {
        _captureid.getCameraScanner().getDecoder().setExactlyNBarcodes(value);
    }

    @ReactMethod
    public void setExposureSensitivity(String iso) {
        _captureid.getCameraScanner().getDecoder().setExposureSensitivity(iso);
    }

    @ReactMethod
    public void setFixedExposureTime(Long ep) {
        _captureid.getCameraScanner().getDecoder().setFixedExposureTime(ep);
    }

    @ReactMethod
    public void setFocus(String focus) {
        _captureid.getCameraScanner().getDecoder().setFocus(Focus.valueOf(focus));
    }

    @ReactMethod
    public void setFocusDistance(float distance) {
        _captureid.getCameraScanner().getDecoder().setFocusDistance(distance);
    }

    @ReactMethod
    public void setNumberOfBarcodesToDecode(int number) {
        _captureid.setNumberOfBarcodesToDecode(number);
    }

    @ReactMethod
    public void setTorch(Boolean enable) {
        _captureid.getCameraScanner().getDecoder().setTorch(enable);
    }

    @ReactMethod
    public void setWhiteBalance(Boolean enable, String balance) {
        _captureid.getCameraScanner().getDecoder().setWhiteBalance(enable, balance);
    }

    @ReactMethod
    public void startCameraPreviewWithOverlay(final String viewid, final Callback callback) {
        final View rootview = getCurrentActivity().getWindow().getDecorView().getRootView();
        getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    View requiredView = ReactFindViewUtil.findView(rootview, viewid);
                    final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    requiredView.setLayoutParams(params);
                    _captureid.getCameraScanner().startFullScreen((ViewGroup) requiredView, new ResultListener() {
                        @Override
                        public void onResult(ResultObject resultObject) {
                            if (resultObject.getFunctionName().equals("startCameraPreview")) {
                                callback.invoke(null, "Success");
                            } else {
                                WritableMap result = new WritableNativeMap();
                                try {
                                    result.putArray("result", jsonToReactArray(resultObject.toJSON()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // send the result back to javaScript. The function inside of javascript needs to be called : "decoderEvent"!
                                sendDecoderEvent(getReactApplicationContext(), "decoderEvent", result);
                            }
                        }
                    });
                } catch (Exception ex) {
                    callback.invoke(ex.getMessage(), null);
                }
            }
        });
    }

    /**
     * Show the camera and build the fl_cameraHostView
     */
    @SuppressLint("ResourceType")
    @ReactMethod
    public void startCameraPreview(final Callback callback) {
        final Activity activity = getCurrentActivity();
        fl_cameraHostView = new FrameLayout(activity);
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    activity.addContentView(fl_cameraHostView, params);
                    _captureid.getCameraScanner().startFullScreen(new ResultListener() {
                        @Override
                        public void onResult(ResultObject resultObject) {
                            if (resultObject.getFunctionName().equals("startCameraPreview")) {
                                callback.invoke(null, "Success");
                            } else {
                                WritableMap result = new WritableNativeMap();
                                try {
                                    result.putArray("result", jsonToReactArray(resultObject.toJSON()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // send the result back to javaScript. The function inside of javascript needs to be called : "decoderEvent"!
                                sendDecoderEvent(getReactApplicationContext(), "decoderEvent", result);
                            }
                        }
                    });
                } catch (Exception ex) {
                    callback.invoke(ex.getMessage(), null);
                }
            }
        });
    }

    /**
     * Show the camera and build the fl_cameraHostView
     */
    @ReactMethod
    public void startSplitOverlay(final int left, final int paddingtop, final int minheight, final int maxheight, final int tolerancelevel, final Callback callback) {
        final boolean[] dataAreSend = {false};
        final Activity activity = getCurrentActivity();
        fl_cameraHostView = new FrameLayout(activity);
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    activity.addContentView(fl_cameraHostView, params);
                    _captureid.getCameraScanner().startFullScreen(new ResultListener() {
                        @Override
                        public void onResult(ResultObject resultObject) {
                            if (!dataAreSend[0]) {
                                try {
                                    WritableMap result = new WritableNativeMap();
                                    result.putArray("result", jsonToReactArray(resultObject.toJSON()));
                                    // send the result back to javaScript. The function inside of javascript needs to be called : "decoderEvent"!
                                    sendDecoderEvent(getReactApplicationContext(), "decoderEvent", result);
//                                    dataAreSend[0] = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, left, paddingtop, minheight, maxheight, tolerancelevel);
                } catch (Exception ex) {
                    callback.invoke(ex.getMessage(), null);
                }
            }
        });
    }

    /**
     * Start the decoder and send the result back to the "decoderEvent" Event inside of the javascript
     */
    @ReactMethod
    public void startDecoder() {
        final boolean[] dataAreSend = {false};
        this._captureid.startDecoding(new ResultListener() {
            @Override
            public void onResult(ResultObject resultObject) {
                if (!dataAreSend[0]) {
                    try {
                        WritableMap result = new WritableNativeMap();
                        result.putArray("result", jsonToReactArray(resultObject.toJSON()));
                        // send the result back to javaScript. The function inside of javascript needs to be called : "decoderEvent"!
                        sendDecoderEvent(getReactApplicationContext(), "decoderEvent", result);
                        dataAreSend[0] = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Hide the camera and remove the [fl_cameraHostView]
     */
    @ReactMethod
    public void stopCameraPreview() {
        final View view = getCurrentActivity().getWindow().getDecorView();
        getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < ((ViewGroup)view).getChildCount(); i++) {
                        View child = ((ViewGroup)view).getChildAt(i);
                        if(child instanceof BoundedLayout) {
                            ((ViewGroup)view).removeView(child);
                            _captureid.getCameraScanner().stopCameraPreview();
                            return;
                        }
                    }
                    if(fl_cameraHostView != null) {
                        ((ViewGroup) fl_cameraHostView.getParent()).removeView(fl_cameraHostView);
                        _captureid.getCameraScanner().stopCameraPreview();
                    }
                } catch(Exception ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        });
    }

    /**
     * Hide the camera and remove the [fl_cameraHostView]
     */
    @ReactMethod
    public void stopDecoding() {
        _captureid.getCameraScanner().stopDecoding();
    }

    @ReactMethod
    public void stringFromSymbologyType(String type, Callback callback) {
        ResultObject res = new ResultObject("stringFromSymbologyType");
        String value = _captureid.getCameraScanner().getDecoder().stringFromSymbologyType(SymbologyType.valueOf(type));
        res.setStringValue(value);
        callback.invoke(null, this.getNativeArray(res));
    }

    private JSONObject addJSONObject(String key, Object value) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("key", key);
            obj.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private JSONArray symArray(String symbology, boolean enable) {
        JSONArray arr = new JSONArray();
        JSONObject obj = new JSONObject();
        try {
            obj.put("symbology", symbology);
            arr.put(obj);
            JSONArray arr1 = new JSONArray();
            arr1.put(addJSONObject("enabled", enable));
            arr.put(arr1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @ReactMethod
    public void setSymbologyProperties(String symbology, boolean enable) {
        JSONArray arr = symArray(symbology, enable);
        _captureid.setSymbologyProperties(arr);
    }


//    CameraTypeValueOf
//            CameraTypeValues
//    FocusValueOf
//            FocusValues
//    SymboogyTypeValueOf
//            SymbologyTypeValues

//    @ReactMethod
//    public void setCameraButtons(JSONArray buttons, final Callback buttonCallback) {
//        -.setCameraButtons(buttons, new ResultHandler() {
//            @Override
//            public void sendResult(ResultObject resultObject) {
//                buttonCallback.invoke(null, getNativeArray(resultObject));
//            }
//        });
//    }

    @ReactMethod
    public void setAimStyle(final int style, final int red, final int green, final int blue) {
        Activity activity = getCurrentActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _captureid.getCameraScanner().setAimStyle(style, red, green, blue);
            }
        });
    }

    @ReactMethod
    public void enableAugmentedReality(Boolean enable) {
        _captureid.enableAugmentedReality(enable);
    }

    @ReactMethod
    public void ar_showVisualizeBarcodes(Boolean enable) {
        _captureid.getCameraScanner().ar_showVisualizeBarcodes(enable);
    }

    /**
     * Enable or disable a symbology
     *
     * @param symbologyName [String]
     * @param enable [Boolean]
     */
    @ReactMethod
    public void enableABarcode(String symbologyName, Boolean enable) {
        setSymbologyProperties(symbologyName, enable);
    }

    /**
     * Enable or disable all symbologies
     *
     * @param enable [Boolean]
     */
    @ReactMethod
    public void enableAllBarcodes(Boolean enable) {
        this._captureid.enableAllDecoders(enable);
    }

//    /**
//     * Activate a EDK license, file is required
//     *
//     * @param filename   [String] Filename
//     * @param customerID [String] CustomerID
//     * @param callback   [Callback]
//     */
//    @ReactMethod
//    public void activateEDKLicense(String filename, String customerID, final Callback callback) {
//        _captureid.getCameraScanner().activateEDKLicense(filename, customerID);
//    }
}