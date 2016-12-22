package com.srgiovine.seshopping.splash;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.Toast;

import com.srgiovine.seshopping.account.AccountManager;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import java.util.Collections;
import java.util.List;

import srgiovine.com.seshopping.R;

class ForgotPasswordFormDialog extends FormDialog {

    private EditText email;

    private BackgroundTask forgotPasswordTask;

    private final Callback<Void> forgotPasswordCallback = new SimpleCallback<Void>() {
        @Override
        public void onSuccess(Void v) {
            ForgotPasswordFormDialog.this.onConfirmActionSuccess();
        }

        @Override
        public void onFailed() {
            ForgotPasswordFormDialog.this.onConfirmActionFailed();
        }
    };

    ForgotPasswordFormDialog(Context context, AccountManager accountManager, Callback<Void> callback) {
        super(context, accountManager, callback);

        email = (EditText) contentView.findViewById(R.id.email);
    }

    @Override
    protected void onTakeConfirmAction() {
        super.onTakeConfirmAction();
        accountManager.requestPasswordRecovery(email.getText().toString(), forgotPasswordCallback);
    }

    @Override
    protected void onConfirmActionSuccess() {
        super.onConfirmActionSuccess();
        Toast.makeText(context, "Password recovery sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onConfirmActionFailed() {
        super.onConfirmActionFailed();
        Snackbar.make(contentView, "Failed to find email", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected boolean onValidateFormFieldInputs() {
        boolean formFieldsValid = super.onValidateFormFieldInputs();
        formFieldsValid &= onValidateEmailField(email);
        return formFieldsValid;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (forgotPasswordTask != null) {
            forgotPasswordTask.cancel();
            forgotPasswordTask = null;
        }
    }

    @Override
    protected List<EditText> formFields() {
        return Collections.singletonList(email);
    }

    @Override
    protected int layoutRes() {
        return R.layout.dialog_forgot_password;
    }
}
