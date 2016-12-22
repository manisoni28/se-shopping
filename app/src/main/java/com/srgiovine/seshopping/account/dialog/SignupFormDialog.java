package com.srgiovine.seshopping.account.dialog;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.EditText;
import android.widget.Toast;

import com.srgiovine.seshopping.account.AccountManager;
import com.srgiovine.seshopping.model.Name;
import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import java.util.Arrays;
import java.util.List;

import srgiovine.com.seshopping.R;

public class SignupFormDialog extends FormDialog {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;

    private BackgroundTask signupTask;

    private final Callback<User> signupCallback = new SimpleCallback<User>() {
        @Override
        public void onSuccess(User user) {
            SignupFormDialog.this.onConfirmActionSuccess();
        }

        @Override
        public void onFailed() {
            SignupFormDialog.this.onConfirmActionFailed();
        }
    };

    public SignupFormDialog(Context context, AccountManager accountManager, Callback<Void> callback) {
        super(context, accountManager, callback);

        firstName = (EditText) contentView.findViewById(R.id.first_name);
        lastName = (EditText) contentView.findViewById(R.id.last_name);
        email = (EditText) contentView.findViewById(R.id.email);
        password = (EditText) contentView.findViewById(R.id.password);
    }

    @Override
    protected void onTakeConfirmAction() {
        super.onTakeConfirmAction();
        Name name = Name.builder()
                .setFirst(firstName.getText().toString())
                .setLast(lastName.getText().toString())
                .build();

        User user = User.builder()
                .setName(name)
                .setEmail(email.getText().toString())
                .setPassword(password.getText().toString())
                .build();

        signupTask = accountManager.signup(user, signupCallback);
    }

    @Override
    protected void onConfirmActionSuccess() {
        super.onConfirmActionSuccess();
        Toast.makeText(context, "Signup success", Toast.LENGTH_SHORT).show();
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
    public void destroy() {
        super.destroy();
        if (signupTask != null) {
            signupTask.cancel();
            signupTask = null;
        }
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
