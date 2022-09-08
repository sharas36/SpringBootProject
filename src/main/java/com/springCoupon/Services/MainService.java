package com.springCoupon.Services;


import com.springCoupon.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class MainService {

    @Autowired
    com.springCoupon.Repositories.CouponRepository couponRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    com.springCoupon.Repositories.CompanyRepository companyRepository;


}
