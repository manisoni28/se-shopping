package com.srgiovine.seshopping.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

import srgiovine.com.seshopping.R;

public abstract class FormDialog {

    protected View contentView;
    private Dialog dialog;
    private View formContainer;
    private ProgressBar progressBar;
    private Callback callback;
    private boolean isShowing;

    private final Runnable onDismissRunnable = new Runnable() {
        @Override
        public void run() {
            dialog.dismiss();
        }
    };

    private final View.OnClickListener cancelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FormDialog.this.dismiss(true);
        }
    };

    private final View.OnClickListener confirmOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onConfirmButtonPressed();
        }
    };

    public FormDialog(Context context, final Callback callback) {
        this.callback = callback;
        contentView = LayoutInflater.from(context).inflate(layoutRes(), null);
        dialog = new Dialog(context, R.style.AppTheme_Dialog) {
            @Override
            public void onBackPressed() {
                FormDialog.this.dismiss(true);
            }
        };
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        formContainer = contentView.findViewById(R.id.form_container);
        progressBar = (ProgressBar) formContainer.findViewById(R.id.progress);
        formContainer.findViewById(R.id.cancel).setOnClickListener(cancelOnClickListener);
        formContainer.findViewById(R.id.confirm).setOnClickListener(confirmOnClickListener);
    }

    public void show() {
        if (isShowing) {
            return;
        }

        setDialogVisible(true, null);
    }

    public void destroy() {
        dismiss(false);
    }

    @CallSuper
    protected boolean onValidateFormFieldInputs() {
        boolean formFieldsValid = true;

        for (EditText formField : formFields()) {
            String value = formField.getText().toString();
            if (TextUtils.isEmpty(value)) {
                formFieldsValid = false;
                formField.setError("This field is required");
            } else {
                formField.setError(null);
            }
        }

        return formFieldsValid;
    }

    @CallSuper
    protected void onConfirmActionSuccess() {
        dismiss(false);
        callback.onSuccess();
    }

    @CallSuper
    protected void onConfirmActionFailed() {
        progressBar.setVisibility(View.GONE);
    }

    @CallSuper
    protected void onTakeConfirmAction() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected boolean onValidateEmailField(EditText email) {
        String emailStr = email.getText().toString();
        if (!emailStr.contains("@")) {
            email.setError("Invalid email");
            return false;
        }
        return true;
    }

    protected boolean onValidatePasswordField(EditText password) {
        String passwordStr = password.getText().toString();
        if (passwordStr.length() < 6) {
            password.setError("Must be at least 6 characters long");
            return false;
        }
        return true;
    }

    private void onConfirmButtonPressed() {
        if (onValidateFormFieldInputs()) {
            onTakeConfirmAction();
        }
    }

    private void clearFormFields() {
        for (EditText formField : formFields()) {
            formField.setText(null);
            formField.setError(null);
        }
    }

    private boolean dismiss(boolean invokeCallback) {
        if (!isShowing) {
            return false;
        }

        progressBar.setVisibility(View.GONE);
        setDialogVisible(false, onDismissRunnable);
        clearFormFields();

        if (invokeCallback) {
            callback.onCancelled();
        }

        return true;
    }

    private void setDialogVisible(boolean visible, Runnable endAction) {
        isShowing = visible;

        if (visible) {
            dialog.show();
        }

        float visiblity = visible ? 1F : 0F;
        formContainer.animate().cancel();
        formContainer.animate().scaleY(visiblity).alpha(visiblity).start();
        contentView.animate().cancel();
        contentView.animate().scaleY(visiblity).alpha(visiblity).withEndAction(endAction).start();
    }

    protected abstract List<EditText> formFields();

    @LayoutRes
    protected abstract int layoutRes();

    public interface Callback {
        void onSuccess();

        void onCancelled();
    }
}
