package com.my.bookingsystem.service.serviceimpl;
import com.my.bookingsystem.model.entity.CustomUserDetails;
import com.my.bookingsystem.model.entity.User;
import com.my.bookingsystem.model.response.ResponseFormat;
import com.my.bookingsystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ResponseFormat responseFormat=null;
       Optional<User> userOptional = userRepository.findByEmail(email);
       if(userOptional.isPresent()){
           return new CustomUserDetails(userOptional.get());
       }
       return null;
    }
}