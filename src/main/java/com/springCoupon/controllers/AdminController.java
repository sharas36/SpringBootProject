package com.springCoupon.controllers;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.*;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    private final ClientType clientType = ClientType.ADMINISTRATOR;

    @SneakyThrows
    @PostMapping("/loginAdmin")
    public String loginAdmin(@RequestBody LoginInfo loginDetails) { //http://localhost:8080/admin/loginAdmin
        String email = loginDetails.getEmail();
        String password = loginDetails.getPassword();
        adminService.adminLogin(email, password);

        return TokensManager.loginToken(email, password, clientType, 1);
    }

    @PostMapping("/addCompany")
    @SneakyThrows
    public void addCompany(@RequestBody Company company, @RequestHeader String token) { // http://localhost:8080/admin/addCompany
        TokensManager.tokenCheck(token, clientType);
        adminService.addCompany(company);


    }

    @GetMapping("/getAllCompanies") // http://localhost:8080/admin/getAllCompanies
    @SneakyThrows
    public ResponseEntity<?> getAllCompanies(@RequestHeader String token) {

        TokensManager.tokenCheck(token, clientType);
        List<Company> res = adminService.getAllCompany();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/updateCompany") //http://localhost:8080/admin/updateCompany
    public void updateCompany(@RequestBody Company company, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        System.out.println(company);
        System.out.println(company);
        adminService.updateCompanyDetails(company);
    }

    @DeleteMapping("/deleteCompany/{id}") // http://localhost:8080/admin/deleteCompany/{id}
    @ResponseBody
    @SneakyThrows
    public boolean deleteCompany(@PathVariable int id, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        return adminService.deleteCompany(id);

    }

    @GetMapping("/getOneCompany/{id}") // http://localhost:8080/admin/getOneCompany/{id}
    @SneakyThrows
    public ResponseEntity<?> getOneCompany(@PathVariable int id, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        Company res = adminService.getOneCompany(id);
        ResponseEntity<Company> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
        return responseWrapper;

    }

    @GetMapping("/getOneCustomer/{id}") // http://localhost:8080/admin/getOneCompany/{id}

    @SneakyThrows
    public ResponseEntity<?> getOneCustomer(@PathVariable int id, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        Customer res = adminService.getOneCustomer(id);
        ResponseEntity<Customer> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
        return responseWrapper;

    }

    @PostMapping("/addCustomer")
    @ResponseBody
    @SneakyThrows
    public void addCustomer(@RequestBody Customer customer, @RequestHeader String token) { // http://localhost:8080/admin/addCustomer
        TokensManager.tokenCheck(token, clientType);
        System.out.println(" i am here");
        adminService.addCustomer(customer);
    }

    @GetMapping("/getAllCustomers")
    @SneakyThrows
    public ResponseEntity<?> getAllCustomers(@RequestHeader String token) {   // http://localhost:8080/admin/getAllCustomers

        TokensManager.tokenCheck(token, clientType);

        List<Customer> res = adminService.getAllCustomer();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/updateCustomer") //http://localhost:8080/admin/updateCustomer
    @SneakyThrows
    public void updateCustomer(@RequestBody Customer customer, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);
        adminService.updateCustomerDetails(customer);
    }

    @DeleteMapping("/deleteCustomer/{id}") // http://localhost:8080/admin/deleteCustomer/{id}
    @ResponseBody
    @SneakyThrows
    public boolean deleteCustomer(@PathVariable int id, @RequestHeader String token) {
        TokensManager.tokenCheck(token, clientType);

        return adminService.deleteCustomer(id);

    }


}




