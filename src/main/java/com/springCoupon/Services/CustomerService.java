package com.springCoupon.Services;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.ClientType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService extends MainService {


    public Integer loginCustomer(String email, String password) throws CouponSystemException {

        if (!customerRepository.findByEmailAndPassword(email, password).isPresent()) {
            throw new CouponSystemException("your email or password not match");
        }

        return customerRepository.findByEmailAndPassword(email, password).get().getCustomerId();

    }

    public void addPurchase(int couponId, String token) throws CouponSystemException {

        int customerId = getIdFromToken(token);
        if (!customerRepository.findById(customerId).isPresent() || !couponRepository.findById(couponId).isPresent()) {
            try {
                throw new CouponSystemException("coupon or customer are not exist");
            } catch (CouponSystemException e) {
                e.printStackTrace();
            }
        }

        Customer customer = customerRepository.getById(customerId);
        Coupon coupon = couponRepository.getById(couponId);

        if (coupon.getAmount() <= 0) {

            throw new CouponSystemException("this coupon is sold out");
        }
        ;

        customer.addCoupon(coupon);
        couponRepository.findById(couponId).get().setAmount(couponRepository.findById(couponId).get().getAmount() - 1);
        couponRepository.save(couponRepository.getById(couponId));
        customerRepository.save(customer);
    }

    public Optional<Coupon> getCouponById(int couponId){
        return couponRepository.findById(couponId);
    }

    public List<Integer> getAllCustomersCoupons(String token) {

        int customerId = getIdFromToken(token);
        return couponRepository.findPurchasesOfCustomer(customerId);
    }

//    public List<Coupon> getAllCustomersCouponsByCategory(int categoryId) {
//        List<Coupon> couponList = couponRepository.findPurchasesOfCustomer(customerId);
//        return couponList.stream().filter(c -> c.getCategoryId() == categoryId).collect(Collectors.toList());
//    }
//
//    public List<Coupon> getAllCustomersCouponsByMaxPrice(int maxPrice) {
//        List<Coupon> couponList = couponRepository.findPurchasesOfCustomer(customerId);
//        return couponList.stream().filter(c -> c.getPrice() <= maxPrice).collect(Collectors.toList());
//    }

    public String getCustomerDetails(String token) {

        int customerId = getIdFromToken(token);
        String customerDetails = "";
        Customer customer = customerRepository.findById(customerId).get();

        customerDetails = "first Name: " + customer.getFirstName() + " \n Last Name:  " +
                customer.getLastName() + "\n Email: " + customer.getEmail();

        return customerDetails;
    }

    public void saveByCoupon(int couponId, String token) {
        int customerId = getIdFromToken(token);
        Coupon coupon = couponRepository.getById(couponId);
        coupon.addCustomer(customerRepository.getById(customerId));
        couponRepository.save(coupon);

    }



//
}
