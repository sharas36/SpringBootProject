package com.springCoupon.menus;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Admin {

    private String email;
    private String password;
}
