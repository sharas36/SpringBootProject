package com.springCoupon.utilities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginInfo {

        private String email;
        private String password;

}
