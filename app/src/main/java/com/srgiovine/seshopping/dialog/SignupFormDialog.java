package com.srgiovine.seshopping.dialog;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import srgiovine.com.seshopping.R;

public class SignupFormDialog extends FormDialog {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;

    public SignupFormDialog(Context context, Callback callback) {
        super(context, callback);

        firstName = (EditText) contentView.findViewById(R.id.first_name);
        lastName = (EditText) contentView.findViewById(R.id.last_name);
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
        Toast.makeText(contentView.getContext(), "Successfully signed up", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onConfirmActionFailed() {
        super.onConfirmActionFailed();
        Snackbar.make(contentView, "Failed to sign up", Snackbar.LENGTH_SHORT).show();
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
        return Arrays.asList(firstName, lastName, email, password);
    }

    @Override
    protected int layoutRes() {
        return R.layout.dialog_signup;
    }
}
