package com.bourne.zinkworksatm.db;

import com.bourne.zinkworksatm.models.Account;
import com.bourne.zinkworksatm.models.Atm;
import com.bourne.zinkworksatm.repositories.AccountRepository;
import com.bourne.zinkworksatm.repositories.AtmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class DbInit {

    private final AtmRepository atmRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public DbInit(AtmRepository atmRepository, AccountRepository accountRepository) {
        this.atmRepository = atmRepository;
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    private void postConstruct() {
        atmRepository.save(new Atm());

        Account accountOne = new Account();
        accountOne.setAccountNumber(123456789L);
        accountOne.setPin(1234);
        accountOne.setBalance(BigDecimal.valueOf(800));
        accountOne.setOverdraft(BigDecimal.valueOf(200));
        accountRepository.save(accountOne);

        Account accountTwo = new Account();
        accountTwo.setAccountNumber(987654321L);
        accountTwo.setPin(4321);
        accountTwo.setBalance(BigDecimal.valueOf(1230));
        accountTwo.setOverdraft(BigDecimal.valueOf(150));
        accountRepository.save(accountTwo);
    }
}
