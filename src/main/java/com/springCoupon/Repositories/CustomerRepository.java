package com.springCoupon.Repositories;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    Optional<Customer> findByEmailAndPassword(String email, String password);

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByCustomerId(String email);


    List<Customer> findAll();

    @Transactional
    @Modifying
    @Query(value = "delete from customers where customer_id =:customer_id", nativeQuery = true)
    void deleteCustomer(@Param("customer_id") int customer_id);
}
