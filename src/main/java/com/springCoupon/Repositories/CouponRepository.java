package com.springCoupon.Repositories;

import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {


    List<Coupon> findAll();

    List<Coupon> findByEndDateLessThan(LocalDateTime localDateTime);

    Page<Coupon> findByCategoryId(int categoryId, org.springframework.data.domain.Pageable pageable);

    Page<Coupon> findByCompany(Company company, org.springframework.data.domain.Pageable pageable);

    List<Coupon> findByCompany(Company company);

    Page<Coupon> findByCompanyAndCategoryId(Company company, int categoryId, Pageable pageable);

    List<Coupon> findByCouponNameAndCompany(String couponName, Company company);

    Page<Coupon> findByEndDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime2, org.springframework.data.domain.Pageable pageable);

    Page<Coupon> findByEndDateBetweenAndCompany(LocalDateTime localDateTime, LocalDateTime localDateTime2, Company company,
                                                org.springframework.data.domain.Pageable pageable);

    Page<Coupon> findByPriceLessThanAndCompany(double price, Company company, org.springframework.data.domain.Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "select coupons_coupon_id from customers_coupons where customer_customer_Id =:customer_id", nativeQuery = true)
    List<Integer> findPurchasesOfCustomer(@Param("customer_id") int customer_id);

    @Transactional
    @Modifying
    @Query(value = "delete from customers_coupons where coupons_coupon_id =:coupon_id", nativeQuery = true)
    void deletePurchasesOfCustomer(@Param("coupon_id") int coupon_id);

    @Transactional
    @Modifying
    @Query(value = "delete from customers_coupons where customers_customer_id =:customer_id", nativeQuery = true)
    void deleteCustomerPurchaseByCustomerId(@Param("customer_id") int customer_id);

    @Transactional
    @Modifying
    @Query(value = "delete from coupons where coupon_id =:coupon_id", nativeQuery = true)
    void deleteCoupon(@Param("coupon_id") int coupon_id);

    @Transactional
    @Modifying
    @Query(value = "select from coupons where companyId = :companyId and price <= :price", nativeQuery = true)
    List<Coupon> getCompanyCouponsByMaxPrice(@Param("companyId") int couponId, @Param("price") String password);

    @Transactional
    @Modifying
    @Query(value = "delete from customers_coupons where coupons_coupon_id =:coupon_id", nativeQuery = true)
    Integer rowOfCoupon(@Param("coupon_id") int coupon_id);


}
