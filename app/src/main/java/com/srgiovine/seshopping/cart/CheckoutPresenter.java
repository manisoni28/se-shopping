package com.srgiovine.seshopping.cart;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;
import com.srgiovine.seshopping.util.LoadingIndicatorDialog;

import java.util.Locale;

import srgiovine.com.seshopping.R;

class CheckoutPresenter {

    private final CartManager cartManager;
    private final EventListener eventListener;

    private final TextView totalPrice;
    private final View checkout;
    private final View updateSettings;

    private final LoadingIndicatorDialog loadingIndicator = new LoadingIndicatorDialog();

    private BackgroundTask validateUserPaymentTask;
    private BackgroundTask purchaseItemsTask;

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

    private final Callback<Void> purchaseItemsCallback = new SimpleCallback<Void>() {
        @Override
        public void onSuccess(Void result) {
            onPurchaseComplete();
        }

        @Override
        public void onFailed() {
            loadingIndicator.dismiss();
            Toast.makeText(checkout.getContext(), "Failed to complete purchase", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled() {
            loadingIndicator.dismiss();
        }
    };

    CheckoutPresenter(View contentView, CartManager cartManager, final EventListener eventListener) {
        this.cartManager = cartManager;
        this.eventListener = eventListener;

        totalPrice = (TextView) contentView.findViewById(R.id.total_price);
        checkout = contentView.findViewById(R.id.checkout);
        updateSettings = contentView.findViewById(R.id.update_settings);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPurchaseClicked();
            }
        });

        updateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onUpdateSettingsClicked();
            }
        });
    }

    void validateUserPaymentInfo() {
        validateUserPaymentTask = cartManager.validateUserPaymentInfo(validateUserPaymentCallback);
    }

    void updateTotalPrice(int totalPriceInt) {
        totalPrice.setText(String.format(Locale.US, "Total\n$%d", totalPriceInt));
        checkout.setEnabled(totalPriceInt != 0);
    }

    void onPause() {
        if (validateUserPaymentTask != null) {
            validateUserPaymentTask.cancel();
            validateUserPaymentTask = null;
        }
    }

    void onDestroy() {
        loadingIndicator.dismiss();
        if (purchaseItemsTask != null) {
            purchaseItemsTask.cancel();
            purchaseItemsTask = null;
        }
    }

    private void onPurchaseClicked() {
        loadingIndicator.show(checkout.getContext(), "Performing checkout transaction...");
        purchaseItemsTask = cartManager.purchaseItemsInCart(purchaseItemsCallback);
    }

    private void onPurchaseComplete() {
        loadingIndicator.dismiss();
        eventListener.onPurchaseComplete();
    }

    private void onReadyToBuy() {
        checkout.setVisibility(View.VISIBLE);
        updateSettings.setVisibility(View.GONE);
    }

    private void onUpdateInfoRequired() {
        checkout.setVisibility(View.GONE);
        updateSettings.setVisibility(View.VISIBLE);
    }

    interface EventListener {
        void onUpdateSettingsClicked();

        void onPurchaseComplete();
    }
}
