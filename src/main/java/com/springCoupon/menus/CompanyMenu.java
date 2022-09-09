package com.springCoupon.menus;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;
@Component
@Scope("prototype")
@Primary
public class CompanyMenu {

    private Scanner scanner= new Scanner(System.in);
    @Autowired
    private CompanyService companyService;

    public CompanyMenu(){

    }

    public int getChoice(){
        System.out.println("Please choose your action: ");
        menu();
        int choice = scanner.nextInt();
        return choice;
    }
    private void menu(){
        System.out.println("1. add new coupon \n" +
                "2. update existing coupon \n" +
                "3. delete coupon \n" +
                "4. get all your coupons \n" +
                "5. get all coupons by category \n" +
                "6. get all coupons by max price \n" +
                "7. get your details");
    }

    public Coupon addCoupon(){
        System.out.println("What's coupon name?");
        String name = scanner.next();
        System.out.println("whats coupon description");
        String description = scanner.next();
        System.out.println("whats the amount?");
        int amount = scanner.nextInt();
        System.out.println("whats the price?");
        double price = scanner.nextDouble();
        System.out.println("whats the last date to use?");
        LocalDateTime endDate = LocalDateTime.parse(scanner.next());
        System.out.println("whats the category?");
        int categoryId = 0;
        while(categoryId < 1 || categoryId > 10){
            categoryId = categoryList();
            if(categoryId < 1 || categoryId > 10){
                System.out.println("wrong choice. please try again");
            }
        }
        Coupon coupon = Coupon.builder().couponName(name).description(description).amount(amount).price(price).endDate(endDate).categoryId(categoryId).build();
        return coupon;
    }

    public Coupon updateCoupon(){
        System.out.println("What's coupon name?");
        String name = scanner.next();
        System.out.println("whats coupon description");
        String description = scanner.next();
        System.out.println("whats the amount?");
        int amount = scanner.nextInt();
        System.out.println("whats the price?");
        double price = scanner.nextDouble();
        System.out.println("whats the last date to use?");
        LocalDateTime endDate = LocalDateTime.parse(scanner.next());
        Coupon coupon = Coupon.builder().couponName(name).description(description).amount(amount).price(price).endDate(endDate).build();
        return coupon;
    }

    public int categoryList(){
        System.out.println("1. travel \n" +
                "2. clothing \n" +
                "3. transportation \n" +
                "4. kitchen \n" +
                "5. garden \n" +
                "6. sport \n" +
                "7. car \n" +
                "8. computer \n" +
                "9. construction \n" +
                "10. food");
        return scanner.nextInt();

    }
}
