package com.srgiovine.seshopping.util;

import android.support.annotation.IntRange;
import android.view.View;
import android.widget.TextView;

import srgiovine.com.seshopping.R;

public class CounterViewPresenter {

    private final TextView count;

    private final EventListener eventListener;

    public CounterViewPresenter(View contentView, EventListener eventListener) {
        this.eventListener = eventListener;

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
    }

    public void setCount(@IntRange(from = 1) int countInt) {
        int currentCountInt = getCount();
        if (countInt != currentCountInt && countInt > 0) {
            setCountInternal(countInt);
            onCountUpdated(countInt);
        }
    }

    public int getCount() {
        if (count.getText().length() == 0) {
            return 0;
        }

        return Integer.valueOf(count.getText().toString());
    }

    private void onDecrementCount() {
        setCount(getCount() - 1);
    }

    private void onIncrementCount() {
        setCount(getCount() + 1);
    }

    private void onCountUpdated(int newCount) {
        eventListener.onCountUpdated(newCount);
    }

    private void setCountInternal(int countInt) {
        count.setText(String.valueOf(countInt));
    }

    public interface EventListener {
        void onCountUpdated(int newCount);
    }
}
