package com.smarthome.server.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {
    private static final long EXPIRATIONTIME = 86_400_000; // 1 day
    private static final String SECRET = "smarthome_secret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse res, String email) {
        String JWT = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET) // HMAC using SHA-512
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        System.out.println(token);
        if (token != null) {
            try {
                String userEmail = TokenAuthenticationService.decodeToken(token);
                return userEmail != null ? new UsernamePasswordAuthenticationToken(userEmail, null, emptyList()) : null;
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }

    public static String decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX.toUpperCase(), " "))
                .getBody()
                .getSubject();
    }
}
