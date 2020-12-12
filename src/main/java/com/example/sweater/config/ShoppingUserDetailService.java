package com.example.sweater.config;

import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ShoppingUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    public ShoppingUserDetailService(User user) {

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(s);
        return new ShoppingUserDetails(user);
    }
}
