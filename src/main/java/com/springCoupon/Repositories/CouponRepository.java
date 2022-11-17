package com.springCoupon.Repositories;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Integer> {


    Coupon getById(int id);

    Page<Coupon> findAll(org.springframework.data.domain.Pageable pageable);

    Page<Coupon> findByCategoryId(int categoryId, org.springframework.data.domain.Pageable pageable);

    Page<Coupon> findByPriceLessThan(int maxPrice, org.springframework.data.domain.Pageable pageable);

    Page<Coupon> findByPriceLessThanAndCompany(int maxPrice, Company company, org.springframework.data.domain.Pageable pageable);
    Page<Coupon> findByCompany(Company company, org.springframework.data.domain.Pageable pageable);


    @Transactional
    @Query(value = "select from coupons where category_Id =:categoryId AND company_id =:companyId", nativeQuery = true)
    Page<Coupon> findByCompanyIdAndCategoryId(int companyId, int categoryId, org.springframework.data.domain.Pageable pageable);

    @Transactional
    @Query(value = "select count(coupon_id) from coupons where category_Id =:categoryId AND company_id =:companyId", nativeQuery = true)
    Integer countByCategoryId(int categoryId, int companyId);

    @Transactional
    @Query(value = "select count(coupon_id) from coupons where price <= maxPrice AND company_id =:companyId", nativeQuery = true)
    Integer countByMaxPrice(int maxPrice, int companyId);

    List<Coupon> findByCouponNameAndCompany(String couponName, Company company);

    List<Coupon> findByEndDateLessThan(LocalDateTime localDateTime);

    @Transactional
    @Query(value = "select coupons_coupon_id from customers_coupons where customers_customer_Id =:customer_id", nativeQuery = true)
    Page<Integer> findPurchasesOfCustomer(@Param("customer_id") int customer_id, org.springframework.data.domain.Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "delete from customers_coupons where coupons_coupon_id =:coupon_id", nativeQuery = true)
    void deletePurchasesOfCustomer(@Param("coupon_id") int coupon_id);

    @Transactional
    @Modifying
    @Query(value = "delete from coupons where coupon_id =:coupon_id", nativeQuery = true)
    void deleteCoupon(@Param("coupon_id") int coupon_id);


    @Transactional
    @Modifying
    @Query(value = "delete from coupons where company_id =:company_id", nativeQuery = true)
    void deleteCouponsOfCompany(@Param("company_id") int company_id);

    @Query(value = "select from coupons where companyId = :companyId and price <= :price", nativeQuery = true)
    Page<Coupon> getCompanyCouponsByMaxPrice(@Param("companyId") int couponId, @Param("price") String password, org.springframework.data.domain.Pageable pageable);

}
