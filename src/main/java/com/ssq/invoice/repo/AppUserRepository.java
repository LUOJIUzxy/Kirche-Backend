package com.ssq.invoice.repo;

import com.ssq.invoice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.username = ?1")
    AppUser findAppUserByUsername(String username);

    AppUser findAppUserByEmail(String email);

//    @Query("SELECT DISTINCT u FROM AppUser u WHERE u.accessibleCompany.companyCode = ?1")
//
//    List<AppUser> findAppUserByAccessableCompanyCode(String companyCode);

    List<AppUser> findAppUsersByRole(String role);
}
