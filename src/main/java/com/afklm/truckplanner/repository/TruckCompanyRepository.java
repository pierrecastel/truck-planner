package com.afklm.truckplanner.repository;

import com.afklm.truckplanner.domain.TruckCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TruckCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TruckCompanyRepository extends JpaRepository<TruckCompany, Long> {

}
