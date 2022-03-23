package com.currencyconverter.repository;

import com.currencyconverter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User where id = :id and excluded = 0")
    Optional<User> findById(Long id);

    @Query("from User where excluded = 0 order by id desc")
    List<User> findAll();

    @Query("from User where username = :username and excluded = 0")
    User findByUsername(String username);
}
