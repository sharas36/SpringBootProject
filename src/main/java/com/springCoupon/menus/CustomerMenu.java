package com.springCoupon.menus;

import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
@Scope("prototype")
@Lazy
public class CustomerMenu extends Menu{

    private Scanner scanner= new Scanner(System.in);

    @Autowired
    private CustomerService customerService;

    public CustomerMenu(){

        while (true){
            System.out.println("Please choose your action: ");
            menu();
            int choice = scanner.nextInt();

            switch (choice){
                case 1:
                    System.out.println("whats the id of the coupon?");
                    int couponId = scanner.nextInt();
                    try {
                        customerService.addPurchase(couponId);
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println(customerService.getAllCustomersCoupons());
                    break;
                case 3:
                    System.out.println("whats the category?");
                    int categoryId = 0;
                    while (categoryId < 1 || categoryId > 10) {
                        categoryId = categoryList();
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

    private void menu(){
        System.out.println("1. purchase coupon \n" +
                "2. get all your coupons \n" +
                "3. get coupons by category \n" +
                "4. get coupons by max price \n" +
                "5. get your details");
    }

    private int categoryList(){
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
