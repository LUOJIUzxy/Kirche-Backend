package com.ssq.invoice;

import com.ssq.invoice.model.AppUser;
import com.ssq.invoice.model.dao.Company;
import com.ssq.invoice.repo.AppUserRepository;
import com.ssq.invoice.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

import static com.ssq.invoice.constant.Role.ROLE_GROUP_ADMIN;

@SpringBootApplication
public class InvoiceApplication implements CommandLineRunner {
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    CompanyRepository companyRepository;

    public static void main(String[] args) {
        SpringApplication.run(InvoiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        companyRepository.deleteAll();
        Company company = new Company("eleap GmbH");
        companyRepository.save(company);

        appUserRepository.deleteAll();
        AppUser groupAdmin = new AppUser("admin", encoder.encode("password"), ROLE_GROUP_ADMIN.name());
        groupAdmin.setAuthorities(new ArrayList<>(Arrays.stream(ROLE_GROUP_ADMIN.getAuthorities()).toList()));
        groupAdmin.setAccessibleCompany(company);
        appUserRepository.save(groupAdmin);
    }
}
