package com.springCoupon.utilities;

import com.springCoupon.Entities.Customer;
import com.springCoupon.Services.AdminService;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.menus.AdminMenu;
import com.springCoupon.menus.CompanyMenu;
import com.springCoupon.menus.CustomerMenu;
import com.springCoupon.menus.Menu;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginManager {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CustomerService customerService;

    private static LoginManager instance = new LoginManager();


    public static LoginManager getInstance() {
        return instance;
    }

    public Menu login(ClientType clientType, String email, String password) throws CouponSystemException {

        switch (clientType) {

            case ADMINISTRATOR:
                    if (adminService.adminLogin(email, password)){
                        return new AdminMenu();
                    }
                break;

            case COMPANY:
                    if (companyService.loginCheck(email, password)) {
                        return new CompanyMenu();
                    }
                break;
            case CUSTOMER:
                    if (customerService.loginCustomer(email, password)) {
                        return new CustomerMenu();
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
