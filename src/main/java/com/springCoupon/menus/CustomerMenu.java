package com.springCoupon.menus;

import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
@Scope("prototype")
@Lazy
@Primary
public class CustomerMenu {

    private Scanner scanner= new Scanner(System.in);

    @Autowired
    private CustomerService customerService;

    public CustomerMenu(){

    }

    public int getChoice(){
        System.out.println("Please choose your action: ");
        menu();
        int choice = scanner.nextInt();
        return choice;
    }

    private void menu(){
        System.out.println("1. purchase coupon \n" +
                "2. get all your coupons \n" +
                "3. get coupons by category \n" +
                "4. get coupons by max price \n" +
                "5. get your details");
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
