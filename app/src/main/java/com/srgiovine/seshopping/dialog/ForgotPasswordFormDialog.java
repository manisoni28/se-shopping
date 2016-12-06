package com.srgiovine.seshopping.dialog;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import srgiovine.com.seshopping.R;

public class ForgotPasswordFormDialog extends FormDialog {

    private EditText email;

    public ForgotPasswordFormDialog(Context context, Callback callback) {
        super(context, callback);

        email = (EditText) contentView.findViewById(R.id.email);
    }

    @Override
    protected void onTakeConfirmAction() {
        super.onTakeConfirmAction();
        // TODO remove fake code
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onConfirmActionSuccess();
            }
        }, 2_000L);
    }

    @Override
    protected void onConfirmActionSuccess() {
        super.onConfirmActionSuccess();
        Toast.makeText(contentView.getContext(), "Successfully found email", Toast.LENGTH_SHORT).show();
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
    protected List<EditText> formFields() {
        return Collections.singletonList(email);
    }

    @Override
    protected int layoutRes() {
        return R.layout.dialog_forgot_password;
    }
}
