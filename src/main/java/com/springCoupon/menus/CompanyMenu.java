package com.springCoupon.menus;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Scanner;

public class CompanyMenu extends Menu{

    private Scanner scanner= new Scanner(System.in);
    @Autowired
    private CompanyService companyService;

    public CompanyMenu(){
        while (true) {
            System.out.println("Please choose your action: ");
            menu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        companyService.addCoupon(addCoupon());
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        companyService.updateCoupon(updateCoupon());
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Whats the id of the coupon you want to delete?");
                    int id = scanner.nextInt();
                    try {
                        companyService.deletedCoupon(id);
                    } catch (CouponSystemException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println(companyService.getCouponsOfCompany());
                    break;
                case 5:
                    System.out.println("whats the category?");
                    int categoryId = 0;
                    while (categoryId < 1 || categoryId > 10) {
                        categoryId = categoryList();
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
        }
    }

    private void menu(){
        System.out.println("1. add new coupon /n" +
                "2. update existing coupon /n" +
                "3. delete coupon /n" +
                "4. get all your coupons /n" +
                "5. get all coupons by category /n" +
                "6. get all coupons by max price /n" +
                "7. get your details");
    }

    private Coupon addCoupon(){
        System.out.println("What's coupon name?");
        String name = scanner.nextLine();
        System.out.println("whats coupon description");
        String description = scanner.nextLine();
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

    private Coupon updateCoupon(){
        System.out.println("What's coupon name?");
        String name = scanner.nextLine();
        System.out.println("whats coupon description");
        String description = scanner.nextLine();
        System.out.println("whats the amount?");
        int amount = scanner.nextInt();
        System.out.println("whats the price?");
        double price = scanner.nextDouble();
        System.out.println("whats the last date to use?");
        LocalDateTime endDate = LocalDateTime.parse(scanner.next());
        Coupon coupon = Coupon.builder().couponName(name).description(description).amount(amount).price(price).endDate(endDate).build();
        return coupon;
    }

    private int categoryList(){
        System.out.println("1. travel /n" +
                "2. clothing /n" +
                "3. transportation /n" +
                "4. kitchen /n" +
                "5. garden /n" +
                "6. sport /n" +
                "7. car /n" +
                "8. computer /n" +
                "9. construction /n" +
                "10. food");
        return scanner.nextInt();

    }
}
