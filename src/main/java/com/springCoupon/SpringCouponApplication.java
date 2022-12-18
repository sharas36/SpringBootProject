package com.springCoupon;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class SpringCouponApplication {

    public static void main(String[] args) throws CouponSystemException {


        ConfigurableApplicationContext ctx = SpringApplication.run(SpringCouponApplication.class, args);


    }


}






