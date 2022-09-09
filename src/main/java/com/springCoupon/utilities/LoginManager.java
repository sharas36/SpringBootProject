package com.springCoupon.utilities;

import com.springCoupon.Services.AdminService;
import com.springCoupon.Services.CompanyService;
import com.springCoupon.Services.CustomerService;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class LoginManager {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CustomerService customerService;



    public Boolean login(ClientType clientType, String email, String password) {

        switch (clientType) {

            case ADMINISTRATOR:
                try {
                    if (this.adminService.adminLogin(email, password)) {
                        return true;
                    }
                } catch (CouponSystemException e) {
                    System.out.println(e.getException());
                }
                return false;

            case COMPANY:
                try {
                    if (companyService.loginCheck(email, password)) {
                        return true;
                    }
                } catch (CouponSystemException e) {
                    System.out.println(e.getException());
                }
                return false;
            case CUSTOMER:
                try {
                    if (customerService.loginCustomer(email, password)) {
                       return true;
                    }
                } catch (CouponSystemException e) {
                    System.out.println(e.getException());
                }
                return false;
            default: {
                System.out.println("please try again");
                break;
            }

        }

        return null;
    }


}

