package com.dulsara.ewallet.ewalletapp.transaction.service;

import com.dulsara.ewallet.ewalletapp.transaction.model.Transaction;
import com.dulsara.ewallet.ewalletapp.transaction.model.TransactionTransferType;
import com.dulsara.ewallet.ewalletapp.transaction.model.TransactionType;
import com.dulsara.ewallet.ewalletapp.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @Order(1)
    void save() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100D);
        transaction.setWalletId(1L);
        transaction.setReferenceId(0L);
        transaction.setTransactionDate(Instant.now());
        transaction.setDescription("Test 1");
        transaction.setType(TransactionType.DEBIT.toString());
        transaction.setTransferType(TransactionTransferType.CASH.toString());
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Transaction transaction1 = transactionService.save(transaction);
        Assertions.assertNotNull(transaction1);
        Assertions.assertEquals(transaction, transaction1, "Checking that Equal Transaction should be there");
    }

    @Test
    @Order(2)
    void saveTransaction() {
        Transaction transaction = new Transaction();
        Instant currentDateTime = Instant.now();
        transaction.setId(1L);
        transaction.setAmount(100D);
        transaction.setWalletId(1L);
        transaction.setReferenceId(0L);
        transaction.setTransactionDate(currentDateTime);
        transaction.setDescription("Test 2");
        transaction.setType(TransactionType.DEBIT.toString());
        transaction.setTransferType(TransactionTransferType.CASH.toString());
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Transaction transaction1 = transactionService.saveTransaction(100D, 1L, 0L, TransactionType.DEBIT.toString(), TransactionTransferType.CASH.toString(), "Test 2", currentDateTime);
    }
}