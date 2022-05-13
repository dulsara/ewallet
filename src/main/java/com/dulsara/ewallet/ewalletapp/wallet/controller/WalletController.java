package com.dulsara.ewallet.ewalletapp.wallet.controller;


import com.dulsara.ewallet.ewalletapp.wallet.model.Wallet;
import com.dulsara.ewallet.ewalletapp.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api/wallets")
@RequiredArgsConstructor

public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public Wallet save(@RequestBody Wallet wallet ) {
        return walletService.save(wallet);
    }

    @GetMapping
    public List<Wallet> findAllWallet() {
        return walletService.findAll();
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Wallet> findByWalletName(@PathVariable String name) {
        return ResponseEntity.ok().body(walletService.findByWalletName(name).get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> findByWalletName(@PathVariable Long id) {
        return ResponseEntity.ok().body(walletService.findByWalletId(id).get());
    }

    @PostMapping("top-up/{balance}/{id}")
    public ResponseEntity<Wallet> topUpWallet(@PathVariable Double balance,@PathVariable Long id) throws Exception {
       return ResponseEntity.ok().body(walletService.addMoneyToWallet(id,balance).get());
    }

    @PostMapping("debit/{balance}/{id}")
    public ResponseEntity<Wallet> debitWallet( @PathVariable Double balance,@PathVariable Long id) throws Exception {

        return ResponseEntity.ok().body(walletService.removeMoneyFromWallet(id, balance).get());
    }

    @PostMapping("transfer/{balance}/{from}/{to}")
    public ResponseEntity<Wallet> transferWalletMoney(@PathVariable Double balance, @PathVariable Long from, @PathVariable Long to) throws Exception {
            return ResponseEntity.ok().body(walletService.walletToWalletTranFer(from, to, balance).get());
    }
}
