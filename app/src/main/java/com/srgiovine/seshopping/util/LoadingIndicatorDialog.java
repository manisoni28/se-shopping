package com.srgiovine.seshopping.util;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingIndicatorDialog {

    private ProgressDialog loadingIndicator;

    public void show(Context context, String message) {
        loadingIndicator = new ProgressDialog(context);
        loadingIndicator.setMessage(message);
        loadingIndicator.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingIndicator.setIndeterminate(true);
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();
    }

    public void dismiss() {
        if (loadingIndicator != null) {
            loadingIndicator.dismiss();
            loadingIndicator = null;
        }
    }
}
