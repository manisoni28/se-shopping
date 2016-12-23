package com.srgiovine.seshopping.settings;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.srgiovine.seshopping.model.Address;
import com.srgiovine.seshopping.model.CreditCardInfo;
import com.srgiovine.seshopping.model.Name;
import com.srgiovine.seshopping.model.User;

import srgiovine.com.seshopping.R;

class SettingsViewPresenter {

    private final TextView email;
    private final TextView name;

    private final EditText street;
    private final EditText city;
    private final EditText state;
    private final EditText zip;
    private final EditText country;

    private final EditText creditCardNumber;
    private final EditText cardHolderName;
    private final EditText expirationDate;
    private final EditText securityCode;

    private final View edit;
    private final View save;

    private String userPassword;

    SettingsViewPresenter(View contentView, final EventListener eventListener) {
        email = (TextView) contentView.findViewById(R.id.email);
        name = (TextView) contentView.findViewById(R.id.name);

        street = (EditText) contentView.findViewById(R.id.street);
        city = (EditText) contentView.findViewById(R.id.city);
        state = (EditText) contentView.findViewById(R.id.state);
        zip = (EditText) contentView.findViewById(R.id.zip);
        country = (EditText) contentView.findViewById(R.id.country);

        creditCardNumber = (EditText) contentView.findViewById(R.id.card_number);
        cardHolderName = (EditText) contentView.findViewById(R.id.card_holder_name);
        expirationDate = (EditText) contentView.findViewById(R.id.expiration_date);
        securityCode = (EditText) contentView.findViewById(R.id.security_code);

        edit = contentView.findViewById(R.id.edit);
        save = contentView.findViewById(R.id.save);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onEditClicked();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onSaveClicked();
            }
        });

        contentView.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onLogoutClicked();
            }
        });

        setEditMode(false);
    }

    void displayUserInfo(User user) {
        userPassword = user.password();

        email.setText(user.email());
        name.setText(user.name().toString());

        Address address = user.address();
        if (address != null) {
            street.setText(address.street());
            city.setText(address.city());
            state.setText(address.state());
            country.setText(address.country());

            if (!TextUtils.isEmpty(address.zip())) {
                zip.setText(String.valueOf(address.zip()));
            }
        }

        CreditCardInfo creditCardInfo = user.creditCardInfo();
        if (creditCardInfo != null) {
            creditCardNumber.setText(creditCardInfo.cardNumber());
            expirationDate.setText(creditCardInfo.expirationDate());

            int securityCodeInt = creditCardInfo.securityCode();
            if (securityCodeInt > 0) {
                securityCode.setText(String.valueOf(securityCodeInt));
            }

            String cardHolderNameStr = creditCardInfo.cardHolderName().toString();
            if (!TextUtils.isEmpty(cardHolderNameStr)) {
                cardHolderName.setText(cardHolderNameStr);
            }
        }
    }

    void setEditMode(boolean editMode) {
        edit.setEnabled(!editMode);
        save.setEnabled(editMode);

        setEditable(editMode, street, city, state, zip, country);
        setEditable(editMode, creditCardNumber, cardHolderName, expirationDate, securityCode);
    }

    boolean validateAddress() {
        return validateAllOrNoFieldsFilled(street, city, state, zip, country);
    }

    boolean validateCreditCardInfo() {
        if (!Name.isValidFullName(cardHolderName.getText().toString())) {
            cardHolderName.setError("Must have first and last name");
            return false;
        }
        return validateAllOrNoFieldsFilled(creditCardNumber, cardHolderName, expirationDate, securityCode);
    }

    User buildUser() {
        Name nameObj = Name.fromFullName(name.getText().toString());

        Address addressObj = Address.builder()
                .setStreet(street.getText().toString())
                .setCity(city.getText().toString())
                .setState(state.getText().toString())
                .setZip(zip.getText().toString())
                .setCountry(country.getText().toString())
                .build();

        int securityCodeInt = securityCode.getText().length() == 0 ? 0 :
                Integer.valueOf(securityCode.getText().toString());
        CreditCardInfo creditCardInfo = CreditCardInfo.builder()
                .setCardHolderName(Name.fromFullName(cardHolderName.getText().toString()))
                .setCardNumber(creditCardNumber.getText().toString())
                .setExpirationDate(expirationDate.getText().toString())
                .setSecurityCode(securityCodeInt)
                .build();

        return User.builder()
                .setEmail(email.getText().toString())
                .setPassword(userPassword)
                .setName(nameObj)
                .setAddress(addressObj)
                .setCreditCardInfo(creditCardInfo)
                .build();
    }

    private void setEditable(boolean editable, EditText... fields) {
        for (EditText field : fields) {
            field.setEnabled(editable);
            field.setFocusableInTouchMode(editable);
            field.setFocusable(editable);
        }
    }

    private boolean validateAllOrNoFieldsFilled(EditText... fields) {
        boolean allFieldsAreEmpty = true;
        boolean allFieldsAreFilled = true;

        for (EditText field : fields) {
            if (field.getText().length() == 0) {
                allFieldsAreFilled = false;
            } else {
                allFieldsAreEmpty = false;
            }
        }

        boolean valid = allFieldsAreEmpty || allFieldsAreFilled;

        if (!valid) {
            for (EditText field : fields) {
                field.setError(field.getText().length() == 0 ? "This field is required" : null);
            }
        }

        return valid;
    }

    interface EventListener {
        void onEditClicked();

        void onSaveClicked();

        void onLogoutClicked();
    }
}
