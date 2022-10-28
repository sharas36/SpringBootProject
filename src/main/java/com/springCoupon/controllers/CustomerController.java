package com.springCoupon.controllers;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @SneakyThrows
    @PostMapping("/loginCustomer")
    public boolean loginCustomer(@RequestBody Customer customer) { //http://localhost:8080/customers/loginCustomer
        return customerService.loginCustomer(customer.getEmail(), customer.getPassword());
    }

    @SneakyThrows
    @PostMapping("/addCustomerPurchase/{couponId}")
    public void addPurchaseByCustomer(@PathVariable int couponId, @RequestHeader String token) {  //http://localhost:8080/customers/addCustomerPurchase/{couponId}
        System.out.println("is working!");
        customerService.addPurchase(couponId);
    }

    @GetMapping("/getAllCustomerCoupon")
    public ResponseEntity<?> getAllCustomersCoupons(@RequestHeader String token, @RequestParam int page, @RequestParam int size) {  //http://localhost:8080/customers/getAllCustomerCoupon
        List<Coupon> coupons = customerService.getAllCustomersCoupons();

        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/getCustomerCouponByCategory/{categoryId}")
    public List<Coupon> getAllCustomersCouponsByCategory(@PathVariable int categoryId) {  //http://localhost:8080/customers//getCustomerCouponByCategory/{categoryId}=
        return customerService.getAllCustomersCouponsByCategory(categoryId);
    }

    @GetMapping("/getCustomerCouponByMaxPrice/{maxPrice}")
    public List<Coupon> getAllCustomersCouponsByMaxPrice(@PathVariable int maxPrice, @RequestParam int page, @RequestParam int size) {  //http://localhost:8080/customers//getCustomerCouponByMaxPrice/{maxPrice}
        return customerService.getAllCustomersCouponsByMaxPrice(maxPrice);
    }

    @PostMapping("addCouponPurchase/{couponId}")
    public void saveByCoupon(@PathVariable int couponId) {  //http://localhost:8080/customers/aaddCouponPurchase/{couponId}
        customerService.saveByCoupon(couponId);
    }

    @GetMapping("/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails() {  //http://localhost:8080/customers/getCustomerDetails
        return new ResponseEntity<>(customerService.getCustomerDetails(), HttpStatus.OK);
    }

    @GetMapping("/getCouponById/{couponId}")
    public ResponseEntity<?> getCouponById(@PathVariable int couponId){
        return new ResponseEntity<>(customerService.getCouponById(couponId), HttpStatus.OK);
    }
}

