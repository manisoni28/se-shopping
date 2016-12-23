package com.srgiovine.seshopping.details;

import android.app.ActionBar;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.util.CounterViewPresenter;

import java.util.Locale;

import srgiovine.com.seshopping.R;

class DetailsViewPresenter implements CounterViewPresenter.EventListener {

    private final EventListener eventListener;

    private final CounterViewPresenter counterViewPresenter;

    private final ImageView image;
    private final TextView description;
    private final ViewGroup categories;
    private final TextView price;
    private final TextView totalPrice;

    @Nullable
    private Item item;

    DetailsViewPresenter(View contentView, EventListener eventListener) {
        this.eventListener = eventListener;

        image = (ImageView) contentView.findViewById(R.id.image);
        description = (TextView) contentView.findViewById(R.id.description);
        categories = (ViewGroup) contentView.findViewById(R.id.categories);
        price = (TextView) contentView.findViewById(R.id.price);
        totalPrice = (TextView) contentView.findViewById(R.id.total_price);

        contentView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddToCart();
            }
        });

        counterViewPresenter = new CounterViewPresenter(contentView, this);
    }

    @Override
    public void onCountUpdated(int newCount) {
        updatePrice(newCount);
    }

    void displayItemInfo(Item item, ActionBar actionBar) {
        this.item = item;

        actionBar.setTitle(item.name());
        image.setImageResource(item.image());
        description.setText(item.description());
        updatePrice(counterViewPresenter.getCount());
        addCategory(item.gender().name());
        addCategory(item.category().name());
    }

    private void updatePrice(int countInt) {
        if (item == null) {
            return;
        }

        price.setText(String.format(Locale.US, "Price\n$%d", item.price()));
        totalPrice.setText(String.format(Locale.US, "Total\n$%d", item.price() * countInt));
    }

    private void addCategory(String category) {
        LayoutInflater inflater = LayoutInflater.from(categories.getContext());
        TextView categoryView = (TextView) inflater.inflate(R.layout.details_category_item, categories, false);
        categoryView.setText(category);
        categories.addView(categoryView);
    }

    private void onAddToCart() {
        eventListener.onAddToCartClicked(counterViewPresenter.getCount());
    }

    interface EventListener {
        void onAddToCartClicked(int count);
    }
}
