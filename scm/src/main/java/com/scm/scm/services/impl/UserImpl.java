package com.scm.scm.services.impl;

import com.scm.scm.entities.User;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.helper.ResourceNotFoundException;
import com.scm.scm.repository.UserRepo;
import com.scm.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRolelist(List.of(AppConstants.ROLE_USER));
        return userRepo.save(user);
    }


    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user1=userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("user not found"));
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setAbout(user.getAbout());
        user1.setPassword(user.getPassword());
        user1.setProfilePic(user.getProfilePic());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setEnabled(user.isEnabled());
        user1.setEmailVerified(user.isEmailVerified());
        user1.setProviderUserId(user.getProviderUserId());
        user1.setPhoneVerified(user.isPhoneVerified());
        user1.setProvider(user.getProvider());
       User user2= userRepo.save(user1);
        return Optional.of(user2);
    }

    @Override
    public void deleteUser(String id) {
        User user1=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found"));
    }

    @Override
    public boolean isUserExist(String userId) {
        User user1=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
        return user1 != null ;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user1=userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found"));
        return user1 != null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
