package com.springCoupon.Services;


import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.Repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService extends MainService {

    public boolean adminLogin(String email, String password) {
        if (!(email.equals("admin@admin.com"))) {
            try {
                throw new CouponSystemException("This email is wrong");
            } catch (CouponSystemException e) {
                e.getException();
            }
        } else if (!(password.equals("admin"))) {

            try {
                throw new CouponSystemException("This password is wrong");
            } catch (CouponSystemException e) {
                e.getException();
            }
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

        if (companyRepository.findById(company.getCompanyId()).isEmpty()) {
            throw new CouponSystemException("This company name isn't  exist");
        } else if (companyRepository.findByEmail(company.getEmail()).get(0).getCompanyId() != company.getCompanyId()) {
            throw new CouponSystemException("This company email is already in use");
        }

        companyRepository.save(company);
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

        if (customerRepository.findById(customer.getCustomerId()).isEmpty()) {
            throw new CouponSystemException("This customer isn't exist");
        }
        if (!customerRepository.findByEmail(customer.getEmail()).isEmpty()
                && customerRepository.findByEmail(customer.getEmail()).get().getCustomerId() != customer.getCustomerId()) {
            throw new CouponSystemException("This customer email is already in use");
        }

        customerRepository.save(customer);
    }

    public void deleteCustomer(int customerId) throws CouponSystemException {

        if (customerRepository.findById(customerId).isEmpty()) {
            throw new CouponSystemException("this customer is not exist");
        }

        customerRepository.deleteById(customerId);

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




