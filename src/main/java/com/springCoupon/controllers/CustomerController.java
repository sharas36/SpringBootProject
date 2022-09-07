package com.springCoupon.controllers;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @SneakyThrows
    @PostMapping
    public void loginCustomer(@RequestBody Customer customer) { //http://localhost:8080/customers/updateCompany
        customerService.loginCustomer(customer.getEmail(), customer.getPassword());
    }

    @SneakyThrows
    @PostMapping("/addCustomerPurchase/{couponId}")
    public void addPurchaseByCustomer(@PathVariable int couponId) {
        customerService.addPurchase(couponId);
    }

    @GetMapping("/getAllCustomerCoupon")
    public ResponseEntity<?> getAllCustomersCoupons() {
        return new ResponseEntity<>(customerService.getAllCustomersCoupons(), HttpStatus.OK);
    }

    @GetMapping("/getCustomerCouponByCategory/{categoryId}")
    public List<Coupon> getAllCustomersCouponsByCategory(@PathVariable int categoryId) {
        return customerService.getAllCustomersCouponsByCategory(categoryId);
    }

    @GetMapping("/getCustomerCouponByMaxPrice/{maxPrice}")
    public List<Coupon> getAllCustomersCouponsByMaxPrice(@PathVariable int maxPrice) {
        return customerService.getAllCustomersCouponsByMaxPrice(maxPrice);
    }

    @PostMapping("addCouponPurchase/{couponId}")
    public void saveByCoupon(@PathVariable int couponId) {
        customerService.saveByCoupon(couponId);
    }


}
