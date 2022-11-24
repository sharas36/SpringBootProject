package com.springCoupon.controllers;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TokensList tokensList;

    private final ClientType clientType = ClientType.COMPANY;

    @SneakyThrows
    @PostMapping("/loginCompany")
    public String loginCompany(@RequestBody LoginInfo loginInfo) { //http://localhost:8080/company/loginCompany
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();

        int id = companyService.loginCheck(email, password);

        return TokensManager.loginToken(email, password, clientType, id);
    }

    @PostMapping("/addCoupon")
    @ResponseBody
    @SneakyThrows
    public void addCoupon(@RequestBody Coupon coupon, @RequestHeader String token) { // http://localhost:8080/admin/addCoupon
        TokensManager.tokenCheck(token, clientType);
        companyService.addCoupon(coupon, token);
    }

    @PostMapping("/updateCoupon") //http://localhost:8080/admin/updateCoupon
    @SneakyThrows
    public void updateCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        companyService.updateCoupon(coupon, token);
    }

    @DeleteMapping("/deleteCoupon/{id}") // http://localhost:8080/admin/deleteCoupon/{id}
    @ResponseBody
    @SneakyThrows
    public void deleteCoupon(@PathVariable int id, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        companyService.deletedCoupon(id);

    }

    @SneakyThrows
    @GetMapping("/getAllCoupons") // http://localhost:8080/company/getAllCoupons
    public ResponseEntity<?> getAllCoupons(@RequestHeader String token, @RequestParam int pageNum) {
        TokensManager.tokenCheck(token, clientType);
        Page<Coupon> res = companyService.getCouponsOfCompany(token, pageNum);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/getCompanyCouponByCategory/{categoryId}")
    public Page<Coupon> getAllCompanyCouponsByCategory(@PathVariable int categoryId, @RequestHeader String token, @RequestParam int pageNum) {  //http://localhost:8080/customers//getCompanyCouponByCategory/{categoryId}
        TokensManager.tokenCheck(token, clientType);
        return companyService.findByCompanyIdAndCategoryId(categoryId, token, pageNum);
    }

    @SneakyThrows
    @PostMapping("/getCouponsBetweenDates")
    public Page<Coupon> getAllCompanyCouponsBetweenDates(@RequestBody DateSelection dateSelection, @RequestHeader String token,
                                                         @RequestParam int pageNum) {  //http://localhost:8080/customers//getCompanyCouponByCategory/{categoryId}
        System.out.println(dateSelection.getDate1() + " " + dateSelection.getDate2());
        TokensManager.tokenCheck(token, clientType);
        return companyService.getCouponBetweenByDate(dateSelection.getDate1(), dateSelection.getDate2(), token, pageNum);
    }

    @SneakyThrows
    @GetMapping("/getCompanyCouponByMaxPrice/{maxPrice}")
    public Page<Coupon> getAllCompanyCouponsByMaxPrice(@PathVariable int maxPrice, @RequestHeader String token, @RequestParam int pageNum) {  //http://localhost:8080/customers//getCompanyCouponByMaxPrice/{maxPrice}
        TokensManager.tokenCheck(token, clientType);
        return companyService.getByMaxPrice(maxPrice, token, pageNum);
    }

    @SneakyThrows
    @GetMapping("/getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader String token) {  //http://localhost:8080/company/getCompanyDetails
        TokensManager.tokenCheck(token, clientType);
        return new ResponseEntity<>(companyService.getCompanyDetails(token), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/getOneCoupon/{couponId}")
    public ResponseEntity<?> getOneCoupon(@PathVariable int couponId, @RequestHeader String token) {  //http://localhost:8080/company/getOneCoupon
        TokensManager.tokenCheck(token, clientType);
        return new ResponseEntity<>(companyService.getOneCoupon(token, couponId), HttpStatus.OK);
    }

    @GetMapping("/getCompanyName")
    public String getCompanyName(@RequestHeader String token) {
        return companyService.getCompanyName(token);
    }


}
