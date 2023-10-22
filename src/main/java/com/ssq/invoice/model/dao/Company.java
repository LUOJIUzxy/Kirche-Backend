package com.ssq.invoice.model.dao;

import com.ssq.invoice.constant.ContactRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

import static javax.persistence.GenerationType.AUTO;

@Entity(name = "Company")
@Table(
        name = "company"
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Company {
    @Id
    @Column(name = "company_id")
    @SequenceGenerator(
            name = "company_sequence",
            sequenceName = "company_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = AUTO,
            generator = "company_sequence"
    )
    private Long companyId;

    @Column(
            name = "company_name",
            columnDefinition = "varchar(255)"
    )
    private String companyName;

    @Column(
            name = "address",
            columnDefinition = "varchar(255)"
    )
    private String address;

    @Column(
            name = "manager",
            columnDefinition = "varchar(255)"
    )
    private String manager;

    @Column(
            name = "tax_id",
            columnDefinition = "varchar(255)"
    )
    private String taxId;

    @Column(
            name = "vat_number",
            columnDefinition = "varchar(255)"
    )
    private String vatNumber;

    @Column(
            name = "HRB",
            columnDefinition = "varchar(255)"
    )
    private String HRB;

    @Email
    @Column(
            name = "email",
            columnDefinition = "varchar(255)"
    )
    private String email;

    @Column(
            name = "invoice_prefix",
            columnDefinition = "varchar(255)"
    )
    private String invoicePrefix;

    public Company(String companyName) {
        this.companyName = companyName;
    }
}
