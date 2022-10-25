package com.springCoupon.Services;


import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.Repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService extends MainService {

    public boolean adminLogin(String email, String password) throws CouponSystemException {
        if (!(email.equals("admin@admin.com"))) {
            throw new CouponSystemException("This email is wrong");

        } else if (!(password.equals("admin"))) {
            throw new CouponSystemException("This password is wrong");
        }
        return true;
    }

    public void addCompany(Company company) throws SQLException, CouponSystemException {

        if (isEmailExist(company.getEmail())) {
            throw new CouponSystemException("This company already exist");
        } else if (!companyRepository.findByCompanyName(company.getCompanyName()).isEmpty()) {
            throw new CouponSystemException("This company name already exist");
        }
        companyRepository.save(company);
    }

    public void updateCompanyDetails(Company company) throws CouponSystemException {

        Company companyFromDb = companyRepository.getById(company.getCompanyId());
        companyFromDb.setCompanyName(company.getCompanyName());
        companyFromDb.setPassword(company.getPassword());
        companyFromDb.setEmail(company.getEmail());

        if (companyRepository.findByEmail(companyFromDb.getEmail()).isPresent()) {
            if (companyRepository.findByEmail(companyFromDb.getEmail()).get().getCompanyId() != company.getCompanyId()) {
                throw new CouponSystemException("This company email is already in use");
            }
        }

        companyRepository.save(companyFromDb);

    }

    public Company updateCompanyInfo(String email, String password, int companyId) throws CouponSystemException {

        if (companyRepository.findByEmail(email).isPresent()) {
            throw new CouponSystemException("this email already in use in our system");
        }

        Company company = companyRepository.getById(companyId);
        company.setEmail(email);
        return companyRepository.save(company);
    }

    public void deleteCompany(int companyId) throws SQLException, CouponSystemException {
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new CouponSystemException("This company isn't exist");
        }
        companyRepository.deleteById(companyId);
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Company getOneCompany(int companyId) throws CouponSystemException {
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new CouponSystemException("This company isn't exist");
        }
        return companyRepository.findById(companyId).get();
    }

    public void addCustomer(Customer customer) throws CouponSystemException {

        if (!customerRepository.findByEmail(customer.getEmail()).isEmpty()) {
            throw new CouponSystemException("this email belong to another  customer");
        }
        customerRepository.save(customer);
    }

    public void updateCustomerDetails(Customer customer) throws CouponSystemException {


        if (!customerRepository.findByEmail(customer.getEmail()).isEmpty()
                && customerRepository.findByEmail(customer.getEmail()).get().getCustomerId() != customer.getCustomerId()) {
            throw new CouponSystemException("This customer email is already in use");
        }

        customerRepository.save(customer);
    }

    public void deleteCustomer(int customerId) throws CouponSystemException {

        Customer customer = customerRepository.getById(customerId);
        customerRepository.deleteCustomer(customerId);

    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        if (!customerRepository.findById(customerId).isPresent()) {
            throw new CouponSystemException("this customer is not exist");
        }
        return customerRepository.findById(customerId).get();
    }

    public boolean isEmailExist(String email) {
        return !companyRepository.findByEmail(email).isEmpty();
    }

}




