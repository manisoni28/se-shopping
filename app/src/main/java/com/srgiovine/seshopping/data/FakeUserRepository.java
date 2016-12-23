package com.srgiovine.seshopping.data;

import com.srgiovine.seshopping.model.Address;
import com.srgiovine.seshopping.model.CreditCardInfo;
import com.srgiovine.seshopping.model.Name;
import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundAsyncTask;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;

// TODO replace FakeUserRepository
class FakeUserRepository implements UserRepository {

    @Override
    public BackgroundTask createUser(User user, Callback<User> callback) {
        BackgroundAsyncTask<User> backgroundTask = new BackgroundAsyncTask<User>(callback) {
            @Override
            protected User doInBackground() {
                Name name = Name.builder()
                        .setFirst("Sarah")
                        .setLast("Estrellado")
                        .build();

                Address address = Address.builder()
                        .setStreet("129 Western Road")
                        .setCity("Austin")
                        .setState("Texas")
                        .setZip("90012")
                        .setCountry("US")
                        .build();

                CreditCardInfo creditCardInfo = CreditCardInfo.builder()
                        .setCardHolderName(name)
                        .setCardNumber("1423 9902 5400 9043")
                        .setExpirationDate("9/2020")
                        .setSecurityCode(229)
                        .build();

                return User.builder()
                        .setEmail("Sarah.Estrellado@gmail.com")
                        .setPassword("password")
                        .setName(name)
                        .setAddress(address)
                        .setCreditCardInfo(creditCardInfo)
                        .build();
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                callback.onSuccess(user);
            }
        };
        backgroundTask.execute();
        return backgroundTask;
    }

    @Override
    public BackgroundTask updateUser(User user, Callback<User> callback) {
        return createUser(null, callback);
    }

    @Override
    public BackgroundTask getUser(String email, String password, Callback<User> callback) {
        return createUser(null, callback);
    }
}
