package com.currencyconverter.service;

import com.currencyconverter.model.User;
import com.currencyconverter.repository.UserRepository;
import com.currencyconverter.util.exception.DuplicateRecordException;
import com.currencyconverter.util.exception.RecordNotFindException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    private final String RECORD_NOT_FIND = "User not found!";
    private final String DUPLICATE_RECORD = "User already registered!";

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    User findById(final Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RecordNotFindException(RECORD_NOT_FIND));
    }

    User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Transactional
    public User save(User user) {
        User newUser = findByUsername(user.getUsername());
        if (Objects.nonNull(newUser))
            throw new DuplicateRecordException(DUPLICATE_RECORD);
        return repository.save(user);
    }
}
