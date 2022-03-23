package com.currencyconverter.service;

import com.currencyconverter.model.Transaction;
import com.currencyconverter.model.User;
import com.currencyconverter.repository.TransactionRepository;
import com.currencyconverter.util.api.ExchengeApiHelp;
import com.currencyconverter.util.api.data.ConvertData;
import com.currencyconverter.util.api.data.RequestData;
import com.currencyconverter.util.exception.BadRequestException;
import com.currencyconverter.util.exception.NoContentException;
import com.currencyconverter.util.exception.RecordNotFindException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository repository;

    public TransactionService(UserService userService, TransactionRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }

    @Transactional
    public Transaction convert(RequestData requestData) {
        validateRequestData(requestData);

        User user = userService.findById(requestData.getUser().getId());

        ConvertData convertData = ExchengeApiHelp
            .convert(
                requestData.getFrom(),
                requestData.getTo(),
                requestData.getAmount()
            );

        Transaction transaction = new Transaction.Builder()
            .sourceCurrency(requestData.getFrom())
            .destinationCurrency(requestData.getTo())
            .sourceValue(requestData.getAmount())
            .destinationValue(convertData.getDestinationValue())
            .conversionRate(convertData.getConversionRate())
            .user(user)
            .build();

        return repository.save(transaction);
    }

    public List<Transaction> findAllTransactionsOrderByIdDesc(Long userId) {
        if (Objects.isNull(userId))
            throw new RecordNotFindException("User ID must be provided.");

        User user = userService.findById(userId);

        List<Transaction> transactions = repository
            .findAllTransactionsOrderByIdDesc(user.getId());

        if (transactions.isEmpty())
            throw new NoContentException();

        return transactions;
    }

    private void validateRequestData(RequestData requestData) {
        if (Objects.isNull(requestData.getFrom()) || requestData.getFrom().isEmpty())
            throw new BadRequestException("The origin must be informed.");

        if (Objects.isNull(requestData.getTo()) || requestData.getTo().isEmpty())
            throw new BadRequestException("The destination must be informed.");

        if (Objects.isNull(requestData.getAmount()))
            throw new BadRequestException("The amount must be informed.");

        if (requestData.getAmount().doubleValue() <= 0)
            throw new BadRequestException("The value must be greater than zero.");

        if (Objects.isNull(requestData.getUser()) || Objects.isNull(requestData.getUser().getId()))
            throw new BadRequestException("The user must be informed.");
    }
}
