package com.dulsara.ewallet.ewalletapp.transaction.service;

import com.dulsara.ewallet.ewalletapp.transaction.model.Transaction;
import com.dulsara.ewallet.ewalletapp.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction saveTransaction(Double amount, Long walletId, Long referenceId, String transactionType, String transactionTransferType, String description, Instant date) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(date);
        transaction.setReferenceId(referenceId);
        transaction.setWalletId(walletId);
        transaction.setTransferType(transactionTransferType);
        transaction.setType(transactionType);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        return save(transaction);
    }
}
