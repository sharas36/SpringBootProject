package com.springCoupon.controllers;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.*;
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

    @Autowired
    private TokensList tokensList;

    private final ClientType clientType = ClientType.CUSTOMER;

    @SneakyThrows
    @PostMapping("/loginCustomer")
    public String loginCustomer(@RequestBody LoginInfo loginInfo) { //http://localhost:8080/customers/loginCustomer
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();
        int id = customerService.loginCustomer(email, password);
        return TokensManager.loginToken(email, password, clientType, id);
    }

    @SneakyThrows
    @PostMapping("/addCustomerPurchase/{couponId}")
    public void addPurchaseByCustomer(@PathVariable int couponId, @RequestHeader String token) {  //http://localhost:8080/customers/addCustomerPurchase/{couponId}
        TokensManager.tokenCheck(token, clientType);
        customerService.addPurchase(couponId, token);
    }

    @SneakyThrows
    @GetMapping("/getAllCustomerCoupon")
    public ResponseEntity<?> getAllCustomersCoupons(@RequestHeader String token) {  //http://localhost:8080/customers/getAllCustomerCoupon
//      TokensManager.tokenCheck();(token, clientType);
        List<Coupon> coupons = customerService.getAllCustomersCoupons(token);

        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/getCustomerCouponByCategory/{categoryId}")
    public List<Coupon> getAllCustomersCouponsByCategory(@PathVariable int categoryId, @RequestHeader String token) {  //http://localhost:8080/customers//getCustomerCouponByCategory/{categoryId}=
        TokensManager.tokenCheck(token, clientType);
        return customerService.getAllCustomersCouponsByCategory(categoryId, token);
    }


    @SneakyThrows
    @GetMapping("/getCustomerCouponByMaxPrice/{maxPrice}")
    public List<Coupon> getAllCustomersCouponsByMaxPrice(@PathVariable int maxPrice, @RequestHeader String token) {  //http://localhost:8080/customers//getCustomerCouponByMaxPrice/{maxPrice}
        TokensManager.tokenCheck(token, clientType);
        return customerService.getAllCustomersCouponsByMaxPrice(maxPrice, token);
    }

    @SneakyThrows
    @PostMapping("addCouponPurchase/{couponId}")
    public void saveByCoupon(@PathVariable int couponId, @RequestHeader String token) {  //http://localhost:8080/customers/aaddCouponPurchase/{couponId}
        TokensManager.tokenCheck(token, clientType);
        customerService.addPurchase(couponId, token);
    }

    @SneakyThrows
    @GetMapping("/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader String token) {  //http://localhost:8080/customers/getCustomerDetails
        TokensManager.tokenCheck(token, clientType);
        return new ResponseEntity<>(customerService.getCustomerDetails(token), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/getCouponById/{couponId}")
    public ResponseEntity<?> getCouponById(@PathVariable int couponId, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        return new ResponseEntity<>(customerService.getCouponById(couponId), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/getFirstAndLastName")
    public String getFirstAndLastName(@RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        return customerService.getFirstAndLastName(token);
    }

    @SneakyThrows
    @GetMapping("/getAllCoupons")
    public List<Coupon> getAllCoupon(@RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        return customerService.getAllCoupon();
    }
}

