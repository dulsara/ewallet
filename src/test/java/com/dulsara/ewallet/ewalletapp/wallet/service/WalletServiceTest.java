package com.dulsara.ewallet.ewalletapp.wallet.service;

import com.dulsara.ewallet.ewalletapp.exception.ResourceNotFoundException;
import com.dulsara.ewallet.ewalletapp.transaction.service.TransactionService;
import com.dulsara.ewallet.ewalletapp.wallet.model.Wallet;
import com.dulsara.ewallet.ewalletapp.wallet.repository.WalletRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @Mock
    private  TransactionService transactionService;


    @Test
    @Order(1)
    void save() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(500D);
        wallet.setName("Test1");
        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);
        Wallet wallet1 = walletService.save(wallet);
        Assertions.assertNotNull(wallet1);
        Assertions.assertEquals(wallet,wallet1,"Checking that Equal Wallet should be there");


    }

    @Test
    @Order(2)
    void findAll() {

        Wallet wallet = new Wallet();
        wallet.setId(2L);
        wallet.setBalance(1000D);
        wallet.setName("Test2");
        Mockito.when(walletRepository.findAll()).thenReturn(Arrays.asList(wallet));
        List<Wallet> walletList = walletService.findAll();

       Assertions.assertNotNull(walletList);
       Assertions.assertEquals(1,walletList.size(),"check array size");
       assertEquals(true,walletList.stream().anyMatch((s)-> "Test2".equalsIgnoreCase(s.getName())),"check weather object are in find all");

    }

    @Test
    @Order(3)
    void findByWalletName() {

        String walletName = "Test3";
        Wallet wallet = new Wallet();
        wallet.setId(3L);
        wallet.setBalance(1000D);
        wallet.setName(walletName);
        Mockito.when(walletRepository.findWalletByName(walletName)).thenReturn(Optional.of(wallet));
        Optional<Wallet> walletOptional = walletService.findByWalletName(walletName);
        Assertions.assertNotNull(walletOptional.get());
        Assertions.assertEquals(walletName,walletOptional.get().getName()," check equality of Object taken by name");
    }

    @Test
    @Order(4)
    void findByWalletId() {
        String walletName = "Test4";
        Wallet wallet = new Wallet();
        wallet.setId(4L);
        wallet.setBalance(1000D);
        wallet.setName(walletName);
        Mockito.when(walletRepository.findById(4L)).thenReturn(Optional.of(wallet));
        Optional<Wallet> walletOptional = walletService.findByWalletId(4L);
        Assertions.assertNotNull(walletOptional.get());
        Assertions.assertEquals(4L,walletOptional.get().getId()," check equality of Object taken by Id");
    }

    @SneakyThrows
    @Test
    @Order(5)
    void addMoneyToWallet()  {
            Wallet wallet = new Wallet();
            wallet.setId(5L);
            wallet.setBalance(1000D);
            wallet.setName("Test5");
            Mockito.when(walletRepository.findById(5L)).thenReturn(Optional.of(wallet));
            Optional<Wallet> walletOptional = walletService.addMoneyToWallet(5L,100D);
            Assertions.assertEquals(1100D,walletOptional.get().getBalance()," check the added amount");

            Mockito.when(walletRepository.findById(100L)).thenReturn(Optional.ofNullable(null));

            Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
                Optional<Wallet> walletOptional1 = walletService.addMoneyToWallet(100L,100D);
            });

            String expectedMessage = "Wallet not found for this id :: 100";
            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
    }

    @SneakyThrows
    @Test
    @Order(6)
    void removeMoneyFromWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(6L);
        wallet.setBalance(1000D);
        wallet.setName("Test6");
        Mockito.when(walletRepository.findById(6L)).thenReturn(Optional.of(wallet));
        Optional<Wallet> walletOptional = walletService.removeMoneyFromWallet(6L,100D);
        Assertions.assertEquals(900D,walletOptional.get().getBalance()," check the remove amount");

        Mockito.when(walletRepository.findById(100L)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            Optional<Wallet> walletOptional1 = walletService.removeMoneyFromWallet(100L,100D);
        });
        String expectedMessage = "Wallet not found for this id :: 100";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

         exception = assertThrows(Exception.class, () -> {
            Optional<Wallet> walletOptional1 = walletService.removeMoneyFromWallet(6L,1100D);
        });

         expectedMessage = "Wallet : Test6 has not enough money to withdraw given amount : 1100";
         actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @SneakyThrows
    @Test
    @Order(7)
    void walletToWalletTranFer() {
        Wallet wallet = new Wallet();
        wallet.setId(7L);
        wallet.setBalance(1000D);
        wallet.setName("Test7");

        Wallet wallet1 = new Wallet();
        wallet1.setId(8L);
        wallet1.setBalance(2000D);
        wallet1.setName("Test8");

        Mockito.when(walletRepository.findById(7L)).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.findById(8L)).thenReturn(Optional.of(wallet1));
        Optional<Wallet> walletOptional = walletService.walletToWalletTranFer(7L,8L,100D);
        Assertions.assertEquals(900D,walletOptional.get().getBalance()," check the transfer amount");

        Mockito.when(walletRepository.findById(100L)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            Optional<Wallet> walletOptional1 = walletService.walletToWalletTranFer(100L,7L,100D);
        });
        String expectedMessage = "Wallet not found for these ids :: 7 & 100";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(Exception.class, () -> {
            Optional<Wallet> walletOptional1 = walletService.walletToWalletTranFer(7L,8L,1100D);
        });

        expectedMessage = "Wallet : Test7 has not enough money to transfer given amount : 1100";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void checkValidBalanceAvailable() {
        assertTrue(walletService.checkValidBalanceAvailable(100D,50D));
        assertTrue(walletService.checkValidBalanceAvailable(100D,100D));
        assertFalse(walletService.checkValidBalanceAvailable(100D,101D));
        assertFalse(walletService.checkValidBalanceAvailable(100D,100.01));
    }
}