package com.srgiovine.seshopping.dialog;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import srgiovine.com.seshopping.R;

public class LoginFormDialog extends FormDialog {

    private EditText email;
    private EditText password;

    public LoginFormDialog(Context context, Callback callback) {
        super(context, callback);

        email = (EditText) contentView.findViewById(R.id.email);
        password = (EditText) contentView.findViewById(R.id.password);
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
        Toast.makeText(contentView.getContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onConfirmActionFailed() {
        super.onConfirmActionFailed();
        Snackbar.make(contentView, "Failed to log in", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected boolean onValidateFormFieldInputs() {
        boolean formFieldsValid = super.onValidateFormFieldInputs();
        formFieldsValid &= onValidateEmailField(email);
        formFieldsValid &= onValidatePasswordField(password);
        return formFieldsValid;
    }

    @Override
    protected List<EditText> formFields() {
        return Arrays.asList(email, password);
    }

    @Override
    protected int layoutRes() {
        return R.layout.dialog_login;
    }
}
