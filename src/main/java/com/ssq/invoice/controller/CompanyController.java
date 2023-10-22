package com.ssq.invoice.controller;


import com.ssq.invoice.constant.UserRole;
import com.ssq.invoice.model.dao.Company;
import com.ssq.invoice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('companies:read')")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('company:update')")
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @GetMapping("/{companyName}")
    public Company findCompanyByName(@PathVariable String companyName) {
        return companyService.findByName(companyName);
    }

    @GetMapping("/name")
    public Company findCompanyByNameAsPathParam(@PathVariable("companyName") String companyName){
        return companyService.findByName(companyName);
    }

}
