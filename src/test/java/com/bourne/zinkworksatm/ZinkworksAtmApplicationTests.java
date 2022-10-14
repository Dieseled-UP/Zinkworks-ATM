package com.bourne.zinkworksatm;

import com.bourne.zinkworksatm.models.Account;
import com.bourne.zinkworksatm.models.Atm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
class ZinkworksAtmApplicationTests {

    @Autowired
    private EntityManager entityManager;

    @Test
    void verifyAccountCanBeSaved() {
        final Account account = new Account();
        account.setAccountNumber(123456789L);
        account.setPin(1234);
        account.setBalance(BigDecimal.valueOf(800));
        account.setOverdraft(BigDecimal.valueOf(200));

        entityManager.persist(account);

        final TypedQuery<Account> results = entityManager
                .createQuery("SELECT u FROM Account u", Account.class);

        final List<Account> resultList = results.getResultList();

        Assertions.assertThat(resultList)
                .hasSize(1)
                .first()
                .isEqualTo(account);
    }

    @Test
    void verifyAtmCanBeSaved() {
        final Atm atm = new Atm();

        entityManager.persist(atm);

        final TypedQuery<Atm> results = entityManager
                .createQuery("SELECT a FROM Atm a", Atm.class);

        final List<Atm> resultList = results.getResultList();

        Assertions.assertThat(resultList)
                .hasSize(1)
                .first()
                .isEqualTo(atm);
    }

}
