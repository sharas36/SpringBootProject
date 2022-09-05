package com.springCoupon.controllers;

import com.springCoupon.Entities.Company;
import com.springCoupon.Services.AdminService;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/addCompany")
    @ResponseBody
    public ResponseEntity<?> addCompany(@RequestBody Company company) { // http://localhost:8080/admin/addCompany
        System.out.println("Got: " + company);
        try {
            adminService.addCompany(company);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<String>("Bad request, there is a problem with the server", HttpStatus.BAD_REQUEST);
        } catch (CouponSystemException e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCompanies") // http://localhost:8080/admin/getCompanies
    @ResponseBody
    public ResponseEntity<?> getCompanies() throws Exception {
        List<Company> res = adminService.getAllCompany();
        System.out.println("getCompanies: ");
        ResponseEntity<List<Company>> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
        return responseWrapper;
    }


}
