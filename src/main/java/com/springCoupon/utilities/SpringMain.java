package com.springCoupon.utilities;

import com.springCoupon.menus.AdminMenu;
import com.springCoupon.menus.CompanyMenu;
import com.springCoupon.menus.CustomerMenu;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class SpringMain {

    @Bean (name = "admin-menu")
    @Lazy
    public static AdminMenu GetAdmin() {
        return new AdminMenu();
    }

    @Bean
    @Lazy
    public static CustomerMenu getCustomer() {
        return new CustomerMenu();
    }

    @Bean
    @Lazy
    public static CompanyMenu getCompany() {
        return new CompanyMenu();
    }

}
