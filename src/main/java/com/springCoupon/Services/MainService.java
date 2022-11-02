package com.springCoupon.Services;


import com.springCoupon.Repositories.CompanyRepository;
import com.springCoupon.Repositories.CouponRepository;
import com.springCoupon.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class MainService {


    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CompanyRepository companyRepository;


}
