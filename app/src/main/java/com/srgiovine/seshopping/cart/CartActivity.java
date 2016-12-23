package com.srgiovine.seshopping.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.srgiovine.seshopping.SEActivity;
import com.srgiovine.seshopping.details.DetailsActivity;
import com.srgiovine.seshopping.settings.SettingsActivity;
import com.srgiovine.seshopping.util.ItemsLoaderView;

import srgiovine.com.seshopping.R;

public class CartActivity extends SEActivity implements CartItemsAdapter.EventListener, CheckoutPresenter.EventListener {

    private CartItemsPresenter cartItemsPresenter;

    private CheckoutPresenter checkoutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_cart, null);
        setContentView(contentView);

        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(this);

        RecyclerView itemsView = (RecyclerView) findViewById(R.id.items);
        itemsView.setLayoutManager(new LinearLayoutManager(this));
        itemsView.setAdapter(cartItemsAdapter);

        cartItemsPresenter = new CartItemsPresenter(cartItemsAdapter, cartManager(),
                new ItemsLoaderView(contentView));
        checkoutPresenter = new CheckoutPresenter(contentView, cartManager(), this);

        cartItemsAdapter.initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartItemsPresenter.loadCartItems();
        checkoutPresenter.validateUserPaymentInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cartItemsPresenter.onPause();
        checkoutPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkoutPresenter.onDestroy();
    }

    @Override
    public void onPurchaseComplete() {
        Toast.makeText(this, "Successfully purchased items", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdateSettingsClicked() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void onTotalPriceChanged(int totalPrice) {
        checkoutPresenter.updateTotalPrice(totalPrice);
    }

    @Override
    public void onItemCountUpdated(CartItem item, int newCount) {
        cartManager().updateItemCount(item.item().id(), newCount);
    }

    @Override
    public void onItemRemoved(CartItem item) {
        cartManager().removeItemFromCart(item.item().id());
        cartItemsPresenter.onItemRemoved();
    }

    @Override
    public void onItemClicked(CartItem item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_ITEM_ID, item.item().id());
        startActivity(intent);
    }
}
