package com.springCoupon.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Table(name = "coupons")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "couponId")
@ToString(of = {"couponId", "couponName", "price",
        "description", "amount", "endDate"
})

public class Coupon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int couponId;

    private String couponName;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_Id")
    @JsonIgnoreProperties({"coupons","hibernateLazyInitializer", "handler"})
    private Company company;


    @ManyToMany(mappedBy = "coupons", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("coupons")
    @JsonIgnore
    List<Customer> customers = new ArrayList<>();

    private int amount;
    private double price;
    private int categoryId;
    private String imageURL;
    private LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime endDate;

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public void removeCustomers(List<Customer> customers) {
        for (Customer customer :
                customers) {
            removeCustomer(customer);
        }
    }
}
