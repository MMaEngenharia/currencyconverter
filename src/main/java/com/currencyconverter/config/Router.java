package com.currencyconverter.config;

public class Router {
    private static final String VERSION = "api/v1";
    public static final String TRANSACTION = Router.VERSION + "transaction";
    public static final String USER = Router.VERSION + "user";

    static String[] VALID_ROUTES = {
        USER,
        TRANSACTION
    };
}
