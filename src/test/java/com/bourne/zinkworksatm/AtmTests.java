package com.bourne.zinkworksatm;

import com.bourne.zinkworksatm.models.Atm;
import com.bourne.zinkworksatm.repositories.AtmRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
class AtmTests {

    @Autowired
    private AtmRepository atmRepository;

    @Before
    public void setUp() {
        atmRepository.deleteAll();
    }

    @Test
    void atmCheckingAtmStartingValues() {
        final Atm atm = new Atm();

        atmRepository.save(atm);

        Assertions.assertThat(atmRepository.findAll())
                .hasSize(1)
                .first()
                .isEqualTo(atm);
    }
}
