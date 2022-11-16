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
//import com.springCoupon.utilities.LoginManager;
import com.springCoupon.utilities.TokensList;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringCouponApplication {

    public static void main(String[] args) throws CouponSystemException, SQLException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        ConfigurableApplicationContext ctx = SpringApplication.run(SpringCouponApplication.class, args);

//        DailyJob dailyJob = ctx.getBean(DailyJob.class);
        AdminService adminService = ctx.getBean(AdminService.class);
        CompanyService companyService = ctx.getBean(CompanyService.class);
        CustomerService customerService = ctx.getBean(CustomerService.class);

        adminService.deleteCustomer(9);


//        companyService.getCouponBetweenByDate(LocalDateTime.of(2019, 03, 28, 14, 33, 48, 640000), LocalDateTime.now(),4);


//        for (int i =1; i<=30;i++){
//            adminService.addPurchase(new Random().nextInt(10)+2,new Random().nextInt(10 )+2);
//        }


//          for (int i=1;i<=50;i++){
//            Company company = getCompany(i);
//            Customer customer = getCustomer(i);
//            adminService.addCustomer(customer);
//            adminService.addCompany(company);
//         }
//        for (int i = 1; i <= 100; i++) {
//            Company company = new Company();
//            company.setCompanyId(new Random().nextInt(49) + 1);
//            Coupon coupon = getCoupon(i, company);
//            adminService.addCoupon(coupon, new Random().nextInt(49) + 1);
//        }
//
//        for (int i = 0; i<= 100; i++){
//          customerService.addPurchase(new Random().nextInt(99) + 1,new Random().nextInt(49) + 1);
//        }

//        String algorithm = "HmacSHA256AA";
//        algorithm = SignatureAlgorithm.HS256.getJcaName();
//        byte[] secretKeyEncoded = "this+is+my+key+and+it+must+be+at+least+256+bits+long".getBytes();
//        byte[] secretKeyDecoded = Base64.getDecoder().decode(secretKeyEncoded);
//        Key key = new SecretKeySpec(secretKeyDecoded, algorithm);
//        Instant now = Instant.now();
//        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
//
//        String token = Jwts.builder()
//                .signWith(key)
//                .setIssuedAt(Date.from(now))
//                .setExpiration(Date.from(now.plus(30, ChronoUnit.SECONDS)))
//                .setId("101")
//                .setSubject("vfv")
//                .claim("clientType", "admin")
//                .claim("clientPassword", "vfvd")
//                .compact();
//        System.out.println(token +" i am  the token ");
//        Jws<Claims> expandedJwt = jwtParser.parseClaimsJws(token);
//        System.out.println(expandedJwt);
//        System.out.println("header");
//        System.out.println(expandedJwt.getHeader());
//        System.out.println("body");
//        System.out.println(expandedJwt.getBody());
//        System.out.println("signature");
//        System.out.println(expandedJwt.getSignature());
//        System.out.println(expandedJwt.getBody().getId());


//        for (int i=1;i<=30;i++){
//            Company company = getCompany(i);
//            adminService.addCompany(company);
//        }

    }

    public static Company getCompany(int i) {

        int year = new Random().nextInt(3) + 2022;
        int month = new Random().nextInt(11) + 1;
        int day = new Random().nextInt(27) + 1;
        int hour = new Random().nextInt(23) + 1;
        int minute = new Random().nextInt(58) + 1;
        int price = new Random().nextInt(239) + 1;
        int categoryId = new Random().nextInt(9) + 1;
        int amount = new Random().nextInt(499) + 1;


        Company company = Company.builder().companyName("company" + i).password("password" + i).email("email" + i + "@gmail.com")
                .dateCreated(LocalDateTime.of(year, month, day, hour, minute)).build();
        return company;
    }

    public static Customer getCustomer(int i) {
        return Customer.builder().email("email" + i +"@gmail.com").password("password" + i).firstName("firstName" + i).lastName("lastName" + i).build();

    }

    public static Coupon getCoupon(int i, Company company) {

        int year = new Random().nextInt(23) + 2000;
        int month = new Random().nextInt(11) + 1;
        int day = new Random().nextInt(27) + 1;
        int hour = new Random().nextInt(23) + 1;
        int minute = new Random().nextInt(58) + 1;
        int price = new Random().nextInt(239) + 1;
        int categoryId = new Random().nextInt(9) + 1;
        int amount = new Random().nextInt(499) + 1;

        LocalDateTime endLocalDateTime = LocalDateTime.of(year, month, day, hour, minute);
        LocalDateTime startDateTime = LocalDateTime.of(year - 1, month, day, hour, minute);
        Coupon coupon = Coupon.builder().couponName("couponName " + i).amount(amount)
                .categoryId(categoryId).description("description" + i)
                .price(price).imageURL("imageUrl" + i).startDate(startDateTime)
                .endDate(endLocalDateTime).company(company).build();


        return coupon;
    }


}



