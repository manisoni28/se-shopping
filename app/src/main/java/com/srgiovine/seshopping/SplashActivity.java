package com.srgiovine.seshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.srgiovine.seshopping.account.dialog.ForgotPasswordFormDialog;
import com.srgiovine.seshopping.account.dialog.LoginFormDialog;
import com.srgiovine.seshopping.account.dialog.SignupFormDialog;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import srgiovine.com.seshopping.R;

public class SplashActivity extends SEActivity {

    private View actionContainer;

    private SignupFormDialog signupFormDialog;
    private LoginFormDialog loginFormDialog;
    private ForgotPasswordFormDialog forgotPasswordFormDialog;

    private final Callback<Void> signupDialogCallback = new SimpleCallback<Void>() {
        @Override
        public void onSuccess(Void v) {
            startActivity(new Intent(SplashActivity.this, BrowseActivity.class));
            finish();
        }

        @Override
        public void onCancelled() {
            setActionbarContainerVisible(true);
        }
    };

    private final Callback<Void> loginFormDialogCallback = new SimpleCallback<Void>() {
        @Override
        public void onSuccess(Void v) {
            startActivity(new Intent(SplashActivity.this, BrowseActivity.class));
            finish();
        }

        @Override
        public void onCancelled() {
            setActionbarContainerVisible(true);
        }
    };

    private final Callback<Void> forgotPasswordFormDialogCallback = new SimpleCallback<Void>() {
        @Override
        public void onSuccess(Void v) {
            setActionbarContainerVisible(true);
        }

        @Override
        public void onCancelled() {
            setActionbarContainerVisible(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (accountManager().loggedInUserExist()) {
            startActivity(new Intent(SplashActivity.this, BrowseActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_splash);

        actionContainer = findViewById(R.id.form_container);

        signupFormDialog = new SignupFormDialog(this, accountManager(), signupDialogCallback);
        loginFormDialog = new LoginFormDialog(this, accountManager(), loginFormDialogCallback);
        forgotPasswordFormDialog = new ForgotPasswordFormDialog(this, accountManager(), forgotPasswordFormDialogCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (signupFormDialog != null) {
            signupFormDialog.destroy();
        }
        if (loginFormDialog != null) {
            loginFormDialog.destroy();
        }
        if (forgotPasswordFormDialog != null) {
            forgotPasswordFormDialog.destroy();
        }
    }

    public void onSignupClicked(View view) {
        setActionbarContainerVisible(false);
        signupFormDialog.show();
    }

    public void onLoginClicked(View view) {
        setActionbarContainerVisible(false);
        loginFormDialog.show();

    }

    public void onForgotPasswordClicked(View view) {
        setActionbarContainerVisible(false);
        forgotPasswordFormDialog.show();
    }

    private void setActionbarContainerVisible(boolean visible) {
        actionContainer.animate().cancel();
        actionContainer.animate().scaleY(visible ? 1f : 0f).alpha(visible ? 1f : 0f).start();
    }

    @Override
    boolean homeAsUpButtonEnabled() {
        return false;
    }
}
