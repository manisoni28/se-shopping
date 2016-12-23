package com.srgiovine.seshopping.util;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import srgiovine.com.seshopping.R;

public class CounterView {

    private final TextView count;

    @Nullable
    private EventListener eventListener;

    public CounterView(View contentView) {
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

    public void setCount(@IntRange(from = 1, to = 9) int countInt) {
        int currentCountInt = getCountInt();
        if (countInt != currentCountInt && countInt > 0 && countInt < 10) {
            setCountInternal(countInt);
            onCountUpdated(countInt);
        }
    }

    public void setEventListener(@Nullable EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public int getCountInt() {
        if (count.getText().length() == 0) {
            return 0;
        }

        return Integer.valueOf(count.getText().toString());
    }

    private void onDecrementCount() {
        setCount(getCountInt() - 1);
    }

    private void onIncrementCount() {
        setCount(getCountInt() + 1);
    }

    private void onCountUpdated(int newCount) {
        if (eventListener != null) {
            eventListener.onCountUpdated(newCount);
        }
    }

    private void setCountInternal(int countInt) {
        count.setText(String.valueOf(countInt));
    }

    public interface EventListener {
        void onCountUpdated(int newCount);
    }
}
