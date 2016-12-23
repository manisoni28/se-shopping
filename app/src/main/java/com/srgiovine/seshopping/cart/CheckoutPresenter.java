package com.srgiovine.seshopping.cart;

import android.view.View;
import android.widget.TextView;

import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import java.util.Locale;

import srgiovine.com.seshopping.R;

class CheckoutPresenter {

    private final CartManager cartManager;

    private final TextView totalPrice;

    private final View buy;

    private final View updateSettings;

    private BackgroundTask validateUserPaymentTask;

    private final Callback<Void> validateUserPaymentCallback = new SimpleCallback<Void>() {
        @Override
        public void onSuccess(Void result) {
            onReadyToBuy();
        }

        @Override
        public void onFailed() {
            onUpdateInfoRequired();
        }
    };

    CheckoutPresenter(View contentView, CartManager cartManager, final EventListener eventListener) {
        this.cartManager = cartManager;

        totalPrice = (TextView) contentView.findViewById(R.id.total_price);
        buy = contentView.findViewById(R.id.buy);
        updateSettings = contentView.findViewById(R.id.update_settings);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBuyClicked();
            }
        });

        updateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onUpdateSettingsClicked();
            }
        });
    }

    void onPause() {
        if (validateUserPaymentTask != null) {
            validateUserPaymentTask.cancel();
            validateUserPaymentTask = null;
        }
    }

    void onBuyClicked() {
        // TODO onBuyClicked
    }

    void validateUserPaymentInfo() {
        validateUserPaymentTask = cartManager.validateUserPaymentInfo(validateUserPaymentCallback);
    }

    void updateTotalPrice(int totalPriceInt) {
        totalPrice.setText(String.format(Locale.US, "Total\n$%d", totalPriceInt));
        buy.setEnabled(totalPriceInt != 0);
    }

    private void onReadyToBuy() {
        buy.setVisibility(View.VISIBLE);
        updateSettings.setVisibility(View.GONE);
    }

    private void onUpdateInfoRequired() {
        buy.setVisibility(View.GONE);
        updateSettings.setVisibility(View.VISIBLE);
    }

    interface EventListener {
        void onUpdateSettingsClicked();
    }
}
