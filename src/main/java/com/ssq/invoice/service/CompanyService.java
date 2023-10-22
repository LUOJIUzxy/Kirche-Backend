package com.ssq.invoice.service;

import com.ssq.invoice.model.dao.Company;
import com.ssq.invoice.repo.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company){
        companyRepository.save(company);
        return company;
    }

    public Company findByName(String name) {
        return companyRepository.findByName(name);
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
}
