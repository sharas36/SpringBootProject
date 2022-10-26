package com.springCoupon.controllers;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.Admin;
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

    @SneakyThrows
    @PostMapping("/loginAdmin")
    public void loginAdmin(@RequestBody String email, String password) { //http://localhost:8080/customers/adminCustomer
        adminService.adminLogin(email, password);
    }

    @PostMapping("/addCompany")
    @SneakyThrows
    public void addCompany(@RequestBody Company company, @RequestHeader String token) { // http://localhost:8080/admin/addCompany
        System.out.println("your token is:" + token);
        System.out.println("Got: " + company);
        adminService.addCompany(company);


    }

    @GetMapping("/getAllCompanies") // http://localhost:8080/admin/getAllCompanies
    @SneakyThrows
    public ResponseEntity<?> getAllCompanies() {
        List<Company> res = adminService.getAllCompany();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/updateCompany") //http://localhost:8080/admin/updateCompany
    public void updateCompany(@RequestBody Company company) {

        System.out.println(company);
        adminService.updateCompanyDetails(company);
    }

    @DeleteMapping("/deleteCompany/{id}") // http://localhost:8080/admin/deleteCompany/{id}
    @ResponseBody
    @SneakyThrows
    public void deleteCompany(@PathVariable int id) {
        adminService.deleteCompany(id);
        System.out.println("deleteCompany: " + id);
    }

    @GetMapping("/getOneCompany/{id}") // http://localhost:8080/admin/getOneCompany/{id}
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<?> getOneCompany(@PathVariable int id) {
        Company res = adminService.getOneCompany(id);
        ResponseEntity<Company> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
        return responseWrapper;

    }

    @PostMapping("/addCustomer")
    @ResponseBody
    @SneakyThrows
    public void addCustomer(@RequestBody Customer customer) { // http://localhost:8080/admin/addCustomer
        System.out.println(" i am here");
        adminService.addCustomer(customer);
    }

    @GetMapping("/getAllCustomers") // http://localhost:8080/admin/getAllCustomers
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> res = adminService.getAllCustomer();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/updateCustomer") //http://localhost:8080/admin/updateCustomer
    @SneakyThrows
    public void updateCustomer(@RequestBody Customer customer) {
        System.out.println(customer.getCustomerId());
        adminService.updateCustomerDetails(customer);
    }

    @DeleteMapping("/deleteCustomer/{id}") // http://localhost:8080/admin/deleteCustomer/{id}
    @ResponseBody
    @SneakyThrows
    public void deleteCustomer(@PathVariable int id) {

        adminService.deleteCustomer(id);

    }


}




