package com.springCoupon.controllers;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.Admin;
import com.springCoupon.utilities.ClientType;
import com.springCoupon.utilities.Token;
import com.springCoupon.utilities.TokensList;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
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

    @Autowired
    private TokensList tokensList;

    private final ClientType clientType = ClientType.ADMINISTRATOR;

    @SneakyThrows
    @PostMapping("/loginAdmin")
    public String loginAdmin(@RequestBody String email, @RequestBody String password) { //http://localhost:8080/admin/loginAdmin
        adminService.adminLogin(email, password);

        return adminService.loginToken(email, password, clientType, 1);
    }

    @PostMapping("/addCompany")
    @SneakyThrows
    public void addCompany(@RequestBody Company company, @RequestHeader String token) { // http://localhost:8080/admin/addCompany
        adminService.tokenCheck(token, clientType);
        System.out.println("your token is:" + token);
        System.out.println("Got: " + company);
        adminService.addCompany(company);


    }

    @GetMapping("/getAllCompanies") // http://localhost:8080/admin/getAllCompanies
    @SneakyThrows
    public ResponseEntity<?> getAllCompanies(@RequestHeader String token) {
        adminService.tokenCheck(token, clientType);
        List<Company> res = adminService.getAllCompany();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/updateCompany") //http://localhost:8080/admin/updateCompany
    public void updateCompany(@RequestBody Company company, @RequestHeader String token) {
        adminService.tokenCheck(token, clientType);
        System.out.println(company);
        adminService.updateCompanyDetails(company);
    }

    @DeleteMapping("/deleteCompany/{id}") // http://localhost:8080/admin/deleteCompany/{id}
    @ResponseBody
    @SneakyThrows
    public void deleteCompany(@PathVariable int id, @RequestHeader String token) {
        adminService.tokenCheck(token, clientType);
        adminService.deleteCompany(id);
        System.out.println("deleteCompany: " + id);
    }

    @GetMapping("/getOneCompany/{id}") // http://localhost:8080/admin/getOneCompany/{id}
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<?> getOneCompany(@PathVariable int id, @RequestHeader String token) {
        adminService.tokenCheck(token, clientType);
        Company res = adminService.getOneCompany(id);
        ResponseEntity<Company> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
        return responseWrapper;

    }

    @PostMapping("/addCustomer")
    @ResponseBody
    @SneakyThrows
    public void addCustomer(@RequestBody Customer customer, @RequestHeader String token) { // http://localhost:8080/admin/addCustomer
        adminService.tokenCheck(token, clientType);
        System.out.println(" i am here");
        adminService.addCustomer(customer);
    }

    @GetMapping("/getAllCustomers")
    @SneakyThrows
    public ResponseEntity<?> getAllCustomers(@RequestHeader String token) {   // http://localhost:8080/admin/getAllCustomers
        adminService.tokenCheck(token, clientType);
        List<Customer> res = adminService.getAllCustomer();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/updateCustomer") //http://localhost:8080/admin/updateCustomer
    @SneakyThrows
    public void updateCustomer(@RequestBody Customer customer, @RequestHeader String token) {
        adminService.tokenCheck(token, clientType);
        adminService.updateCustomerDetails(customer);
    }

    @DeleteMapping("/deleteCustomer/{id}") // http://localhost:8080/admin/deleteCustomer/{id}
    @ResponseBody
    @SneakyThrows
    public void deleteCustomer(@PathVariable int id, @RequestHeader String token) {
        adminService.tokenCheck(token, clientType);

        adminService.deleteCustomer(id);

    }


}




