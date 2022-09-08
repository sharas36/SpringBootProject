package com.springCoupon.utilities;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class DailyJob {

    @Autowired
    CouponRepository couponRepository;


    int timeToSleep = 24 * 60 * 60;

    Thread thread = new Thread(() -> {

        while (true) {

            List<Coupon> couponList = couponRepository.findAll();
            List<Coupon> couponsToDelete = couponList.stream().filter(coupon -> {
                return coupon.getEndDate().isBefore(LocalDateTime.now());}).collect(Collectors.toList());

            couponsToDelete.forEach(coupon -> {
                couponRepository.deleteById(coupon.getCouponId());
            });


            try {
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    });

    public void startDailyJob() {
    thread.start();
    }

}