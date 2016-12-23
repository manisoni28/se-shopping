package com.srgiovine.seshopping.details;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.srgiovine.seshopping.SEActivity;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import srgiovine.com.seshopping.R;

public class DetailsActivity extends SEActivity implements DetailsViewPresenter.EventListener {

    public static final String EXTRA_ITEM_ID = "item_id";

    private DetailsViewPresenter viewPresenter;

    private BackgroundTask getItemTask;

    private final Callback<Item> getItemCallback = new SimpleCallback<Item>() {
        @Override
        public void onSuccess(Item item) {
            viewPresenter.displayItemInfo(item, DetailsActivity.this.getActionBar());
        }

        @Override
        public void onFailed() {
            Toast.makeText(DetailsActivity.this, "Unable to retrieve item details", Toast.LENGTH_SHORT).show();
            DetailsActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_details, null);
        setContentView(contentView);

        viewPresenter = new DetailsViewPresenter(contentView, this);
        getItemTask = itemRepository().getItemById(itemId(), getItemCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getItemTask != null) {
            getItemTask.cancel();
        }
    }

    @Override
    public void onAddToCartClicked(int count) {
        cartManager().addItemToCart(itemId(), count);
        Toast.makeText(this, "Added item to cart", Toast.LENGTH_SHORT).show();
        finish();
    }

    private long itemId() {
        return getIntent().getLongExtra(EXTRA_ITEM_ID, 0L);
    }

}
