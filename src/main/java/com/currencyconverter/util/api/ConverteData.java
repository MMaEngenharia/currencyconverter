package com.currencyconverter.util.api;

import java.math.BigDecimal;

public class ConverteData {

    private BigDecimal conversionRate;
    private BigDecimal destinationValue;

    private ConverteData() {

    }

    private ConverteData(Builder builder) {
        this.conversionRate = builder.conversionRate;
        this.destinationValue = builder.destinationValue;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public BigDecimal getDestinationValue() {
        return destinationValue;
    }

    static class Builder {

        private BigDecimal conversionRate;
        private BigDecimal destinationValue;

        Builder conversionRate(BigDecimal conversionRate) {
            this.conversionRate = conversionRate;
            return this;
        }

        Builder destinationValue(BigDecimal destinationValue) {
            this.destinationValue = destinationValue;
            return this;
        }

        ConverteData build() {
            return new ConverteData(this);
        }
    }
}
