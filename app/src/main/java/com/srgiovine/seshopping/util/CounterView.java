package com.srgiovine.seshopping.util;

import android.view.View;
import android.widget.TextView;

import srgiovine.com.seshopping.R;

public class CounterView {

    private final TextView count;

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

    public int getCountInt() {
        return Integer.valueOf(count.getText().toString());
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
}
