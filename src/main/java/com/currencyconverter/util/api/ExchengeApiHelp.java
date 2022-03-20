package com.currencyconverter.util.api;

import com.currencyconverter.util.exception.NotAcceptable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class ExchengeApiHelp {

    private static final int SCALE = 2;
    private static final String MESSAGE = "Taxa de conversão informada não é válida: %s";
    private static final String ACCESS_KEY = "bca312a1593dcd663a55085bf1ab3203";
    private static final String BASE = "EUR";
    private static final String URL = "http://api.exchangeratesapi.io/latest?access_key=%s&base=%s";

    public static ConverteData converte(String from, String to, BigDecimal amount) {
        ExchengeApiData exchengeApiData = getConversionRate();

        BigDecimal fromValue = exchengeApiData.getRates().entrySet().stream()
            .filter(f -> f.getKey().equals(from))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElseThrow(() -> new NotAcceptable(String.format(MESSAGE, from)));

        BigDecimal toValue = exchengeApiData.getRates().entrySet().stream()
            .filter(f -> f.getKey().equals(to))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElseThrow(() -> new NotAcceptable(String.format(MESSAGE, to)));


        BigDecimal destinationValue = toValue
            .divide(
                fromValue,
                SCALE,
                RoundingMode.HALF_EVEN
            )
            .multiply(amount);

        return new ConverteData
            .Builder()
            .conversionRate(toValue)
            .destinationValue(destinationValue)
            .build();
    }

    public static ExchengeApiData getConversionRate() {
        String url = getUrl();
        ResponseEntity<ExchengeApiData> responseEntity = new RestTemplate()
            .getForEntity(
                url,
                ExchengeApiData.class
            );
        return responseEntity.getBody();
    }

    private static String getUrl() {
        return String.format(
            URL,
            ACCESS_KEY,
            BASE
        );
    }
}