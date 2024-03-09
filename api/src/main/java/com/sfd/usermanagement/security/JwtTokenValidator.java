package com.sfd.usermanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@Component
@Log4j2
public class JwtTokenValidator {
    @Value("${security.secret.salt}")
    private String signingKey;

    public void validate(String jwtToken) {
        try {
            Jws<Claims> jwt = parseJwt(jwtToken);
            if(!StringUtils.hasText(jwt.getBody().getSubject())
                    || Boolean.parseBoolean(Objects.toString(jwt.getBody().get("isRefreshToken")))) {
                throw new InvalidCredentialsException("Provided Token is not valid. Help: Check if token is expired or tempered", HttpStatus.UNAUTHORIZED.value());
            }
        } catch (Exception e) {
            throw new InvalidCredentialsException("Could not validate Json Token", e, HttpStatus.UNAUTHORIZED.value());
        }
    }

    public Jws<Claims> parseJwt(String jwtToken) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(jwtToken);
    }
}
