package com.food.delivery.services.impl;

import com.food.delivery.entities.User;
import com.food.delivery.model.AppUserDetails;
import com.food.delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: Md. Tanver Ahammed,
 * ICT, MBSTU
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository alumniRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User alumni = this.alumniRepository.findByEmail(username);
        if (alumni == null)
            throw new UsernameNotFoundException("User details not found for this user: " + username);
        return new AppUserDetails(alumni);
    }
}
