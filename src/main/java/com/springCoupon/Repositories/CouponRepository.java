package com.springCoupon.Repositories;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

//
//    @Query(value = "select * from employee p where date  :startDate AND :endDate",nativeQuery = true)
//    public List<Person> findAllWithCreationDateTimeBefore(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);

//    @Query(value = "select * from coupons c where end_date between :start and :end and company_id = :companyId ",nativeQuery = true)
//    List<Coupon> GetCouponBetweenByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,int companyId);
//
//    @Query(value = "select  * from coupons c  where c.company_id = :company_id", nativeQuery = true)
//    List<Coupon> findByCompany_id(@Param("company_id") int company_id);
//
//    List<Coupon> findByCouponName(String couponName);
//
//    @Query(value = "select * from coupons c where c.category_id = :categoryId and c.company_id = :companyId", nativeQuery = true)
//    List<Coupon> findByCompanyIdAndCategoryId(@Param("categoryId") int categoryId, @Param("companyId") int companyId);
//
//
//    @Query(value = "select * from coupons c where c.price <= :price and company_id = :companyId ", nativeQuery = true)
//    List<Coupon> getCouponByMaxPrice(@Param("price") int price, @Param("companyId") int company_id);


//    void deleteCoupon(@Param("coupon_id") Integer coupon_id);

    List<Coupon> findAll();

    List<Coupon> findByCategoryId(int categoryId);

    List<Coupon> findByCompany(Company company);

    List<Coupon> findByCompanyAndCategoryId(Company company, int categoryId);

    List<Coupon> findByCouponNameAndCompany(String couponName, Company company);

    @Query(value = "select from customers_coupons where customerId =: customer_id", nativeQuery = true)
    List<Coupon> findPurchasesOfCustomer(@Param("customer_id") int customer_id);

    @Query(value = "select  from customers_coupons where customerId =: customer_id AND coupon_id = ", nativeQuery = true)
    List<Coupon> findPurchasesOfCustomerByCategoryId(@Param("customer_id") int customer_id, @Param("category_id") int category_id);

    @Transactional
    @Modifying
    @Query(value = "delete from coupons where coupon_id =:coupon_id", nativeQuery = true)
    void deleteCoupon(@Param("coupon_id") int coupon_id);


    @Query(value = "select  from coupons c where c.companyId = :companyId and c.price <= :price", nativeQuery = true)
    List<Coupon> getCompanyCouponsByMaxPrice(@Param("companyId") int companyId, @Param("price") int price);

}
