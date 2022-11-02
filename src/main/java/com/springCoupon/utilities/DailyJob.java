package com.springCoupon.utilities;


import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.Repositories.CouponRepository;
import com.springCoupon.Repositories.CustomerRepository;
import com.springCoupon.Services.MainService;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DailyJob {
    // annotation @scheduler - spring boot to che

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CustomerRepository customerRepository;

    int timeToSleep = 24 * 60 * 60;


    Thread thread = new Thread(() -> {
        List<Coupon> couponList = couponRepository.findByEndDateLessThan(LocalDateTime.now());




        try {
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    public void startDailyJob() {

        thread.start();

    }

}