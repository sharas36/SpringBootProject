package com.springCoupon.utilities;


import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
@EnableScheduling
public class TokensList {

    private final ArrayList<Token> tokenList = new ArrayList<>();


    public TokensList() {
        tokensWork();

    }

    @Scheduled(fixedDelay = 60*1000)
    public void tokensWork(){
        Thread tokensWork = new Thread(() -> {
            while (true) {
                ArrayList<Token> existTokens = this.tokenList;
                if(!existTokens.isEmpty()) {
                    existTokens.forEach(token -> {
                        if(token.getExpirationTime().before(Date.from(Instant.now()))){
                            tokenList.remove(token);
                        }
                    });
                }
            }
        });
        tokensWork.start();
    }
    public void addToken(Token token){
        tokenList.add(token);
    }

    public boolean isThisTokenExist(String token){
        return tokenList.contains(getToken(token));
    }

    public Token getToken(String token){
        for (Token t: tokenList) {
            if(t.getToken().equals(token)){
                return t;
            }
        }
        return null;
    }
}
