package com.srgiovine.seshopping.account.dialog;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.Toast;

import com.srgiovine.seshopping.account.AccountManager;
import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import java.util.Arrays;
import java.util.List;

import srgiovine.com.seshopping.R;

public class LoginFormDialog extends FormDialog {

    private EditText email;
    private EditText password;

    private BackgroundTask loginTask;

    private final Callback<User> loginCallback = new SimpleCallback<User>() {
        @Override
        public void onSuccess(User user) {
            LoginFormDialog.this.onConfirmActionSuccess();
        }

        @Override
        public void onFailed() {
            LoginFormDialog.this.onConfirmActionFailed();
        }
    };

    public LoginFormDialog(Context context, AccountManager accountManager, Callback<Void> callback) {
        super(context, accountManager, callback);

        email = (EditText) contentView.findViewById(R.id.email);
        password = (EditText) contentView.findViewById(R.id.password);
    }

    @Override
    protected void onTakeConfirmAction() {
        super.onTakeConfirmAction();
        loginTask = accountManager.login(email.getText().toString(), password.getText().toString(), loginCallback);
    }

    @Override
    protected void onConfirmActionSuccess() {
        super.onConfirmActionSuccess();
        Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show();
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
        return formFieldsValid;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (loginTask != null) {
            loginTask.cancel();
            loginTask = null;
        }
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
