package com.ecommerce.ordermanagement.service;

import com.ecommerce.ordermanagement.dto.UserRegistrationDto;
import com.ecommerce.ordermanagement.entity.User;
import com.ecommerce.ordermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword()); // In a real app, password should be encrypted

        return userRepository.save(user);
    }
}