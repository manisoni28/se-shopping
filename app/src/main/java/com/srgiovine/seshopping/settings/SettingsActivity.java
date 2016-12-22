package com.srgiovine.seshopping.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.srgiovine.seshopping.SEActivity;
import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.splash.SplashActivity;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import srgiovine.com.seshopping.R;

public class SettingsActivity extends SEActivity implements SettingsViewPresenter.EventListener {

    private SettingsViewPresenter viewPresenter;

    private BackgroundTask getLoggedInUserTask;
    private BackgroundTask updateUserTask;

    private final Callback<User> getLoggedInUserCallback = new SimpleCallback<User>() {
        @Override
        public void onSuccess(User result) {
            viewPresenter.displayUserInfo(result);
        }

        @Override
        public void onFailed() {
            Toast.makeText(SettingsActivity.this, "Unable to retrieve your info",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private final Callback<User> updateUserCallback = new SimpleCallback<User>() {
        @Override
        public void onSuccess(User result) {
            viewPresenter.displayUserInfo(result);
        }

        @Override
        public void onFailed() {
            Toast.makeText(SettingsActivity.this, "Unable to update your info",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_settings, null);
        setContentView(contentView);

        viewPresenter = new SettingsViewPresenter(contentView, this);
        getLoggedInUserTask = accountManager().getLoggedInUser(getLoggedInUserCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getLoggedInUserTask != null) {
            getLoggedInUserTask.cancel();
            getLoggedInUserTask = null;
        }
        if (updateUserTask != null) {
            updateUserTask.cancel();
            updateUserTask = null;
        }
    }

    @Override
    public void onEditClicked() {
        viewPresenter.setEditMode(true);
    }

    @Override
    public void onSaveClicked() {
        boolean addressIsValid = viewPresenter.validateAddress();
        boolean creditCardInfoIsValid = viewPresenter.validateCreditCardInfo();
        if (addressIsValid && creditCardInfoIsValid) {
            viewPresenter.setEditMode(false);
            updateUserTask = accountManager().updateUser(viewPresenter.buildUser(), updateUserCallback);
        }
    }

    @Override
    public void onLogoutClicked() {
        accountManager().logout();
        cartManager().clearCart();

        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
    }
}
