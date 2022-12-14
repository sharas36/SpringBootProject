package com.springCoupon.Services;


import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.Repositories.CouponRepository;
import com.springCoupon.utilities.TokensManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class AdminService extends MainService {
    private static final int pageSize = 8;

    public boolean adminLogin(String email, String password) throws CouponSystemException {
        if (!(email.equals("admin@admin.com"))) {
            throw new CouponSystemException("This email is wrong");

        } else if (!(password.equals("admin12345"))) {
            throw new CouponSystemException("This password is wrong");
        }
        return true;
    }

    public void addCompany(Company company) throws CouponSystemException {

        if (isEmailExist(company.getEmail())) {
            throw new CouponSystemException("This company email already exist");
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

    public boolean deleteCompany(int companyId) throws SQLException, CouponSystemException {
        if (companyRepository.findById(companyId).isEmpty()) {
            throw new CouponSystemException("This company isn't exist");
        }
        Company company = companyRepository.findById(companyId).get();
        List<Coupon> couponList = couponRepository.findByCompany(company);
        System.out.println(couponList.toString());
        companyRepository.deleteById(companyId);
        for (Coupon coupon : couponList) {
            couponRepository.deleteCoupon(coupon.getCouponId());

        }
        for (Coupon coupon : couponList) {

            couponRepository.deletePurchasesOfCustomer(coupon.getCouponId());
        }

        return companyRepository.findById(companyId).isPresent();
    }

    public Page<Company> getAllCompany(int pageNum) {
        return companyRepository.findAll(PageRequest.of(pageNum,pageSize));
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
        System.out.println(customer);
        customerRepository.save(customer);
    }

    public boolean deleteCustomer(int customerId) throws CouponSystemException {
        couponRepository.deleteCustomerPurchaseByCustomerId(customerId);
        customerRepository.deleteCustomer(customerId);
        return customerRepository.findById(customerId).isPresent();
    }

    public Page<Customer> getAllCustomer(int pageNum) {
        return customerRepository.findAll(PageRequest.of(pageNum,pageSize));
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

    public void addPurchase(int couponId, int customerId) throws CouponSystemException {

//        int customerId = TokensManager.getIdFromToken(token);
        if (!customerRepository.findById(customerId).isPresent() || !couponRepository.findById(couponId).isPresent()) {
            try {
                throw new CouponSystemException("coupon or customer are not exist");
            } catch (CouponSystemException e) {
                e.printStackTrace();
            }
        }

        Customer customer = customerRepository.getById(customerId);
        Coupon coupon = couponRepository.getById(couponId);

        if (coupon.getAmount() <= 0) {

            throw new CouponSystemException("this coupon is sold out");
        }
        ;

        customer.addCoupon(coupon);
        couponRepository.findById(couponId).get().setAmount(couponRepository.findById(couponId).get().getAmount() - 1);
        couponRepository.save(couponRepository.getById(couponId));
        customerRepository.save(customer);
    }

    public Coupon addCoupon(Coupon coupon, int companyId) throws CouponSystemException {

        if (!couponRepository.findByCouponNameAndCompany(coupon.getCouponName(), companyRepository.getById(companyId)).isEmpty()) {
            throw new CouponSystemException("the same name belongs to the same company");
        }
        coupon.setCompany(companyRepository.getById(companyId));
        return couponRepository.save(coupon);
    }

    public Page<Customer> getByPage(int pageNum) {
        return customerRepository.findAll(PageRequest.of(pageNum, 10));
    }

    public Page<Coupon> getByCategory(int categoryId, int pageNum) {
        return couponRepository.findByCategoryId(categoryId, PageRequest.of(pageNum, 10));
    }


}




