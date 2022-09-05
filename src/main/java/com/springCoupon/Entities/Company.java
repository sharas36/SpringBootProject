package com.springCoupon.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "companyId")
@ToString(of = {"companyId", "companyName", "email",
"dateCreated"})

public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;

    private String companyName;
    private String email;
    private String password;
    private LocalDateTime dateCreated;

    @OneToMany(orphanRemoval = true, mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company")
    private List<Coupon> coupons = new ArrayList<Coupon>();

    public void addCoupon(Coupon coupon) {
        this.coupons.add(coupon);
    }


}