package com.currencyconverter.repository;

import com.currencyconverter.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("from Transaction t where t.user.id = :userId and t.excluded = 0 order by t.id desc")
    List<Transaction> findAllTransactionsOrderByIdDesc(long userId);
}
