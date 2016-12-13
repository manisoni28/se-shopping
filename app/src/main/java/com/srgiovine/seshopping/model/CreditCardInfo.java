package com.srgiovine.seshopping.model;

public class CreditCardInfo {

    private final String number;
    private final Name cardHolderName;
    private final int expirationMonth;
    private final int expirationYear;
    private final int securityCode;

    private CreditCardInfo(Name cardHolderName, String number, int expirationMonth, int expirationYear, int securityCode) {
        this.cardHolderName = cardHolderName;
        this.number = number;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.securityCode = securityCode;
    }

    public Name cardHolderName() {
        return cardHolderName;
    }

    public int expirationMonth() {
        return expirationMonth;
    }

    public int expirationYear() {
        return expirationYear;
    }

    public String number() {
        return number;
    }

    public int securityCode() {
        return securityCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Name cardHolderName;
        private String number;
        private int expirationMonth;
        private int expirationYear;
        private int securityCode;

        public Builder setCardHolderName(Name cardHolderName) {
            this.cardHolderName = cardHolderName;
            return this;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setExpirationMonth(int expirationMonth) {
            this.expirationMonth = expirationMonth;
            return this;
        }

        public Builder setExpirationYear(int expirationYear) {
            this.expirationYear = expirationYear;
            return this;
        }

        public Builder setSecurityCode(int securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        public CreditCardInfo build() {
            return new CreditCardInfo(cardHolderName, number, expirationMonth, expirationYear, securityCode);
        }
    }
}
