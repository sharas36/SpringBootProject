package com.springCoupon.controllers;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.Admin;
import com.springCoupon.utilities.ClientType;
import com.springCoupon.utilities.Token;
import com.springCoupon.utilities.TokensList;
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

    @Autowired
    private TokensList tokensList;

    private final ClientType clientType = ClientType.COMPANY;
    private final int pageSize = 10;

    @SneakyThrows
    @PostMapping("/loginCompany")
    public String loginCustomer(@RequestBody String email, @RequestBody String password) { //http://localhost:8080/company/loginCompany
        int id = companyService.loginCheck(email, password);

        return companyService.loginToken(email, password, clientType, id);
    }

    @PostMapping("/addCoupon")
    @ResponseBody
    @SneakyThrows
    public void addCoupon(@RequestBody Coupon coupon, @RequestHeader String token) { // http://localhost:8080/admin/addCoupon
        companyService.tokenCheck(token, clientType);
        companyService.addCoupon(coupon, token);
    }

    @PostMapping("/updateCoupon") //http://localhost:8080/admin/updateCoupon
    @SneakyThrows
    public void updateCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
        companyService.tokenCheck(token, clientType);
        companyService.updateCoupon(coupon);
    }

    @DeleteMapping("/deleteCoupon/{id}") // http://localhost:8080/admin/deleteCoupon/{id}
    @ResponseBody
    @SneakyThrows
    public void deleteCoupon(@PathVariable int id, @RequestHeader String token) {
        companyService.tokenCheck(token, clientType);
        companyService.deletedCoupon(id);

    }

    @SneakyThrows
    @GetMapping("/getAllCoupons") // http://localhost:8080/company/getAllCoupons
    public ResponseEntity<?> getAllCoupons(@RequestHeader String token, @RequestParam int pageNum) {
        org.springframework.data.domain.Pageable pageable = (org.springframework.data.domain.Pageable) PageRequest.of(pageNum, pageSize);
        companyService.tokenCheck(token, clientType);
        Page<Coupon> res = companyService.getCouponsOfCompany(token, pageable);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/getCompanyCouponByCategory/{categoryId}")
    public Page<Coupon> getAllCompanyCouponsByCategory(@PathVariable int categoryId, @RequestHeader String token, @RequestParam int pageNum) {  //http://localhost:8080/customers//getCompanyCouponByCategory/{categoryId}
        org.springframework.data.domain.Pageable pageable = (org.springframework.data.domain.Pageable) PageRequest.of(pageNum, pageSize);
        companyService.tokenCheck(token, clientType);
        return companyService.findByCompanyIdAndCategoryId(categoryId, token, pageable);
    }

    @SneakyThrows
    @GetMapping("/getCompanyCouponByMaxPrice/{maxPrice}")
    public List<Coupon> getAllCompanyCouponsByMaxPrice(@PathVariable int maxPrice, @RequestHeader String token, @RequestParam int pageNum) {  //http://localhost:8080/company//getCompanyCouponByMaxPrice/{maxPrice}
        org.springframework.data.domain.Pageable pageable = (org.springframework.data.domain.Pageable) PageRequest.of(pageNum, pageSize);
        companyService.tokenCheck(token, clientType);
        return companyService.getByMaxPrice(maxPrice, token, pageable);
    }
    @SneakyThrows
    @GetMapping("/getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader String token) {  //http://localhost:8080/company/getCompanyDetails
        companyService.tokenCheck(token, clientType);
        return new ResponseEntity<>(companyService.getCompanyDetails(token), HttpStatus.OK);
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/hello")
    public Customer hello(@RequestHeader String token) {  //http://localhost:8080/company/hello
        System.out.println(token);
        return new Customer();
    } //http://localhost:8080/company/hello

    @SneakyThrows
    @GetMapping("/countCouponsByCategory/{categoryId}")
    public int countCouponsByCategory(@PathVariable int categoryId, @RequestHeader String token) {  //http://localhost:8080/company/countCouponsByCategory/{categoryId}
        companyService.tokenCheck(token, clientType);
        return companyService.countCouponsByCategory(categoryId, 8);
    }
}
