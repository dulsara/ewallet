package com.dulsara.ewallet.ewalletapp.wallet.service;

import com.dulsara.ewallet.ewalletapp.exception.ResourceNotFoundException;
import com.dulsara.ewallet.ewalletapp.transaction.model.TransactionTransferType;
import com.dulsara.ewallet.ewalletapp.transaction.model.TransactionType;
import com.dulsara.ewallet.ewalletapp.transaction.service.TransactionService;
import com.dulsara.ewallet.ewalletapp.wallet.model.Wallet;
import com.dulsara.ewallet.ewalletapp.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionService transactionService;


    public Wallet save (Wallet wallet){
        return walletRepository.save(wallet);
    }

    public List<Wallet> findAll () {
        return walletRepository.findAll();
    }

    public Optional<Wallet> findByWalletName (String name) {
        return walletRepository.findWalletByName(name);
    }

    public Optional<Wallet> findByWalletId (Long id) {
        return walletRepository.findById(id);
    }



    public Optional<Wallet> findById (Wallet wallet) {
        return walletRepository.findById(wallet.getId());
    }

    public Optional<Wallet> walletToWalletTranFer (Long walletId, Long transferWalletId, Double transferAmount) throws Exception {

        Optional<Wallet> transferWalletOptional = walletRepository.findById(transferWalletId);
        Optional<Wallet> walletOptional = walletRepository.findById(walletId);

        if (transferWalletOptional.isPresent() && walletOptional.isPresent())
        {
            if (checkValidBalanceAvailable(walletOptional.get().getBalance(), transferAmount)) {
                transferWalletOptional.get().setBalance(transferWalletOptional.get().getBalance() + transferAmount);
                walletOptional.get().setBalance(walletOptional.get().getBalance() - transferAmount);
                save(transferWalletOptional.get());
                save(walletOptional.get());
                transactionService.saveTransaction(transferAmount,walletId,transferWalletId, TransactionType.DEBIT.toString(), TransactionTransferType.W2W.toString(),"SUCCESS");
                return walletOptional;
            }
            else {
                throw new Exception("Wallet : "+ walletOptional.get().getName()+ " has not enough money to transfer given amount : " + transferAmount);
            }
        } else {
            throw new ResourceNotFoundException("Wallet not found for these ids :: " +transferWalletId +" & " + walletId );
        }
    }

    public Optional<Wallet> addMoneyToWallet (Long walletId, Double addedAmount)  {

        Optional<Wallet> walletOptional = walletRepository.findById(walletId);

        if (walletOptional.isPresent()) {
                walletOptional.get().setBalance(walletOptional.get().getBalance() + addedAmount);
                save(walletOptional.get());
                transactionService.saveTransaction(addedAmount,walletId,0L, TransactionType.CREDIT.toString(), TransactionTransferType.CASH.toString(),"SUCCESS");

            return walletOptional;
        }

        return null;
    }


    public Optional<Wallet> removeMoneyFromWallet (Long walletId, Double removeAmount) throws Exception {

        Optional<Wallet> walletOptional = walletRepository.findById(walletId);

        if (walletOptional.isPresent()) {

            if (checkValidBalanceAvailable(walletOptional.get().getBalance(),removeAmount)) {
                walletOptional.get().setBalance(walletOptional.get().getBalance() - removeAmount);
                save(walletOptional.get());
                transactionService.saveTransaction(removeAmount,walletId,-1L, TransactionType.DEBIT.toString(), TransactionTransferType.CASH.toString(),"SUCCESS");
                return walletOptional;
            }
            else {
                throw new Exception("Wallet : "+ walletOptional.get().getName()+ " has not enough money to withdraw given amount : " + removeAmount);

            }
        } else {
            throw new ResourceNotFoundException("Wallet not found for this id :: " + walletId );
        }

    }

    private boolean checkValidBalanceAvailable (Double currentBalance, Double deductedBalance) {
        return  currentBalance - deductedBalance >= 0.00;
    }

}
