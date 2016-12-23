package com.srgiovine.seshopping.cart;

import android.content.SharedPreferences;
import android.support.annotation.IntRange;
import android.text.TextUtils;

import com.srgiovine.seshopping.account.AccountManager;
import com.srgiovine.seshopping.data.ItemRepository;
import com.srgiovine.seshopping.model.Address;
import com.srgiovine.seshopping.model.CreditCardInfo;
import com.srgiovine.seshopping.model.Item;
import com.srgiovine.seshopping.model.Name;
import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private final AccountManager accountManager;

    private final ItemRepository itemRepository;

    private final Cart cart;

    private CartManager(Cart cart, AccountManager accountManager, ItemRepository itemRepository) {
        this.cart = cart;
        this.accountManager = accountManager;
        this.itemRepository = itemRepository;
    }

    BackgroundTask validateUserPaymentInfo(Callback<Void> callback) {
        return accountManager.getLoggedInUser(new GetLoggedInUserCallback(callback));
    }

    BackgroundTask getItemsInCart(Callback<List<CartItem>> callback) {
        return itemRepository.getItemsWithIds(cart.ids(), new GetItemsWithIdsCallback(callback));
    }

    public boolean addItemToCart(long itemId, @IntRange(from = 1) int count) {
        return cart.addItem(itemId, count);
    }

    boolean updateItemCount(long itemId, @IntRange(from = 1) int count) {
        return cart.updateItemCount(itemId, count);
    }

    void removeItemFromCart(long itemId) {
        cart.removeItem(itemId);
    }

    public void clearCart() {
        cart.clear();
    }

    public static CartManager create(AccountManager accountManager, ItemRepository itemRepository,
                                     SharedPreferences sharedPreferences) {
        return new CartManager(new Cart(sharedPreferences), accountManager, itemRepository);
    }

    private static boolean paymentAndAddressIsValid(User user) {
        if (user == null) {
            return false;
        }

        Address address = user.address();
        CreditCardInfo creditCardInfo = user.creditCardInfo();
        if (address == null || creditCardInfo == null) {
            return false;
        }

        Name cardHolderName = creditCardInfo.cardHolderName();
        if (cardHolderName == null) {
            return false;
        }

        return allStringsNotEmpty(address.street(), address.city(), address.state(), address.zip(),
                address.country(), creditCardInfo.cardNumber(), cardHolderName.first(), cardHolderName.last(),
                creditCardInfo.expirationDate(), String.valueOf(creditCardInfo.securityCode()));
    }

    private static boolean allStringsNotEmpty(String... strings) {
        for (String string : strings) {
            if (TextUtils.isEmpty(string)) {
                return false;
            }
        }
        return true;
    }

    private class GetLoggedInUserCallback implements Callback<User> {

        private final Callback<Void> callback;

        private GetLoggedInUserCallback(Callback<Void> callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(User user) {
            if (paymentAndAddressIsValid(user)) {
                callback.onSuccess(null);
            } else {
                onFailed();
            }
        }

        @Override
        public void onFailed() {
            callback.onFailed();
        }

        @Override
        public void onCancelled() {
            callback.onCancelled();
        }
    }

    private class GetItemsWithIdsCallback implements Callback<List<Item>> {

        private final Callback<List<CartItem>> callback;

        private GetItemsWithIdsCallback(Callback<List<CartItem>> callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(List<Item> items) {
            List<CartItem> cartItems = new ArrayList<>();
            for (Item item : items) {
                cartItems.add(new CartItem(cart.itemCount(item.id()), item));
            }
            callback.onSuccess(cartItems);
        }

        @Override
        public void onFailed() {
            callback.onFailed();
        }

        @Override
        public void onCancelled() {
            callback.onCancelled();
        }
    }
}
