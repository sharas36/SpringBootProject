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
@NoArgsConstructor
@Builder
@Data
@Component
public class Token {

    private final byte[] secretKeyEncoded = "this+is+my+key+and+it+must+be+at+least+256+bits+long".getBytes();
    private final byte[] secretKeyDecoded = Base64.getDecoder().decode(secretKeyEncoded);
    private final String algorithm = SignatureAlgorithm.HS256.getJcaName();
    private final Key key = new SecretKeySpec(secretKeyDecoded, algorithm);
    private final Instant now = Instant.now();
    private final Date expirationTime = Date.from(now.plus(30, ChronoUnit.MINUTES));
    private String token;
    private final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    private final Jws<Claims> expandedJwt = jwtParser.parseClaimsJws(token);

    public Token(String email, String password, ClientType clientType) {
        token = Jwts.builder()
                .signWith(key)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.SECONDS)))
                .setId("1")
                .setSubject(email)
                .claim("clientType", clientType.name())
                .claim("clientPassword", password)
                .compact();


    }
}
