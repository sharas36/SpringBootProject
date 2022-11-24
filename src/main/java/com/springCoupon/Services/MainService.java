package com.springCoupon.Services;


import com.springCoupon.Repositories.CompanyRepository;
import com.springCoupon.Repositories.CouponRepository;
import com.springCoupon.Repositories.CustomerRepository;
import com.springCoupon.exception.CouponSystemException;
import com.springCoupon.utilities.ClientType;
import com.springCoupon.utilities.Token;
import com.springCoupon.utilities.TokensList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class MainService {


    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private TokensList tokensList;

    public String loginToken(String email,String password,ClientType clientType, int id) throws CouponSystemException{
        Token token = new Token(email, password, clientType, id);
        System.out.println(token.getToken());
        tokensList.addToken(token);
        return token.getToken();
    }

    public void tokenCheck(String token, ClientType clientType) throws CouponSystemException {
        if(!tokensList.isThisTokenExist(token)){
            throw new CouponSystemException("your time is expired");
        }
        if(!tokensList.getToken(token).getClientType().equals(clientType)){
            throw new CouponSystemException("you cant get this page");
        }
    }

    public Integer getIdFromToken(String token){
        return Integer.valueOf(tokensList.getToken(token).getExpandedJwt().getBody().getId());
    }
}
