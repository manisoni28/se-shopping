package com.srgiovine.seshopping.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.srgiovine.seshopping.model.Address;
import com.srgiovine.seshopping.model.CreditCardInfo;
import com.srgiovine.seshopping.model.Name;
import com.srgiovine.seshopping.model.User;

import srgiovine.com.seshopping.R;

public class SettingsViewPresenter {

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

    private ProgressDialog loadingIndicator;

    public SettingsViewPresenter(View rootView, final EventListener eventListener) {
        email = (TextView) rootView.findViewById(R.id.email);
        name = (TextView) rootView.findViewById(R.id.name);

        street = (EditText) rootView.findViewById(R.id.street);
        city = (EditText) rootView.findViewById(R.id.city);
        state = (EditText) rootView.findViewById(R.id.state);
        zip = (EditText) rootView.findViewById(R.id.zip);
        country = (EditText) rootView.findViewById(R.id.country);

        creditCardNumber = (EditText) rootView.findViewById(R.id.card_number);
        cardHolderName = (EditText) rootView.findViewById(R.id.card_holder_name);
        expirationDate = (EditText) rootView.findViewById(R.id.expiration_date);
        securityCode = (EditText) rootView.findViewById(R.id.security_code);

        edit = rootView.findViewById(R.id.edit);
        save = rootView.findViewById(R.id.save);

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

        rootView.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onLogoutClicked();
            }
        });

        setEditMode(false);
    }

    public void showLoadingIndicator(Context context, String message) {
        loadingIndicator = new ProgressDialog(context);
        loadingIndicator.setMessage(message);
        loadingIndicator.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingIndicator.setIndeterminate(true);
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();
    }

    public void dismissLoadingIndicator() {
        if (loadingIndicator != null) {
            loadingIndicator.dismiss();
            loadingIndicator = null;
        }
    }

    public void showUserInfo(User user) {
        userPassword = user.password();

        email.setText(user.email());
        name.setText(user.name().toString());

        Address address = user.address();
        if (address != null) {
            street.setText(address.street());
            city.setText(address.city());
            state.setText(address.state());
            zip.setText(String.valueOf(address.zip()));
            country.setText(address.country());
        }

        CreditCardInfo creditCardInfo = user.creditCardInfo();
        if (creditCardInfo != null) {
            creditCardNumber.setText(creditCardInfo.cardNumber());
            cardHolderName.setText(creditCardInfo.cardHolderName().toString());
            expirationDate.setText(creditCardInfo.expirationDate());
            securityCode.setText(String.valueOf(creditCardInfo.securityCode()));
        }
    }

    public void setEditMode(boolean editMode) {
        edit.setEnabled(!editMode);
        save.setEnabled(editMode);

        setEditable(editMode, street, city, state, zip, country);
        setEditable(editMode, creditCardNumber, cardHolderName, expirationDate, securityCode);
    }

    public boolean validateAddress() {
        return validateAllOrNoFieldsFilled(street, city, state, zip, country);
    }

    public boolean validateCreditCardInfo() {
        return validateAllOrNoFieldsFilled(creditCardNumber, cardHolderName, expirationDate, securityCode);
    }

    public User buildUser() {
        Name nameObj = Name.fromFullName(name.getText().toString());

        Address addressObj = Address.builder()
                .setStreet(street.getText().toString())
                .setCity(city.getText().toString())
                .setState(state.getText().toString())
                .setZip(zip.getText().toString())
                .setCountry(country.getText().toString())
                .build();

        CreditCardInfo creditCardInfo = CreditCardInfo.builder()
                .setCardHolderName(Name.fromFullName(cardHolderName.getText().toString()))
                .setCardNumber(creditCardNumber.getText().toString())
                .setExpirationDate(expirationDate.getText().toString())
                .setSecurityCode(Integer.valueOf(securityCode.getText().toString()))
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

    public interface EventListener {
        void onEditClicked();

        void onSaveClicked();

        void onLogoutClicked();
    }
}
