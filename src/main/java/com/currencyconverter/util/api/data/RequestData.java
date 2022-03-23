package com.currencyconverter.util.api.data;

import com.currencyconverter.model.User;

import java.math.BigDecimal;

public class RequestData {

    private String from;
    private String to;
    private BigDecimal amount;
    private User user;

    public RequestData() {

    }

    private RequestData(Builder builder) {
        from = builder.from;
        to = builder.to;
        amount = builder.amount;
        user = builder.user;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public static class Builder {

        private String from;
        private String to;
        private BigDecimal amount;
        private User user;

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public RequestData build() {
            return new RequestData(this);
        }
    }
}
