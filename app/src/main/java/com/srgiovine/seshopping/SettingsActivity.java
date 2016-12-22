package com.srgiovine.seshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;
import com.srgiovine.seshopping.util.SettingsViewPresenter;

import srgiovine.com.seshopping.R;

public class SettingsActivity extends SEActivity implements SettingsViewPresenter.EventListener {

    private SettingsViewPresenter viewPresenter;

    private BackgroundTask getLoggedInUserTask;
    private BackgroundTask updateUserTask;

    private final Callback<User> getLoggedInUserCallback = new SimpleCallback<User>() {
        @Override
        public void onSuccess(User result) {
            viewPresenter.showUserInfo(result);
            viewPresenter.dismissLoadingIndicator();
        }

        @Override
        public void onFailed() {
            viewPresenter.dismissLoadingIndicator();
            Toast.makeText(SettingsActivity.this, "Unable to retrieve your info",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private final Callback<User> updateUserCallback = new SimpleCallback<User>() {
        @Override
        public void onSuccess(User result) {
            viewPresenter.showUserInfo(result);
            viewPresenter.dismissLoadingIndicator();
        }

        @Override
        public void onFailed() {
            viewPresenter.dismissLoadingIndicator();
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
        viewPresenter.showLoadingIndicator(this, "Retrieving your info...");
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
        viewPresenter.dismissLoadingIndicator();
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
            viewPresenter.showLoadingIndicator(this, "Saving your info...");
            updateUserTask = accountManager().updateUser(viewPresenter.buildUser(), updateUserCallback);
        }
    }

    @Override
    public void onLogoutClicked() {
        accountManager().logout();

        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
    }
}
