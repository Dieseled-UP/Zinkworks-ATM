package com.bourne.zinkworksatm.services.impl;

import com.bourne.zinkworksatm.exceptions.BadPinException;
import com.bourne.zinkworksatm.exceptions.BalanceException;
import com.bourne.zinkworksatm.exceptions.InvalidInputException;
import com.bourne.zinkworksatm.exceptions.NotesException;
import com.bourne.zinkworksatm.models.Account;
import com.bourne.zinkworksatm.models.Atm;
import com.bourne.zinkworksatm.repositories.AccountRepository;
import com.bourne.zinkworksatm.repositories.AtmRepository;
import com.bourne.zinkworksatm.services.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class AtmServiceImpl implements AtmService {

    private final AtmRepository atmRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AtmServiceImpl(AtmRepository atmRepository, AccountRepository accountRepository) {
        this.atmRepository = atmRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public BigDecimal checkBalance(Long accountNumber, int pinNumber) throws BadPinException {
        Account account = retrieveAccount(accountNumber);

        if (account.getPin() == pinNumber) {
            return account.getBalance();
        } else {
            throw new BadPinException("Invalid pin number");
        }
    }

    @Override
    public HashMap<String, Object> makeWithdrawal(Long accountNumber, int pinNumber, int amount) throws BadPinException, InvalidInputException, NotesException, BalanceException {
        Account account = retrieveAccount(accountNumber);
        Atm atm = retrieveAtm();
        HashMap<String, Object> transaction = new HashMap<>();

        if (account.getPin() == pinNumber) {
            if (amount < 5 || amount % 5 != 0) {
                throw new InvalidInputException("Sorry withdrawal requests must be in multiples of 5");
            }

            if (atm.getRemainingCashTotal().compareTo(BigDecimal.valueOf(amount)) >= 0) {

                if (account.getBalancePlusOverdraft().compareTo(BigDecimal.valueOf(amount)) >= 0) {

                    if (BigDecimal.valueOf(amount).compareTo(account.getBalance()) > 0) {
                        account.setOverdraft(account.getOverdraft().subtract(BigDecimal.valueOf(amount).subtract(account.getBalance())));
                        account.setBalance(BigDecimal.valueOf(0));
                    } else {
                        account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(amount)));
                    }

                    atm.setRemainingCashTotal(atm.getRemainingCashTotal().subtract(BigDecimal.valueOf(amount)));
                    calculateNotes(atm, amount);
                    transaction.put("withdraw", amount);
                    transaction.put("remainingBalance", account.getBalance());
                    transaction.put("remainingOverdraft", account.getOverdraft());

                    atmRepository.saveAndFlush(atm);
                    accountRepository.saveAndFlush(account);

                    return transaction;
                } else {
                    throw new BalanceException("Sorry your account has insufficient funds");
                }
            } else {
                throw new BalanceException("Sorry this ATM cannot complete this transaction due to insufficient funds");
            }
        } else {
            throw new BadPinException("Invalid pin number");
        }
    }

    private Account retrieveAccount(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .stream()
                .findFirst()
                .orElse(null);
    }

    private Atm retrieveAtm() {
        return atmRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void calculateNotes(Atm atm, int amount) throws NotesException {
        if (amount < 50 || atm.getNumOfFifties() == 0) {
            calculateTwenties(atm, amount);
        } else {
            int numOfFifties = amount / 50;
            if (numOfFifties > atm.getNumOfFifties()) {
                amount = amount - atm.getNumOfFifties() * 50;
                atm.setNumOfFifties(0);
            } else {
                amount = amount - numOfFifties * 50;
                atm.setNumOfFifties(atm.getNumOfFifties() - numOfFifties);
            }
            if (amount > 0) {
                calculateTwenties(atm, amount);
            }
        }
    }

    public void calculateTwenties(Atm atm, int amount) throws NotesException {
        if (amount < 20 || atm.getNumOfTwenties() == 0) {
            calculateTens(atm, amount);
        } else {
            int numOfTwenties = amount / 20;
            if (numOfTwenties > atm.getNumOfTwenties()) {
                amount = amount - atm.getNumOfTwenties() * 20;
                atm.setNumOfTwenties(0);
            } else {
                amount = amount - numOfTwenties * 20;
                atm.setNumOfTwenties(atm.getNumOfTwenties() - numOfTwenties);
            }
            if (amount > 0) {
                calculateTens(atm, amount);
            }
        }
    }

    public void calculateTens(Atm atm, int amount) throws NotesException {
        if (amount < 10 || atm.getNumOfTens() == 0) {
            calculateFives(atm, amount);
        } else {
            int numOfTens = amount / 10;
            if (numOfTens > atm.getNumOfTens()) {
                amount = amount - atm.getNumOfTens() * 10;
                atm.setNumOfTens(0);
            } else {
                amount = amount - numOfTens * 10;
                atm.setNumOfTens(atm.getNumOfTens() - numOfTens);
            }
            if (amount > 0) {
                calculateFives(atm, amount);
            }
        }
    }

    public void calculateFives(Atm atm, int amount) throws NotesException {
        if (atm.getNumOfFives() == 0) {
            throw new NotesException("Please choose a value in a different multiple");
        } else {
            int numOfFives = amount / 5;
            if (numOfFives > atm.getNumOfFives()) {
                throw new NotesException("Please choose a value in a different multiple");
            } else {
                atm.setNumOfFives(atm.getNumOfFives() - numOfFives);
            }
        }
    }
}
