package com.reactlibrary;

import android.view.View;
import androidx.annotation.NonNull;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class CIDScanView extends SimpleViewManager<View> {
    private static final String REACT_CLASS = "CIDScanView";

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected View createViewInstance(@NonNull ThemedReactContext reactContext) {
        View view = new View(reactContext);
        return view;
    }
}
