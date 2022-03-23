package com.currencyconverter.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
public class Transaction extends EntityBase<Long> {

    @NotNull
    @NotEmpty
    @Column(name = "source_currency", nullable = false)
    private String sourceCurrency;

    @NotNull
    @Column(name = "source_value")
    private BigDecimal sourceValue;

    @NotNull
    @NotEmpty
    @Column(name = "destination_currency", nullable = false)
    private String destinationCurrency;

    @Transient
    private BigDecimal destinationValue;

    @NotNull
    @Column(name = "conversion_rate", nullable = false)
    private BigDecimal conversionRate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Transaction() {
    }

    private Transaction(Builder builder) {
        sourceCurrency = builder.sourceCurrency;
        sourceValue = builder.sourceValue;
        destinationCurrency = builder.destinationCurrency;
        destinationValue = builder.destinationValue;
        conversionRate = builder.conversionRate;
        user = builder.user;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public BigDecimal getSourceValue() {
        return sourceValue;
    }

    public String getDestinationCurrency() {
        return destinationCurrency;
    }

    public BigDecimal getDestinationValue() {
        return destinationValue;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public User getUser() {
        return user;
    }

    public static class Builder {

        private String sourceCurrency;
        private BigDecimal sourceValue;
        private String destinationCurrency;
        private BigDecimal destinationValue;
        private BigDecimal conversionRate;
        private User user;

        public Builder sourceCurrency(String sourceCurrency) {
            this.sourceCurrency = sourceCurrency;
            return this;
        }

        public Builder sourceValue(BigDecimal sourceValue) {
            this.sourceValue = sourceValue;
            return this;
        }

        public Builder destinationCurrency(String destinationCurrency) {
            this.destinationCurrency = destinationCurrency;
            return this;
        }

        public Builder destinationValue(BigDecimal destinationValue) {
            this.destinationValue = destinationValue;
            return this;
        }

        public Builder conversionRate(BigDecimal conversionRate) {
            this.conversionRate = conversionRate;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
