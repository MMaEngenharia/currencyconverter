package com.currencyconverter.config.security;

public class Router {
    private static final String VERSION = "/api/v1";
    public static final String USER = Router.VERSION + "/user";
    public static final String TRANSACTION = Router.VERSION + "/transaction";

    static String[] VALID_ROUTES = {
        USER,
        TRANSACTION + "/convert",
        TRANSACTION + "/all/user/**"
    };
}
