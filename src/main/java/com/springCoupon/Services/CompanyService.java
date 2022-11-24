package com.springCoupon.Services;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.TokensManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService extends MainService {
    private static final int pageSize = 8;

    public Integer loginCheck(String email, String password) throws CouponSystemException {

        if (companyRepository.findByEmailAndPassword(email, password).isPresent()) {
            return companyRepository.findByEmailAndPassword(email, password).get().getCompanyId();
        }
        throw new CouponSystemException("your email or password is wrong");
    }

    public Page<Coupon> getCouponsOfCompany(String token, int pageNum) {
        int id = TokensManager.getIdFromToken(token);
        Page<Coupon> couponList = couponRepository.findByCompany(companyRepository.getById(id),
                (org.springframework.data.domain.Pageable) PageRequest.of(pageNum, pageSize));
        return couponList;
    }

    public Page<Coupon> findByCompanyIdAndCategoryId(int categoryId, String token,int pageNum) {
        int id = TokensManager.getIdFromToken(token);
        return couponRepository.findByCompanyAndCategoryId(companyRepository.getById(id), categoryId,PageRequest.of(pageNum,pageSize));
    }

    public Coupon addCoupon(Coupon coupon, String token) throws CouponSystemException {
        System.out.println("i got here company");
        int id = TokensManager.getIdFromToken(token);
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
            couponRepository.deletePurchasesOfCustomer(couponId);
            couponRepository.deleteCoupon(couponId);
        }
    }

    public void updateCoupon(Coupon coupon, String token) throws CouponSystemException {
        int id = TokensManager.getIdFromToken(token);
        if (couponRepository.findById(coupon.getCouponId()).isEmpty()) {
            throw new CouponSystemException("this coupons is not exist");
        }

        Company company = companyRepository.getById(id);
        coupon.setCompany(company);
        company.addCoupon(coupon);
        couponRepository.save(coupon);

    }

    public String getCompanyDetails(String token) {
        int id = TokensManager.getIdFromToken(token);
        return companyRepository.findById(id).toString();
    }

    public Page<Coupon> getByMaxPrice(double price, String token, int pageNum) {
        int id = TokensManager.getIdFromToken(token);

        return couponRepository.findByPriceLessThanAndCompany(price, companyRepository.findById(id).get(), PageRequest.of(pageNum,
                pageSize));
    }


    public Page<Coupon> getCouponBetweenByDate(LocalDateTime start, LocalDateTime end, String token, int pageNum) {
        System.out.println("i got here");
        int id = TokensManager.getIdFromToken(token);

        Page<Coupon> couponList = couponRepository.findByEndDateBetweenAndCompany(start, end,
                companyRepository.findById(id).get(), PageRequest.of(pageNum, pageSize));

        return couponList;
    }

    public String getCompanyName(String token) {
        int id = TokensManager.getIdFromToken(token);
        return companyRepository.findById(id).get().getCompanyName();
    }

    public Coupon getOneCoupon(String token, int couponId) {
        int id = TokensManager.getIdFromToken(token);
        return couponRepository.findById(couponId).get();
    }


}
