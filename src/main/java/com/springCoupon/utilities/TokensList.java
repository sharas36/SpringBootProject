package com.springCoupon.utilities;


import com.springCoupon.exception.CouponSystemException;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokensList {


    private void tokensWork() {

        Thread tokensList = new Thread(() -> {
            int i = 0;
            while (true) {

                System.out.println("thread is starting" + ++i);

                ArrayList<Token> expiredTokens = (ArrayList<Token>) TokensManager.getTokenList()
                        .stream()
                        .filter(token -> {
                            return token.getExpirationTime().before(Date.from(Instant.now()));
                        })
                        .collect(Collectors.toList());

                if (!expiredTokens.isEmpty()) {
                    expiredTokens.forEach(token -> {
                        TokensManager.removeToken(token);
                    });
                }

                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        });
        tokensList.start();

    }

    @PostConstruct
    public void startTokenListJob() {
        tokensWork();
    }


}
