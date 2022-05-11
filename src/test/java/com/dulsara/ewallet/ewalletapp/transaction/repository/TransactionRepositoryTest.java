package com.dulsara.ewallet.ewalletapp.transaction.repository;

import com.dulsara.ewallet.ewalletapp.transaction.model.Transaction;
import com.dulsara.ewallet.ewalletapp.transaction.model.TransactionTransferType;
import com.dulsara.ewallet.ewalletapp.transaction.model.TransactionType;
import com.dulsara.ewallet.ewalletapp.wallet.model.Wallet;
import com.dulsara.ewallet.ewalletapp.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void save() {

        Transaction transaction = new Transaction();
        transaction.setAmount(100D);
        transaction.setWalletId(1L);
        transaction.setReferenceId(0L);
        transaction.setTransactionDate(Instant.now());
        transaction.setDescription("Test Repo 1");
        transaction.setType(TransactionType.DEBIT.toString());
        transaction.setTransferType(TransactionTransferType.CASH.toString());
        Transaction transaction1 =  transactionRepository.save(transaction);

        Assertions.assertNotNull(transaction1.getId());
        Assertions.assertEquals(transaction1.getAmount(), transaction.getAmount(), "check Amount field  ");
    }
}