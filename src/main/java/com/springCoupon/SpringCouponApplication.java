package com.springCoupon;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.controllers.AdminController;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.menus.AdminMenu;
import com.springCoupon.menus.CompanyMenu;
import com.springCoupon.menus.CustomerMenu;
import com.springCoupon.utilities.ClientType;
import com.springCoupon.utilities.DailyJob;
import com.springCoupon.utilities.LoginManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class SpringCouponApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        ConfigurableApplicationContext ctx = SpringApplication.run(SpringCouponApplication.class, args);


        DailyJob dailyJob = ctx.getBean(DailyJob.class);
        dailyJob.startDailyJob();

        LoginManager loginManager = ctx.getBean(LoginManager.class);

        AdminService adminService = ctx.getBean(AdminService.class);

        CompanyService companyService = ctx.getBean(CompanyService.class);

        CustomerService customerService = ctx.getBean(CustomerService.class);


        int type = 0;
        while (type < 1 || type > 3) {
            System.out.println("1. Admin \n" +
                    "2. Company \n" +
                    "3. Customer \n");
            type = scanner.nextInt();
        }

        ClientType clientType;
        if (type == 1) {
            clientType = ClientType.ADMINISTRATOR;
        } else if (type == 2) {
            clientType = ClientType.COMPANY;
        } else {
            clientType = ClientType.CUSTOMER;
        }
        System.out.println("email: ");
        String email = scanner.next();
        System.out.println("password: ");
        String password = scanner.next();
        while (true) {
            if (loginManager.login(clientType, email, password)) {

                if (type == 1) {
                    AdminMenu adminMenu = ctx.getBean(AdminMenu.class);
                    int choice = adminMenu.getChoice();
                    switch (choice) {
                        case 1:
                            try {
                                Company company = adminMenu.addCompany();
                                System.out.println(company.getCompanyName());
                                adminService.addCompany(company);
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            System.out.println("whats the id of the company to update?");
                            int id = scanner.nextInt();
                            try {
                                adminService.updateCompanyDetails(adminMenu.updateCompany(adminService.getOneCompany(id)));
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
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
                                System.out.println(e.getException());
                            }
                            break;
                        case 4:
                            System.out.println(adminService.getAllCompany());
                            break;
                        case 5:
                            System.out.println("whats the id of the company to show?");
                            id = scanner.nextInt();
                            try {
                                System.out.println(adminService.getOneCompany(id));
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 6:
                            try {
                                adminService.addCustomer(adminMenu.addCustomer());
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 7:
                            System.out.println("whats the id of the customer to update?");
                            id = scanner.nextInt();
                            try {
                                adminService.updateCustomerDetails(adminService.getOneCustomer(id));
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 8:
                            System.out.println("whats the id of the customer to delete?");
                            id = scanner.nextInt();
                            try {
                                adminService.deleteCustomer(id);
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 9:
                            System.out.println("whats the id of the customer to show?");
                            id = scanner.nextInt();
                            try {
                                System.out.println(adminService.getOneCustomer(id));
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 10:
                            System.out.println(adminService.getAllCustomer());
                            break;
                        default:
                            System.out.println("wrong choice. please try again");
                    }
                } else if (type == 2) {
                    CompanyMenu companyMenu = ctx.getBean(CompanyMenu.class);
                    int choice = companyMenu.getChoice();
                    switch (choice) {
                        case 1:
                            try {
                                companyService.addCoupon(companyMenu.addCoupon());
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 2:
                            try {
                                companyService.updateCoupon(companyMenu.updateCoupon());
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 3:
                            System.out.println("Whats the id of the coupon you want to delete?");
                            int id = scanner.nextInt();
                            try {
                                companyService.deletedCoupon(id);
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 4:
                            System.out.println(companyService.getCouponsOfCompany());
                            break;
                        case 5:
                            System.out.println("whats the category?");
                            int categoryId = 0;
                            while (categoryId < 1 || categoryId > 10) {
                                categoryId = companyMenu.categoryList();
                                if (categoryId < 1 || categoryId > 10) {
                                    System.out.println("wrong choice. please try again");
                                }
                            }
                            System.out.println(companyService.findByCompanyIdAndCategoryId(categoryId));
                            break;
                        case 6:
                            System.out.println("whats the price?");
                            int price = scanner.nextInt();
                            System.out.println(companyService.getByMaxPrice(price));
                        case 7:
                            companyService.getCompanyDetails();
                            break;
                        default:
                            System.out.println("wrong choice. please try again");
                    }
                } else {
                    CustomerMenu customerMenu = ctx.getBean(CustomerMenu.class);
                    int choice = customerMenu.getChoice();
                    switch (choice) {
                        case 1:
                            System.out.println("whats the id of the coupon?");
                            int couponId = scanner.nextInt();
                            try {
                                customerService.addPurchase(couponId);
                            } catch (CouponSystemException e) {
                                System.out.println(e.getException());
                            }
                            break;
                        case 2:
                            System.out.println(customerService.getAllCustomersCoupons());
                            break;
                        case 3:
                            System.out.println("whats the category?");
                            int categoryId = 0;
                            while (categoryId < 1 || categoryId > 10) {
                                categoryId = customerMenu.categoryList();
                                if (categoryId < 1 || categoryId > 10) {
                                    System.out.println("wrong choice. please try again");
                                }
                            }
                            System.out.println(customerService.getAllCustomersCouponsByCategory(categoryId));
                            break;
                        case 4:
                            System.out.println("whats the price?");
                            int price = scanner.nextInt();
                            customerService.getAllCustomersCouponsByMaxPrice(price);
                            break;
                        case 5:
                            customerService.getCustomerDetails();
                            break;
                        default:
                            System.out.println("wrong choice. please try again");
                    }
                }
            }
            else
            {
                System.out.println("email: ");
                email = scanner.next();
                System.out.println("password: ");
                password = scanner.next();
            }
        }


    }

    public static Company GetCompany(int i) {

        int year = new Random().nextInt(22) + 2000;
        int month = new Random().nextInt(11) + 1;
        int day = new Random().nextInt(27) + 1;
        int hour = new Random().nextInt(23) + 1;
        int minute = new Random().nextInt(58) + 1;
        int price = new Random().nextInt(239) + 1;
        int categoryId = new Random().nextInt(9) + 1;
        int amount = new Random().nextInt(499) + 1;


        Company company = Company.builder().companyName("company" + i).password("password" + i).email("email" + i)
                .dateCreated(LocalDateTime.of(year, month, day, hour, minute)).build();
        return company;
    }

    public static Customer GetCustomer(int i) {
        return Customer.builder().email("email" + i).password("password" + i).firstName("firstName" + i).lastName("lastName" + i).build();

    }

    public static Coupon GetCoupon(int i, Company company) {

        int year = new Random().nextInt(22) + 2000;
        int month = new Random().nextInt(11) + 1;
        int day = new Random().nextInt(27) + 1;
        int hour = new Random().nextInt(23) + 1;
        int minute = new Random().nextInt(58) + 1;
        int price = new Random().nextInt(239) + 1;
        int categoryId = new Random().nextInt(9) + 1;
        int amount = new Random().nextInt(499) + 1;


        Coupon coupon = Coupon.builder().couponName("couponName " + i).amount(amount)
                .categoryId(categoryId).description("description" + i)
                .price(price).imageURL("imageUrl" + i).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.of(year, month, day, hour, minute)).company(company).build();

        //  coupon.getCompany().addCoupon(coupon);
        return coupon;
    }


}



