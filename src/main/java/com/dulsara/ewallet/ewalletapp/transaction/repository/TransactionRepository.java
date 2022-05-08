package com.dulsara.ewallet.ewalletapp.transaction.repository;

import com.dulsara.ewallet.ewalletapp.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
