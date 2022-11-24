package com.springCoupon;

import com.springCoupon.Entities.Company;
import com.springCoupon.Entities.Coupon;
import com.springCoupon.Repositories.CouponRepository;
import com.springCoupon.Services.MainService;
import org.jboss.jandex.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class SpringCouponApplicationTests {
    @Autowired
    CouponRepository couponRepository;

    @Test
    void contextLoads() {
        Company company = new Company();
        company.setCompanyId(1);
        Page<Coupon> page = couponRepository.findByCompanyAndCategoryId(company, 3, PageRequest.of(0, 8));
        page.forEach(System.out::println);
        System.out.println(page.getTotalElements());
    }

    @Test
    void checkRepository() {
        Company company = new Company();
        company.setCompanyId(1);

        int year = 2025;
        int month = new Random().nextInt(11) + 1;
        int day = new Random().nextInt(27) + 1;
        int hour = new Random().nextInt(23) + 1;
        int minute = new Random().nextInt(58) + 1;
        int price = new Random().nextInt(239) + 1;
        int categoryId = new Random().nextInt(9) + 1;
        int amount = new Random().nextInt(499) + 1;

        LocalDateTime endLocalDateTime = LocalDateTime.of(year, month, day, hour, minute);
        LocalDateTime startDateTime = LocalDateTime.of(year - 1, month, day, hour, minute);

        System.out.println("end" + endLocalDateTime.toString() + " start:" + startDateTime.toString());

        Page<Coupon> couponPage = couponRepository.findByEndDateBetweenAndCompany(startDateTime, endLocalDateTime, company,
                (org.springframework.data.domain.Pageable) PageRequest.of(0, 10));

        System.out.println(couponPage.getTotalElements());
        couponPage.forEach(System.out::println);

    }

}
