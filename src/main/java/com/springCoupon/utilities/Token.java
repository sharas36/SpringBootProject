package com.springCoupon.utilities;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@Component
public class Token {

    private byte[] secretKeyEncoded;
    private byte[] secretKeyDecoded;
    private String algorithm;
    private Key key;
    private Date expirationTime;
    private String token;
    private JwtParser jwtParser;
    private Jws<Claims> expandedJwt;
    private ClientType clientType;

    public Token() {

    }

    public Token(String email, String password, ClientType clientType, int id) {
        this.clientType = clientType;
        this.secretKeyEncoded = "this+is+my+key+and+it+must+be+at+least+256+bits+long".getBytes();
        this.secretKeyDecoded = Base64.getDecoder().decode(secretKeyEncoded);
        this.algorithm = SignatureAlgorithm.HS256.getJcaName();
        this.key = new SecretKeySpec(secretKeyDecoded, algorithm);
        Instant now = Instant.now();
        this.expirationTime = Date.from(now.plus(30, ChronoUnit.MINUTES));
        token = Jwts.builder()
                .signWith(key)
                .setIssuedAt(Date.from(now))
                .setExpiration(expirationTime)
                .setId(Integer.toString(id))
                .setSubject(email)
                .claim("clientType", clientType.name())
                .claim("clientPassword", password)
                .compact();

        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        expandedJwt = jwtParser.parseClaimsJws(token);

    }


}
