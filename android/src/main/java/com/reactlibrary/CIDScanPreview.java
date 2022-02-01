package com.reactlibrary;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import app.captureid.captureidlibrary.BoundedLayout;
import app.captureid.captureidlibrary.SimpleScanner;
import app.captureid.captureidlibrary.listeners.SimpleScannerEventListener;

public class CIDScanPreview extends ConstraintLayout {
    private SimpleScanner _scanner;
    private CIDScanPreview _preview;

    public CIDScanPreview(Context context) {
        super(context);
        initialize();
    }

    public void dropView() {
        for(int i = 0; i < getChildCount(); i++) {
            if(getChildAt(i) instanceof BoundedLayout) {
                ((BoundedLayout) getChildAt(i)).removeAllViews();
            }
        }
        removeAllViews();
        _scanner.closeSharedObject();
    }

    private SimpleScannerEventListener listener = new SimpleScannerEventListener() {
        @Override
        public void onPreviewReady(int id) {
            ReactContext reactContext = (ReactContext)getContext();
            reactContext
                    .getJSModule(RCTEventEmitter.class)
                    .receiveEvent(getId(), "topPreviewReady", null);
        }
    };

    private void initialize() {
        setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT));
        _scanner = SimpleScanner.getSharedObject(getContext());
        Point displaySize = new Point(0,0);
        ((ThemedReactContext)getContext()).getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
        _scanner.setDisplaySize(displaySize);
        _preview = this;
        _scanner.startScanner(_preview, listener);
    }
}
