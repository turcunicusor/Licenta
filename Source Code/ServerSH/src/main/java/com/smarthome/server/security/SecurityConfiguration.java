package com.smarthome.server.security;

import com.smarthome.server.service.DeviceManager;
import com.smarthome.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(1000)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String[] ROUTES_SWAGGER = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };
    private static final String[] ROUTES_ALLOWED = {
            "/register",
            "/roles/add",
            "/" //never delete that
    };
    private final DataSource dataSource;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final DeviceManager deviceManager;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    public SecurityConfiguration(@Qualifier("dataSource") DataSource dataSource,
                                 BCryptPasswordEncoder passwordEncoder, UserService userService,
                                 DeviceManager deviceManager) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.deviceManager = deviceManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(); // magic line
        http.csrf().disable().authorizeRequests()
                .antMatchers(ROUTES_SWAGGER).permitAll()
                .antMatchers(ROUTES_ALLOWED).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), userService, deviceManager),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }
}
