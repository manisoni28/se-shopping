package com.srgiovine.seshopping.details;

import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.util.CounterView;

import java.util.Locale;

import srgiovine.com.seshopping.R;

class DetailsViewPresenter {

    private final EventListener eventListener;

    private final CounterView counterView;

    private final ImageView image;
    private final TextView description;
    private final ViewGroup categories;
    private final TextView price;

    DetailsViewPresenter(View contentView, CounterView counterView, EventListener eventListener) {
        this.eventListener = eventListener;
        this.counterView = counterView;

        image = (ImageView) contentView.findViewById(R.id.image);
        description = (TextView) contentView.findViewById(R.id.description);
        categories = (ViewGroup) contentView.findViewById(R.id.categories);
        price = (TextView) contentView.findViewById(R.id.price);

        contentView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
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
        price.setText(String.format(Locale.US, "$%d", item.price()));
        addCategory(item.gender().name());
        addCategory(item.category().name());
    }

    private void addCategory(String category) {
        LayoutInflater inflater = LayoutInflater.from(categories.getContext());
        TextView categoryView = (TextView) inflater.inflate(R.layout.details_category_item, categories, false);
        categoryView.setText(category);
        categories.addView(categoryView);
    }

    private void onAddToCart() {
        eventListener.onAddToCartClicked(counterView.getCountInt());
    }

    interface EventListener {
        void onAddToCartClicked(int count);
    }
}
