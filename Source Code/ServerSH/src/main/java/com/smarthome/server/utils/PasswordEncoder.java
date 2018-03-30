package com.smarthome.server.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//public final class PasswordEncoder {
//    private static BCryptPasswordEncoder passwordEncoder;
//
//    static {
//        passwordEncoder = new BCryptPasswordEncoder();
//    }
//
//    public static String encode(String rawPassword) {
//        return passwordEncoder.encode(rawPassword);
//    }
//
//    public static Boolean matches(String rawPassword, String encryptedPassword) {
//        return passwordEncoder.matches(rawPassword, encryptedPassword);
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder;
//    }
//}
