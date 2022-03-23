package com.currencyconverter.controller;

import com.currencyconverter.config.ResponseApi;
import com.currencyconverter.config.security.Router;
import com.currencyconverter.service.TransactionService;
import com.currencyconverter.util.api.data.RequestData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Router.TRANSACTION)
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/convert")
    public ResponseEntity converte(@RequestBody RequestData requestData) {
        return ResponseApi.ok(service.convert(requestData));
    }

    @GetMapping("/all/user/{id}")
    public ResponseEntity findAllTransactionsOrderByIdDesc(@PathVariable Long id) {
        return ResponseApi.ok(service.findAllTransactionsOrderByIdDesc(id));
    }
}
