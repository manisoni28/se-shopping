package com.srgiovine.seshopping.model;

public class CreditCardInfo {

    private final String cardNumber;
    private final Name cardHolderName;
    private final String expirationDate;
    private final int securityCode;

    private CreditCardInfo(Name cardHolderName, String cardNumber, String expirationDate, int securityCode) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
    }

    public Name cardHolderName() {
        return cardHolderName;
    }

    public String expirationDate() {
        return expirationDate;
    }

    public String cardNumber() {
        return cardNumber;
    }

    public int securityCode() {
        return securityCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Name cardHolderName;
        private String cardNumber;
        private String expirationDate;
        private int securityCode;

        public Builder setCardHolderName(Name cardHolderName) {
            this.cardHolderName = cardHolderName;
            return this;
        }

        public Builder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder setSecurityCode(int securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        public CreditCardInfo build() {
            return new CreditCardInfo(cardHolderName, cardNumber, expirationDate, securityCode);
        }
    }
}
