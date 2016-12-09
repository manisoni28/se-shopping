package com.srgiovine.seshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.srgiovine.seshopping.dialog.ForgotPasswordFormDialog;
import com.srgiovine.seshopping.dialog.FormDialog;
import com.srgiovine.seshopping.dialog.LoginFormDialog;
import com.srgiovine.seshopping.dialog.SignupFormDialog;

import srgiovine.com.seshopping.R;

public class SplashActivity extends Activity {

    private View contentView;
    private View actionContainer;

    private SignupFormDialog signupFormDialog;
    private LoginFormDialog loginFormDialog;
    private ForgotPasswordFormDialog forgotPasswordFormDialog;

    private final FormDialog.Callback signupDialogCallback = new FormDialog.Callback() {
        @Override
        public void onSuccess() {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }

        @Override
        public void onCancelled() {
            setActionbarContainerVisible(true);
        }
    };

    private final FormDialog.Callback loginFormDialogCallback = new FormDialog.Callback() {
        @Override
        public void onSuccess() {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }

        @Override
        public void onCancelled() {
            setActionbarContainerVisible(true);
        }
    };

    private final FormDialog.Callback forgotPasswordFormDialogCallback = new FormDialog.Callback() {
        @Override
        public void onSuccess() {
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
        contentView = getLayoutInflater().inflate(R.layout.activity_splash, null);
        setContentView(contentView);

        actionContainer = findViewById(R.id.form_container);

        signupFormDialog = new SignupFormDialog(this, signupDialogCallback);
        loginFormDialog = new LoginFormDialog(this, loginFormDialogCallback);
        forgotPasswordFormDialog = new ForgotPasswordFormDialog(this, forgotPasswordFormDialogCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signupFormDialog.destroy();
        loginFormDialog.destroy();
        forgotPasswordFormDialog.destroy();
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

}