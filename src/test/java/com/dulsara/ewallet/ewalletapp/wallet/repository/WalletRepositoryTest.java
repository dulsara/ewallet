package com.dulsara.ewallet.ewalletapp.wallet.repository;

import com.dulsara.ewallet.ewalletapp.wallet.model.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    @Order(1)
    void save() {

        String walletName = "TestRepo1";
        Wallet wallet = new Wallet();
        wallet.setBalance(500D);
        wallet.setName(walletName);
        Wallet wallet1 =  walletRepository.save(wallet);

        Assertions.assertNotNull(wallet1.getId());
        Assertions.assertEquals(walletName, wallet1.getName(), "check Name  ");
    }

    @Test
    @Order(2)
    void findById() {

        Wallet wallet = new Wallet();
        String walletName = "TestRepo2";
        wallet.setBalance(1000D);
        wallet.setName(walletName);
        Wallet wallet1 =  walletRepository.save(wallet);
        Optional<Wallet> walletOptional = walletRepository.findById(wallet1.getId());
        Assertions.assertNotNull(walletOptional.get());
        Assertions.assertEquals(walletName,walletOptional.get().getName()," check equality of Object taken by name");

    }

    @Test
    @Order(3)
    void findAll() {

        Wallet wallet = new Wallet();
        String walletName = "TestRepo3";
        wallet.setBalance(1000D);
        wallet.setName(walletName);
        walletRepository.save(wallet);
        List<Wallet> walletList = walletRepository.findAll();
        Assertions.assertNotNull(walletList);
        Assertions.assertEquals(1,walletList.size(),"check array size");
        assertEquals(true,walletList.stream().anyMatch((s)-> walletName.equalsIgnoreCase(s.getName())),"check weather object are in find all");

    }

    @Test
    @Order(4)
    void findWalletByName() {
        String walletName = "TestRepo4";
        Wallet wallet = new Wallet();
        wallet.setBalance(500D);
        wallet.setName(walletName);
        walletRepository.save(wallet);
        Optional<Wallet> walletOptional = walletRepository.findWalletByName(walletName);
        Assertions.assertNotNull(walletOptional.get());
        Assertions.assertEquals(walletName,walletOptional.get().getName()," check equality of Object taken by name");
    }
}