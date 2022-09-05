package com.springCoupon.Services;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService extends MainService {

    private int companyId = 1;

    public boolean loginCheck(String email, String password) {

        if (!companyRepository.findByEmailAndPassword(email, password).isEmpty()) {
            companyId = companyRepository.findByEmailAndPassword(email, password).get(0).getCompanyId();
            return true;
        }
        return false;
    }

    public List<Coupon> getCouponsOfCompany(int companyId) {
        return couponRepository.findByCompany(companyRepository.getById(companyId));
    }

    public List<Coupon> findByCompanyIdAndCategoryId(int categoryId, int companyId) {

        return couponRepository.findByCompanyAndCategoryId(companyRepository.getById(companyId), categoryId);
    }

    public Coupon addCoupon(Coupon coupon) throws CouponSystemException {
        if (!couponRepository.findByCouponNameAndCompany(coupon.getCouponName(), companyRepository.getById(companyId)).isEmpty()) {
                System.out.println("this name already in use");
                throw new CouponSystemException("the same name belongs to the same company");
        }
        coupon.setCompany(companyRepository.getById(companyId));
        return couponRepository.save(coupon);
    }

    public void deletedCoupon(Coupon coupon) {

        if (couponRepository.findById(coupon.getCouponId()).isEmpty()) {
            System.out.println("this coupons is not exist");
        } else {
            System.out.println("deleted");
            couponRepository.deleteById(coupon.getCouponId());
        }
    }

    public void updateCouponPrice(Coupon coupon, int price)  {

        if (couponRepository.findById(coupon.getCouponId()).isEmpty()) {
            System.out.println("this coupons is not exist");
            return;
        }

        coupon.setPrice(price);
        couponRepository.save(coupon);

    }

    public String getCompanyDetails() {
        return companyRepository.findById(companyId).toString();
    }

    public List<Coupon> getByMaxPrice(int price) {
        return couponRepository.findByCompany(companyRepository.getById(companyId)).stream().filter(coupon -> coupon.getPrice()<=price).collect(Collectors.toList());
    }

    public List<Coupon> getCouponBetweenByDate(LocalDateTime start, LocalDateTime end) {
        return couponRepository.findByCompany(companyRepository.getById(companyId)).stream().filter(coupon -> coupon.getEndDate().isBefore(end) && coupon.getStartDate().isAfter(start)).collect(Collectors.toList());
    }


    public void setCompanyId(int company_Id) {
        companyId = company_Id;
    }

    public int getCompanyId() {
        return this.companyId;
    }

}