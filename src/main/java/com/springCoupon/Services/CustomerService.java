package com.springCoupon.Services;

import com.springCoupon.Entities.Coupon;
import com.springCoupon.Entities.Customer;
import com.springCoupon.exception.CouponSystemException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService extends MainService {

    int customerId = 7;

    public void loginCustomer(String email, String password) throws CouponSystemException {

        if (!customerRepository.findByEmailAndPassword(email, password).isPresent()) {
            throw new CouponSystemException("your email or password not match");
        }

        setCustomerId(customerRepository.findByEmailAndPassword(email, password).get().getCustomerId());
    }

    public void addPurchase(int couponId) throws CouponSystemException {

        if (!customerRepository.findById(this.customerId).isPresent() || !couponRepository.findById(couponId).isPresent()) {
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

    public List<Coupon> getAllCustomersCoupons() {
        Customer customer = customerRepository.getById(customerId);
        List<Coupon> couponsList = customer.getCoupons();

        return couponsList;
    }

    public List<Coupon> getAllCustomersCouponsByCategory(int categoryId) {
        List<Coupon> couponByCategory = customerRepository.getById(customerId).getCoupons();
        couponByCategory = couponByCategory.stream().filter(c -> c.getCategoryId() == categoryId).collect(Collectors.toList());
        return couponByCategory;
    }

    public List<Coupon> getAllCustomersCouponsByMaxPrice(int maxPrice) {
        List<Coupon> couponByMaxPrice = customerRepository.getById(customerId).getCoupons();
        couponByMaxPrice = couponByMaxPrice.stream().filter(c -> c.getPrice() <= maxPrice).collect(Collectors.toList());
        return couponByMaxPrice;
    }

    public String getCustomerDetails() {

        String customerDetails = "";
        Customer customer = customerRepository.findById(this.customerId).get();

        customerDetails = "first Name: " + customer.getFirstName() + " \n Last Name:  " +
                customer.getLastName() + "\n Email: " + customer.getEmail();

        return customerDetails;
    }

    public void saveByCoupon(int couponId) {
        Coupon coupon = couponRepository.getById(couponId);
        coupon.addCustomer(customerRepository.getById(this.customerId));
        couponRepository.save(coupon);

    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


}
