package com.reactlibrary;

import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.util.Size;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import app.captureid.captureidlibrary.BoundedLayout;
import app.captureid.captureidlibrary.CaptureID;
import app.captureid.captureidlibrary.SimpleScanner;
import app.captureid.captureidlibrary.listeners.SimpleScannerEventListener;

public class RNCIDScanView extends ViewGroupManager<CIDScanPreview> {
    private static final String REACT_CLASS = "RNCIDScanView";

    private CaptureID _captureID;
    private ReactContext _reactcontext;
    private ReactApplicationContext _context;
    ConstraintLayout _layout;

    public RNCIDScanView(ReactApplicationContext context) {
        super();
        _context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected CIDScanPreview createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new CIDScanPreview(reactContext);
    }

    @Override
    public void onDropViewInstance(@NonNull CIDScanPreview view) {
        view.dropView();
        super.onDropViewInstance(view);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


//    protected void setConfiguration(JSONObject config) {
//        if(_scanner != null) {
//            _scanner.setConfiguration(config);
//        }
//    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of("onPreviewReady", (Object)MapBuilder.of(
                        "registrationName", "onPreviewReady"));
    }

    private Object getContext() {
        return _context;
    }
    private Object getReactContext() {
        return _reactcontext;
    }

//    @ReactProp("onPreviewReady", )

//    @ReactProp(name="config")
//    public void setConfig(CIDScanView scanView, JSONObject config) {
//        scanView.setConfiguration(config);
//    }
}
