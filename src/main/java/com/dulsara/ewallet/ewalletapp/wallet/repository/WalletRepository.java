package com.dulsara.ewallet.ewalletapp.wallet.repository;

import com.dulsara.ewallet.ewalletapp.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findWalletByName(String name);
}
