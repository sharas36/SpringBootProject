package com.springCoupon.utilities;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class DailyJob {
    // annotation @scheduler - spring boot to check

    @Autowired
    CouponRepository couponRepository;

    public void startDailyJob() {

        dailyJob();
    }

    @Scheduled(fixedDelay = 24 * 60 * 60)
    public void dailyJob() {

        List<Coupon> couponsToDelete = couponRepository.findByEndDateLessThan(LocalDateTime.now());

        couponsToDelete.forEach(coupon -> {
            couponRepository.deletePurchasesOfCustomer(coupon.getCouponId());
            couponRepository.deleteCoupon(coupon.getCouponId());

        });
    }
}