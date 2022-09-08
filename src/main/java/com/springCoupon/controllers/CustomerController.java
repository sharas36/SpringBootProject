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
@ResponseStatus(value = HttpStatus.OK)
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @SneakyThrows
    @PostMapping("/loginCustomer")
    public void loginCustomer(@RequestBody Customer customer) { //http://localhost:8080/customers/loginCustomer
        customerService.loginCustomer(customer.getEmail(), customer.getPassword());
    }

    @SneakyThrows
    @PostMapping("/addCustomerPurchase/{couponId}")
    public void addPurchaseByCustomer(@PathVariable int couponId) {  //http://localhost:8080/customers/addCustomerPurchase/{couponId}
        customerService.addPurchase(couponId);
    }

    @GetMapping("/getAllCustomerCoupon")
    public ResponseEntity<?> getAllCustomersCoupons() {  //http://localhost:8080/customers/getAllCustomerCoupon
        List<Coupon> coupons = customerService.getAllCustomersCoupons();
        for (Coupon coupon : coupons) {
            System.out.println(coupon);
        }
       return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/getCustomerCouponByCategory/{categoryId}")
    public List<Coupon> getAllCustomersCouponsByCategory(@PathVariable int categoryId) {  //http://localhost:8080/customers//getCustomerCouponByCategory/{categoryId}=
        return customerService.getAllCustomersCouponsByCategory(categoryId);
    }

    @GetMapping("/getCustomerCouponByMaxPrice/{maxPrice}")
    public List<Coupon> getAllCustomersCouponsByMaxPrice(@PathVariable int maxPrice) {  //http://localhost:8080/customers//getCustomerCouponByMaxPrice/{maxPrice}
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
}
