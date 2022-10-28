package com.springCoupon.controllers;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.utilities.Admin;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @SneakyThrows
    @PostMapping("/loginCompany")
    public void loginCustomer(@RequestBody Company company) { //http://localhost:8080/company/loginCompany
        companyService.loginCheck(company.getEmail(), company.getPassword());
    }

    @PostMapping("/addCoupon")
    @ResponseBody
    @SneakyThrows
    public void addCoupon(@RequestBody Coupon coupon) { // http://localhost:8080/admin/addCoupon
        companyService.addCoupon(coupon);
    }

    @PostMapping("/updateCoupon") //http://localhost:8080/admin/updateCoupon
    @SneakyThrows
    public void updateCoupon(@RequestBody Coupon coupon) {

        companyService.updateCoupon(coupon);
    }

    @DeleteMapping("/deleteCoupon/{id}") // http://localhost:8080/admin/deleteCoupon/{id}
    @ResponseBody
    @SneakyThrows
    public void deleteCoupon(@PathVariable int id) {

        companyService.deletedCoupon(id);

    }

    @GetMapping("/getAllCoupons") // http://localhost:8080/company/getAllCoupons
    public ResponseEntity<?> getAllCoupons() {
        List<Coupon> res = companyService.getCouponsOfCompany();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/getCompanyCouponByCategory/{categoryId}")
    public List<Coupon> getAllCompanyCouponsByCategory(@PathVariable int categoryId) {  //http://localhost:8080/customers//getCompanyCouponByCategory/{categoryId}
        return companyService.findByCompanyIdAndCategoryId(categoryId);
    }

    @GetMapping("/getCompanyCouponByMaxPrice/{maxPrice}")
    public List<Coupon> getAllCompanyCouponsByMaxPrice(@PathVariable int maxPrice) {  //http://localhost:8080/customers//getCompanyCouponByMaxPrice/{maxPrice}
        return companyService.getByMaxPrice(maxPrice);
    }

    @GetMapping("/getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails() {  //http://localhost:8080/company/getCompanyDetails
        return new ResponseEntity<>(companyService.getCompanyDetails(), HttpStatus.OK);
    }
    @ResponseBody
    @PostMapping("/hello")
    public Customer hello(@RequestHeader String token) {  //http://localhost:8080/company/hello
        System.out.println(token);
        return new Customer();
    } //http://localhost:8080/company/hello
}
