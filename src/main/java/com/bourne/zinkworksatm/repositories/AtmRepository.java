package com.bourne.zinkworksatm.repositories;

import com.bourne.zinkworksatm.models.Atm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtmRepository extends JpaRepository<Atm, Long> {

}
