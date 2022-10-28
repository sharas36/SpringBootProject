package com.springCoupon.utilities;


import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class TokensList {

    private HashMap<String, Date> tokenList;

    public TokensList() {

        Thread tokensWork = new Thread(() -> {
            while (true) {
                HashMap<String, Date> existTokens = this.tokenList;
                existTokens.forEach((s, date) -> {
                    if (date.after(Date.from(Instant.now()))) {
                        tokenList.remove(s);
                    }
                });
                try {
                    Thread.sleep(60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tokensWork.start();
    }
    public void addToken(Token token){
        tokenList.put(token.getToken(), token.getExpirationTime());
    }

}
