package com.srgiovine.seshopping.details;

import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.srgiovine.seshopping.model.Item;

import srgiovine.com.seshopping.R;

class DetailsViewPresenter {

    private final EventListener eventListener;

    private final ImageView image;
    private final TextView description;
    private final ViewGroup categories;
    private final TextView price;
    private final TextView amount;

    DetailsViewPresenter(View contentView, EventListener eventListener) {
        this.eventListener = eventListener;

        image = (ImageView) contentView.findViewById(R.id.image);
        description = (TextView) contentView.findViewById(R.id.description);
        categories = (ViewGroup) contentView.findViewById(R.id.categories);
        price = (TextView) contentView.findViewById(R.id.price);
        amount = (TextView) contentView.findViewById(R.id.amount);

        contentView.findViewById(R.id.decrement_amount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecrementAmount();
            }
        });
        contentView.findViewById(R.id.increment_amount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncrementAmount();
            }
        });
        contentView.findViewById(R.id.increment_amount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddToCart();
            }
        });
    }

    void displayItemInfo(Item item, ActionBar actionBar) {
        actionBar.setTitle(item.name());
        image.setImageResource(item.image());
        description.setText(item.description());
        price.setText(String.valueOf(item.price()));
        addCategory(item.gender().name());
        addCategory(item.category().name());
    }

    private void addCategory(String category) {
        LayoutInflater inflater = LayoutInflater.from(categories.getContext());
        TextView categoryView = (TextView) inflater.inflate(R.layout.details_category_item, categories, true);
        categoryView.setText(category);
    }

    private void onAddToCart() {
        eventListener.onAddToCartClicked(getAmountInt());
    }

    private void onDecrementAmount() {
        int newAmount = Math.max(1, getAmountInt() - 1);
        amount.setText(String.valueOf(newAmount));
    }

    private void onIncrementAmount() {
        int newAmount = Math.min(9, getAmountInt() + 1);
        amount.setText(String.valueOf(newAmount));
    }

    private int getAmountInt() {
        return Integer.valueOf(amount.getText().toString());
    }

    interface EventListener {
        void onAddToCartClicked(int amount);
    }
}
