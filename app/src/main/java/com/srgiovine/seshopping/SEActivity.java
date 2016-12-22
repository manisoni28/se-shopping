package com.srgiovine.seshopping;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.srgiovine.seshopping.account.AccountManager;
import com.srgiovine.seshopping.cart.CartManager;
import com.srgiovine.seshopping.data.ItemRepository;

public abstract class SEActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeAsUpButtonEnabled());
            actionBar.setHomeButtonEnabled(homeAsUpButtonEnabled());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected AccountManager accountManager() {
        return seApplication().accountManager();
    }

    protected ItemRepository itemRepository() {
        return seApplication().itemRepository();
    }

    protected CartManager cartManager() {
        return seApplication().cartManager();
    }

    protected boolean homeAsUpButtonEnabled() {
        return true;
    }

    private SEApplication seApplication() {
        return (SEApplication) getApplication();
    }
}
