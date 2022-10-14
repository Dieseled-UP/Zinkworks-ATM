package com.bourne.zinkworksatm.repositories;

import com.bourne.zinkworksatm.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
