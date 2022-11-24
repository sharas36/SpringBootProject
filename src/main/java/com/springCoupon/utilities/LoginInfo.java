package com.springCoupon.utilities;
    
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springCoupon.Entities.Coupon;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "companyId")

public class LoginInfo {

    private String email;
    private String password;

}