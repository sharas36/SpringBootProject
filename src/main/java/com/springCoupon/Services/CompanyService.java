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


    public Integer loginCheck(String email, String password) throws CouponSystemException {

        if (!companyRepository.findByEmailAndPassword(email, password).isEmpty()) {
           return companyRepository.findByEmailAndPassword(email, password).get(0).getCompanyId();
        }
        throw new CouponSystemException("Something wrong. Please try again");
    }

    public Page<Coupon> getCouponsOfCompany(String token, org.springframework.data.domain.Pageable pageable) {
        int id = getIdFromToken(token);
        return couponRepository.findByCompany(companyRepository.getById(id), pageable);
    }

    public Page<Coupon> findByCompanyIdAndCategoryId(int categoryId, String token, org.springframework.data.domain.Pageable pageable) {
        int id = getIdFromToken(token);
        return couponRepository.findByCompanyIdAndCategoryId(id, categoryId, pageable);
    }

    public Coupon addCoupon(Coupon coupon, String token) throws CouponSystemException {
        int id = getIdFromToken(token);
        if (!couponRepository.findByCouponNameAndCompany(coupon.getCouponName(), companyRepository.getById(id)).isEmpty()) {
            throw new CouponSystemException("the same name belongs to the same company");
        }
        coupon.setCompany(companyRepository.getById(id));
        return couponRepository.save(coupon);
    }

    public void deletedCoupon(int couponId) throws CouponSystemException {

        if (couponRepository.findById(couponId).isEmpty()) {
            throw new CouponSystemException("this coupons is not exist");
        } else {
            couponRepository.deleteCoupon(couponId);
        }
    }

    public void updateCoupon(Coupon coupon) throws CouponSystemException {

        if (couponRepository.findById(coupon.getCouponId()).isEmpty()) {
            throw new CouponSystemException("this coupons is not exist");
        }

        couponRepository.save(coupon);

    }

    public String getCompanyDetails(String token) {
        int id = getIdFromToken(token);
        return companyRepository.findById(id).toString();
    }

    public List<Coupon> getByMaxPrice(int price, String token, org.springframework.data.domain.Pageable pageable) {
        int id = getIdFromToken(token);
        return couponRepository.findByCompany(companyRepository.getById(id), pageable).stream().filter(coupon -> coupon.getPrice() <= price).collect(Collectors.toList());
    }

    public List<Coupon> getCouponBetweenByDate(LocalDateTime start, LocalDateTime end, String token, org.springframework.data.domain.Pageable pageable) {
        int id = getIdFromToken(token);
        return couponRepository.findByCompany(companyRepository.getById(id), pageable)
                .stream().filter(coupon -> coupon.getEndDate().isBefore(end) && coupon.getStartDate().isAfter(start)).collect(Collectors.toList());
    }

    public Coupon addCoupon(Coupon coupon, int companyId) throws CouponSystemException {
        if (!couponRepository.findByCouponNameAndCompany(coupon.getCouponName(), companyRepository.getById(companyId)).isEmpty()) {
            throw new CouponSystemException("the same name belongs to the same company");
        }
        coupon.setCompany(companyRepository.getById(companyId));
        return couponRepository.save(coupon);
    }

    public int countCouponsByCategory(int categoryId, int id){
//        int id = getIdFromToken(token);
        return couponRepository.countByCategoryId(categoryId, id);
    }
}
