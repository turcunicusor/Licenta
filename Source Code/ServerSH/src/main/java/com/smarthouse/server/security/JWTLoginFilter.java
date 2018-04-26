package com.smarthouse.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthouse.server.dtos.LoginDTO;
import com.smarthouse.server.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final UserService userService;

    JWTLoginFilter(String url, AuthenticationManager authManager, UserService userService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        LoginDTO creds = new ObjectMapper().readValue(req.getInputStream(), LoginDTO.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                        creds.getEmail(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        TokenAuthenticationService.addAuthentication(res, auth.getName());
        userService.loggedIn(auth.getName());
    }
}
