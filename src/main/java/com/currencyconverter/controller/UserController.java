package com.currencyconverter.controller;

import com.currencyconverter.config.ResponseApi;
import com.currencyconverter.config.security.Router;
import com.currencyconverter.model.User;
import com.currencyconverter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Router.USER)
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody User user) {
        return ResponseApi
            .ok(
                service.save(user),
                HttpStatus.CREATED
            );
    }
}
