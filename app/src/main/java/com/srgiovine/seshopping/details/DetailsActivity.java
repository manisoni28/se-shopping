package com.srgiovine.seshopping.details;

import android.os.Bundle;
import android.view.View;

import com.srgiovine.seshopping.SEActivity;

import srgiovine.com.seshopping.R;

public class DetailsActivity extends SEActivity implements DetailsViewPresenter.EventListener {

    public static final String EXTRA_ITEM_ID = "item_id";

    private DetailsViewPresenter viewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_details, null);
        setContentView(contentView);

        viewPresenter = new DetailsViewPresenter(contentView, this);
    }

    @Override
    public void onAddToCartClicked(int amount) {

    }

    private long itemId() {
        return getIntent().getLongExtra(EXTRA_ITEM_ID, 0L);
    }

}
