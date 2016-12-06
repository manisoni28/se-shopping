package com.srgiovine.seshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ViewSwitcher;

import com.srgiovine.seshopping.dialog.ForgotPasswordFormDialog;
import com.srgiovine.seshopping.dialog.FormDialog;
import com.srgiovine.seshopping.dialog.LoginFormDialog;
import com.srgiovine.seshopping.dialog.SignupFormDialog;

import srgiovine.com.seshopping.R;

public class SplashActivity extends Activity {

    private View contentView;
    private View actionContainer;
    private ViewSwitcher backgroundSwitcher;

    private SignupFormDialog signupFormDialog;
    private LoginFormDialog loginFormDialog;
    private ForgotPasswordFormDialog forgotPasswordFormDialog;

    private final Handler handler = new Handler();

    private final Runnable backgroundSwitcherRunnable = new Runnable() {
        @Override
        public void run() {
            backgroundSwitcher.showNext();
            handler.postDelayed(this, 10_000L);
        }
    };

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
        backgroundSwitcher = (ViewSwitcher) findViewById(R.id.background_switcher);

        signupFormDialog = new SignupFormDialog(this, signupDialogCallback);
        loginFormDialog = new LoginFormDialog(this, loginFormDialogCallback);
        forgotPasswordFormDialog = new ForgotPasswordFormDialog(this, forgotPasswordFormDialogCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(backgroundSwitcherRunnable, 5_000L);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(backgroundSwitcherRunnable);
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
