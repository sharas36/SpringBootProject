package com.springCoupon.Services;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
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

    public Page<Coupon> getCouponsOfCompany(Pageable pageable) {
        return couponRepository.findByCompany(companyRepository.getById(companyId), pageable);
    }

    public Page<Coupon> findByCompanyIdAndCategoryId(int categoryId, Pageable pageable) {

        return couponRepository.findByCompanyAndCategoryId(companyRepository.getById(companyId), categoryId, pageable);
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

    public Page<Coupon> getByMaxPrice(int price, Pageable pageable) {
        return couponRepository.findByCompany(companyRepository.getById(companyId), pageable).stream().filter(coupon -> coupon.getPrice() <= price).collect(Collectors.toCollection());
    }

    public Page<Coupon> getCouponBetweenByDate(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return couponRepository.findByCompany(companyRepository.getById(companyId), pageable).stream().filter(coupon -> coupon.getEndDate().isBefore(end) && coupon.getStartDate().isAfter(start)).collect(Collectors.toCollection());
    }

    public void setCompanyId(int company_Id) {
        companyId = company_Id;
    }

    public int getCompanyId() {
        return this.companyId;
    }

}
