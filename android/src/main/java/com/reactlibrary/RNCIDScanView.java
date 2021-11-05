package com.reactlibrary;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import org.json.JSONObject;

import app.captureid.captureidlibrary.BoundedLayout;
import app.captureid.captureidlibrary.CaptureID;
import app.captureid.captureidlibrary.SimpleScanner;
import app.captureid.captureidlibrary.result.ResultListener;
import app.captureid.captureidlibrary.result.ResultObject;

public class RNCIDScanView extends ViewGroupManager<ConstraintLayout> {
    private static final String REACT_CLASS = "RNCIDScanView";

    private CaptureID _captureID;
    private SimpleScanner _scanner;

    public RNCIDScanView() {
        super();
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected ConstraintLayout createViewInstance(@NonNull ThemedReactContext reactContext) {
        ConstraintLayout layout = new ConstraintLayout(reactContext);
        layout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT));
        _scanner = SimpleScanner.getSharedObject(reactContext);
        Point displaySize = new Point(0,0);
        reactContext.getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
        _scanner.setDisplaySize(displaySize);
        _scanner.startScanner(layout);
        return layout;
    }

    @Override
    public void onDropViewInstance(@NonNull ConstraintLayout view) {
        for(int i = 0; i < view.getChildCount(); i++) {
            if(view.getChildAt(i) instanceof BoundedLayout) {
                ((BoundedLayout) view.getChildAt(i)).removeAllViews();
            }
        }
        view.removeAllViews();
        _scanner.closeSharedObject();
        super.onDropViewInstance(view);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


    protected void setConfiguration(JSONObject config) {
        if(_scanner != null) {
            _scanner.setConfiguration(config);
        }
    }

//    @ReactProp(name="config")
//    public void setConfig(CIDScanView scanView, JSONObject config) {
//        scanView.setConfiguration(config);
//    }
}
