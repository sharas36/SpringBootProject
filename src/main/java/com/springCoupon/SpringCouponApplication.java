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
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

//        for (int i=0;i<=30;i++){
//            Company company = new Company();
//            company.setCompanyId(new Random().nextInt(10) +4);
//            companyService.addCoupon(getCoupon(i,company));
//        }

    }

    public static Company getCompany(int i) {

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

    public static Customer getCustomer(int i) {
        return Customer.builder().email("email" + i).password("password" + i).firstName("firstName" + i).lastName("lastName" + i).build();

    }

    public static Coupon getCoupon(int i, Company company) {

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



