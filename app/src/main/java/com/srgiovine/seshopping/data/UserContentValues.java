package com.srgiovine.seshopping.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.srgiovine.seshopping.model.Address;
import com.srgiovine.seshopping.model.CreditCardInfo;
import com.srgiovine.seshopping.model.Name;
import com.srgiovine.seshopping.model.User;

final class UserContentValues {

    private UserContentValues() {
    }

    static User toUser(Cursor cursor) {
        Name userName = Name.builder()
                .setFirst(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FIRST_NAME)))
                .setLast(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.LAST_NAME)))
                .build();

        Address address = Address.builder()
                .setStreet(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.STREET)))
                .setCity(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.CITY)))
                .setState(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.STATE)))
                .setZip(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.ZIP)))
                .setCountry(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.COUNTRY)))
                .build();

        Name cardHolderName = Name.builder()
                .setFirst(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.CARD_HOLDER_FIRST_NAME)))
                .setLast(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.CARD_HOLDER_LAST_NAME)))
                .build();

        CreditCardInfo creditCardInfo = CreditCardInfo.builder()
                .setCardHolderName(cardHolderName)
                .setCardNumber(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.CARD_NUMBER)))
                .setExpirationDate(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.CARD_EXPIRATION_DATE)))
                .setSecurityCode(cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.CARD_SECURITY_CODE)))
                .build();

        return User.builder()
                .setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.EMAIL)))
                .setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.PASSWORD)))
                .setName(userName)
                .setAddress(address)
                .setCreditCardInfo(creditCardInfo)
                .build();
    }

    static ContentValues toContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.EMAIL, user.email());
        contentValues.put(UserContract.PASSWORD, user.password());

        contentValues.put(UserContract.FIRST_NAME, user.name().first());
        contentValues.put(UserContract.LAST_NAME, user.name().last());

        Address address = user.address();
        if (address != null) {
            contentValues.put(UserContract.STREET, address.street());
            contentValues.put(UserContract.CITY, address.city());
            contentValues.put(UserContract.STATE, address.state());
            contentValues.put(UserContract.ZIP, address.zip());
            contentValues.put(UserContract.COUNTRY, address.country());
        }

        CreditCardInfo creditCardInfo = user.creditCardInfo();
        if (creditCardInfo != null) {
            contentValues.put(UserContract.CARD_NUMBER, creditCardInfo.cardNumber());
            contentValues.put(UserContract.CARD_HOLDER_FIRST_NAME, creditCardInfo.cardHolderName().first());
            contentValues.put(UserContract.CARD_HOLDER_LAST_NAME, creditCardInfo.cardHolderName().last());
            contentValues.put(UserContract.CARD_EXPIRATION_DATE, creditCardInfo.expirationDate());
            if (creditCardInfo.securityCode() > 0) {
                contentValues.put(UserContract.CARD_SECURITY_CODE, creditCardInfo.securityCode());
            }
        }

        return contentValues;
    }
}
