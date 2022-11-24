package com.springCoupon.Services;


import com.springCoupon.Repositories.CompanyRepository;
import com.springCoupon.Repositories.CouponRepository;
import com.springCoupon.Repositories.CustomerRepository;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.ClientType;
import com.springCoupon.utilities.Token;
import com.springCoupon.utilities.TokensList;
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
