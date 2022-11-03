package com.springCoupon;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.DailyJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class SpringCouponApplication {

    public static void main(String[] args) throws CouponSystemException, SQLException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        ConfigurableApplicationContext ctx = SpringApplication.run(SpringCouponApplication.class, args);

        DailyJob dailyJob = ctx.getBean(DailyJob.class);
        AdminService adminService = ctx.getBean(AdminService.class);
        CompanyService companyService = ctx.getBean(CompanyService.class);
        CustomerService customerService = ctx.getBean(CustomerService.class);
        customerService.setCustomerId(26);
        adminService.deleteCoupon(994,27);


       dailyJob.startDailyJob();

//        for (int i = 10; i <= 30; i++) {
//
//            customerService.setCustomerId(i);
//
//            for (int y = 11; y <= 15; y++) {
//
//             customerService.addPurchase(y);
//            }
//
//        }

//        for (int i = 1; i <= 200; i++) {
//            for (int y = 1; y <= 5; y++) {
//                Company company = new Company();
//                company.setCompanyId(i);
//                companyService.setCompanyId(i);
//                companyService.addCoupon(getCoupon(i +"" +y,company));
//
//            }

    }


//        for (int i=1;i<=30;i++){
//            Company company = getCompany(i);
//            adminService.addCompany(company);
//        }


    public static Company getCompany(String i) {

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

    public static Customer getCustomer(String i) {
        return Customer.builder().email("email" + i).password("password" + i).firstName("firstName" + i).lastName("lastName" + i).build();

    }

    public static Coupon getCoupon(String i, Company company) {

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



