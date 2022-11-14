package com.springCoupon.exception;

public class CouponSystemException extends Exception {

    String exception;

    public CouponSystemException(String exception) {
        this.exception = exception;
    }


    public String getException() {
        return exception;
    }
}

