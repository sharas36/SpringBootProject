package com.springCoupon.controllers;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.exception.CouponSystemException;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public void addCompany(@RequestBody Company company) { // http://localhost:8080/admin/addCompany

        System.out.println("Got: " + company);
        adminService.addCompany(company);


    }

    @GetMapping("/getAllCompanies") // http://localhost:8080/admin/getAllCompanies
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<?> getAllCompanies() {
        List<Company> res = adminService.getAllCompany();
        ResponseEntity<List<Company>> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
        return responseWrapper;
    }

    @SneakyThrows
    @PostMapping("/updateCompany") //http://localhost:8080/admin/updateCompany
    public void updateCompany(@RequestBody Company company) {

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
        adminService.addCustomer(customer);
    }

    @GetMapping("/getAllCustomers") // http://localhost:8080/admin/getAllCustomers
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> res = adminService.getAllCustomer();
        ResponseEntity<List<Customer>> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
        return responseWrapper;
    }


    @PostMapping("/updateCustomer") //http://localhost:8080/admin/updateCustomer
    @SneakyThrows
    public void updateCustomer(@RequestBody Customer customer) {

        adminService.updateCustomerDetails(customer);
    }

    @DeleteMapping("/deleteCustomer/{id}") // http://localhost:8080/admin/deleteCustomer/{id}
    @ResponseBody
    @SneakyThrows
    public void deleteCustomer(@PathVariable int id) {

        adminService.deleteCustomer(id);

    }


}


//@Controller
//@RequestMapping("api")
//public class PersonController {
//
//    @Autowired
//    private Service service;
//
//    @GetMapping("/hey") // http://localhost:8080/api/hey
//    @ResponseBody
//    public String greet(){
//        return "Hello from backend!";
//    }
//
//    @GetMapping("/randomPerson") // http://localhost:8080/api/randomPerson
//    @ResponseBody
//    public Person randomPerson(){
//        Person p = Person.builder().gender(Gender.Other).name("Elon").age(28).build();
//        Company mindolife = new Company();
//        mindolife.setName("Mindolife");
//        mindolife.setInitDate(LocalDate.of(2014, 7,14));
//        p.setCompany(mindolife);
//        return p;
//    }
//
//    @GetMapping("/add/{x}/{y}") // http://localhost:8080/api/add/{x}/{y}
//    @ResponseBody
//    public String add(@PathVariable Double x, @PathVariable Double y){
//        System.out.println("Got: x="+x+", y="+y);
//        return "Result: " + (x+y);
//    }
//
//    @PostMapping("/addPerson")
//    @ResponseBody
//    public Integer addPerson(@RequestBody Person person, @RequestHeader("myKey") int sessionKey){ // http://localhost:8080/api/addPerson
//        System.out.println("Got: "+person);
//        System.out.println("sessionKey="+sessionKey);
//        return (new Random()).nextInt();
//    }
//
//    @GetMapping("/getCompany/{id}") // http://localhost:8080/api/getCompany/{id}
//    @ResponseBody
//    public ResponseEntity<?> getCompany(@PathVariable int id){
//        try {
//            Company res = service.findCompany(id);
//            System.out.println("getCompany: ");
//            ResponseEntity<Company> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
//            return responseWrapper;
//        }catch (Exception e){
//            return new ResponseEntity<String>("Bad request, no company found for id="+id, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/getCompanies") // http://localhost:8080/api/getCompanies
//    @ResponseBody
//    public ResponseEntity<?> getCompanies() throws Exception {
//        List<Company> res = service.findAllCompanies();
////        if(res.size()<3){ //for testing in case of exception
////            throw new Exception("There's less than three companies!!");
////        }
//        System.out.println("getCompanies: ");
//        ResponseEntity<List<Company>> responseWrapper = new ResponseEntity<>(res, HttpStatus.OK);
//        return responseWrapper;
//    }
//
//    @PostMapping("/addCompany")
//    @ResponseBody
//    public Integer addCompany(@RequestBody Company company){ // http://localhost:8080/api/addCompany
//        System.out.println("Got: "+company);
//        Company res = service.addCompany(company);
//        return res.getId();
//    }
//
//    @DeleteMapping("/deleteCompany/{id}") // http://localhost:8080/api/deleteCompany/{id}
//    @ResponseBody
//    public ResponseEntity<?> deleteCompany(@PathVariable int id) throws Exception {
//        service.deleteCompany(id);
//        System.out.println("deleteCompany: "+id);
//        return new ResponseEntity<>("Success", HttpStatus.OK);
//    }
//

