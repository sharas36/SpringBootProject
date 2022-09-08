package com.springCoupon.menus;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

@Primary
@Component
@Scope("prototype")
public class AdminMenu extends Menu {

    private Scanner scanner= new Scanner(System.in);


    @Autowired
    private AdminService adminService;

    public AdminMenu(){

        while (true) {
            System.out.println("Please choose your action: ");
            menu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        Company company = addCompany();
                        System.out.println(company.getCompanyName());
                        this.adminService.addCompany(company);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("whats the id of the company to update?");
                    int id = scanner.nextInt();
                    try {
                        adminService.updateCompanyDetails(updateCompany(adminService.getOneCompany(id)));
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("whats the id of the company to delete?");
                    id = scanner.nextInt();
                    try {
                        adminService.deleteCompany(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println(adminService.getAllCompany());
                case 5:
                    System.out.println("whats the id of the company to show?");
                    id = scanner.nextInt();
                    try {
                        System.out.println(adminService.getOneCompany(id));
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    try {
                        adminService.addCustomer(addCustomer());
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    System.out.println("whats the id of the customer to update?");
                    id = scanner.nextInt();
                    try {
                        adminService.updateCustomerDetails(adminService.getOneCustomer(id));
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 8:
                    System.out.println("whats the id of the customer to delete?");
                    id = scanner.nextInt();
                    try {
                        adminService.deleteCustomer(id);
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 9:
                    System.out.println("whats the id of the customer to show?");
                    id = scanner.nextInt();
                    try {
                        System.out.println(adminService.getOneCustomer(id));
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 10:
                    System.out.println(adminService.getAllCustomer());
                    break;
                default:
                    System.out.println("wrong choice. please try again");
            }
        }
    }

    private void menu(){
        System.out.println("1. add new company \n" +
                "2. update Existing company\n" +
                "3. delete company \n" +
                "4. get all companies \n" +
                "5. get one company \n" +
                "6. add new customer \n" +
                "7. update existing customer \n" +
                "8. delete customer \n" +
                "9. get all customers \n" +
                "10. get one customer \n");
    }

    private Company addCompany(){
        System.out.println("What's company name?");
        String name = scanner.next();
        System.out.println("whats the email?");
        String email = scanner.next();
        System.out.println("whats the password?");
        String password = scanner.next();

        Company company = Company.builder().companyName(name).dateCreated(LocalDateTime.now()).email(email).password(password).build();
        System.out.println(company == null);
        return company;
    }

    private Company updateCompany(Company company){
        System.out.println("whats the email?");
        String email = scanner.next();
        System.out.println("whats the password?");
        String password = scanner.next();
        company.setEmail(email);
        company.setPassword(password);
        return company;
    }

    private Customer addCustomer(){
        System.out.println("What's first name?");
        String firstName = scanner.next();
        System.out.println("What's last name?");
        String lastName = scanner.next();
        System.out.println("whats the email?");
        String email = scanner.next();
        System.out.println("whats the password?");
        String password = scanner.next();
        Customer customer = Customer.builder().firstName(firstName).lastName(lastName).email(email).password(password).build();
        return customer;
    }

    private Customer updateCustomer(Customer customer){
        System.out.println("whats the first name?");
        String firstName = scanner.next();
        System.out.println("whats the last name?");
        String lastName = scanner.next();
        System.out.println("whats the email?");
        String email = scanner.next();
        System.out.println("whats the password?");
        String password = scanner.next();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);
        return customer;
    }
}
