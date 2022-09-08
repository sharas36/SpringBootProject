package com.springCoupon;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.DailyJob;
import com.springCoupon.utilities.LoginManager;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class SpringCouponApplication {

    public static void main(String[] args) throws CouponSystemException {

        ConfigurableApplicationContext ctx = SpringApplication.run(SpringCouponApplication.class, args);

        DailyJob dailyJob = ctx.getBean(DailyJob.class);


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

    public MainFacade login(ClientType clientType, String email, String password) {

        switch (clientType) {

            case ADMINISTRATOR:
                if (new AdminFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO()).login(email, password)) {
                    return new AdminFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO());
                }
                break;

            case COMPANY:
                if (new CompanyFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO()).login(email, password)) {
                    return new CompanyFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO());
                }
                break;
            case CUSTOMER:
                if (new CustomerFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO()).login(email, password)) {
                    return new CustomerFacade(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO());
                }
                break;
            default: {
                System.out.println("please try again");
                break;
            }

        }

        return null;
    }


}



