package com.currencyconverter.util.api;

import com.currencyconverter.util.api.data.ConvertData;
import com.currencyconverter.util.api.data.ExchengeApiData;
import com.currencyconverter.util.exception.NotAcceptableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class ExchengeApiHelp {

    private static final int SCALE = 2;
    private static final String MESSAGE = "Conversion rate reported is not valid: %s";
    private static final String ACCESS_KEY = "bca312a1593dcd663a55085bf1ab3203";
    private static final String BASE = "EUR";
    private static final String URL = "http://api.exchangeratesapi.io/latest?access_key=%s&base=%s";

    public static ConvertData convert(String from, String to, BigDecimal amount) {
        ExchengeApiData exchengeApiData = getConversionRate();

        BigDecimal fromValue = getRateValue(from, exchengeApiData);

        BigDecimal toValue = getRateValue(to, exchengeApiData);

        BigDecimal destinationValue = toValue
            .divide(
                fromValue,
                SCALE,
                RoundingMode.HALF_EVEN
            )
            .multiply(amount);

        return new ConvertData
            .Builder()
            .conversionRate(toValue)
            .destinationValue(destinationValue)
            .build();
    }

    private static BigDecimal getRateValue(String from, ExchengeApiData exchengeApiData) {
        return exchengeApiData
            .getRates()
            .entrySet().stream()
            .filter(f -> f.getKey().equals(from))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElseThrow(() -> new NotAcceptableException(String.format(MESSAGE, from)));
    }

    private static ExchengeApiData getConversionRate() {
        String url = getUrl();
        ResponseEntity<ExchengeApiData> responseEntity = new RestTemplate()
            .getForEntity(
                url,
                ExchengeApiData.class
            );
        return responseEntity.getBody();
    }

    public static String getUrl() {
        return String.format(
            URL,
            ACCESS_KEY,
            BASE
        );
    }
}