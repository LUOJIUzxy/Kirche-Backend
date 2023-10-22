package com.ssq.invoice.repo;

import com.ssq.invoice.model.dao.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.companyName = ?1")
    Company findByName(String name);
//    AppUser findAppUserByUsername(String username);
//
//    AppUser findAppUserByEmail(String email);
//
//    @Query("SELECT DISTINCT u FROM AppUser u WHERE u.accessibleCompany.companyCode = ?1")
//
//    List<AppUser> findAppUserByAccessableCompanyCode(String companyCode);
//
//    List<AppUser> findAppUsersByRole(String role);
}
