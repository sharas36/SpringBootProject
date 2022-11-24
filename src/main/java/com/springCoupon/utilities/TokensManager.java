package com.springCoupon.utilities;


import com.springCoupon.exception.CouponSystemException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Scope("singleton")
@EnableScheduling
public class TokensManager {

    private static final HashMap<String, Token> tokenList = new HashMap<>();

    private static String lock = "lock";

    public static String loginToken(String email, String password, ClientType clientType, int id) throws CouponSystemException {
        Token token = new Token(email, password, clientType, id);
        System.out.println(token.getToken());
        tokenList.put(token.getToken(), token);
        return token.getToken();
    }

    public static void tokenCheck(String token, ClientType clientType) throws CouponSystemException {

        if (!tokenList.containsKey(token)) {
            throw new CouponSystemException(" your time is expired");
        }
        if (!tokenList.get(token).getExpandedJwt().getBody().get("clientType").toString().equals(clientType.toString())) {

            throw new CouponSystemException("you cant get this page");
        }

    }

    public static Integer getIdFromToken(String token) {
        return Integer.valueOf(tokenList.get(token).getExpandedJwt().getBody().getId());
    }

    public static List<Token> getTokenList() {
        List<Token> list = new ArrayList<Token>(tokenList.values());

        return list;
    }

    public static void removeToken(Token token) {
        synchronized (lock) {
            tokenList.remove(token.getToken());
        }

    }
}
