package com.srgiovine.seshopping.details;

import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.srgiovine.seshopping.model.Item;

import java.util.Locale;

import srgiovine.com.seshopping.R;

class DetailsViewPresenter {

    private final EventListener eventListener;

    private final ImageView image;
    private final TextView description;
    private final ViewGroup categories;
    private final TextView price;
    private final TextView count;

    DetailsViewPresenter(View contentView, EventListener eventListener) {
        this.eventListener = eventListener;

        image = (ImageView) contentView.findViewById(R.id.image);
        description = (TextView) contentView.findViewById(R.id.description);
        categories = (ViewGroup) contentView.findViewById(R.id.categories);
        price = (TextView) contentView.findViewById(R.id.price);
        count = (TextView) contentView.findViewById(R.id.count);

        contentView.findViewById(R.id.decrement_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDecrementCount();
            }
        });
        contentView.findViewById(R.id.increment_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIncrementCount();
            }
        });
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
        eventListener.onAddToCartClicked(getCountInt());
    }

    private void onDecrementCount() {
        int countInt = getCountInt();
        if (countInt > 1) {
            setCountInt(countInt - 1);
        }
    }

    private void onIncrementCount() {
        int countInt = getCountInt();
        if (countInt < 9) {
            setCountInt(countInt + 1);
        }
    }

    private void setCountInt(int countInt) {
        count.setText(String.valueOf(countInt));
    }

    private int getCountInt() {
        return Integer.valueOf(count.getText().toString());
    }

    interface EventListener {
        void onAddToCartClicked(int count);
    }
}
