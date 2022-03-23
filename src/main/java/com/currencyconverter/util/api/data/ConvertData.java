package com.currencyconverter.util.api.data;

import java.math.BigDecimal;

public class ConvertData {

    private BigDecimal conversionRate;
    private BigDecimal destinationValue;

    private ConvertData(Builder builder) {
        conversionRate = builder.conversionRate;
        destinationValue = builder.destinationValue;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public BigDecimal getDestinationValue() {
        return destinationValue;
    }

    public static class Builder {

        private BigDecimal conversionRate;
        private BigDecimal destinationValue;

        public Builder conversionRate(BigDecimal conversionRate) {
            this.conversionRate = conversionRate;
            return this;
        }

        public Builder destinationValue(BigDecimal destinationValue) {
            this.destinationValue = destinationValue;
            return this;
        }

        public ConvertData build() {
            return new ConvertData(this);
        }
    }
}
