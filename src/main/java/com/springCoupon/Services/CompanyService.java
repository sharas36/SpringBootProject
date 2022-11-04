package com.springCoupon.Services;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService extends MainService {


    private int companyId = 2;

    public boolean loginCheck(String email, String password) throws CouponSystemException {


        if (!companyRepository.findByEmailAndPassword(email, password).isEmpty()) {
            companyId = companyRepository.findByEmailAndPassword(email, password).get(0).getCompanyId();
            return true;
        }
        throw new CouponSystemException("Something wrong. Please try again");
    }

    public List<Coupon> getCouponsOfCompany() {
        return couponRepository.findByCompany(companyRepository.getById(companyId));
    }

    public List<Coupon> findByCompanyIdAndCategoryId(int categoryId) {

        return couponRepository.findById(this.companyId).stream().filter(coupon -> coupon.getCategoryId() == categoryId).collect(Collectors.toList());
    }

    public Coupon addCoupon(Coupon coupon) throws CouponSystemException {
        if (!couponRepository.findByCouponNameAndCompany(coupon.getCouponName(), companyRepository.getById(companyId)).isEmpty()) {
            throw new CouponSystemException("the same name belongs to the same company");
        }
        coupon.setCompany(companyRepository.getById(companyId));
        return couponRepository.save(coupon);
    }

    public void deletedCoupon(int couponId) throws CouponSystemException {

        if (couponRepository.findById(couponId).isEmpty()) {
            throw new CouponSystemException("this coupons is not exist");
        } else {
//            Company company = companyRepository.getById(this.companyId);
//            company.getCoupons().remove(couponRepository.findById(couponId).get());
//            companyRepository.save(company);
            couponRepository.deleteCoupon(couponId);

        }
    }

    public void updateCoupon(Coupon coupon) throws CouponSystemException {

        if (couponRepository.findById(coupon.getCouponId()).isEmpty()) {
            throw new CouponSystemException("this coupons is not exist");
        }

        couponRepository.save(coupon);

    }

    public String getCompanyDetails() {
        return companyRepository.findById(companyId).toString();
    }

    public List<Coupon> getByMaxPrice(int price) {
        return new ArrayList<>();
    }

    public List<Coupon> getCouponBetweenByDate(LocalDateTime start, LocalDateTime end) {
        return couponRepository.findByCompany(companyRepository.getById(companyId)).stream().filter
                (coupon -> coupon.getEndDate().isBefore(end) && coupon.getStartDate().isAfter(start)).collect(Collectors.toList());
    }

    public void setCompanyId(int company_Id) {
        companyId = company_Id;
    }

    public int getCompanyId() {
        return this.companyId;
    }




}
