package com.ms.userprofile.services;

import com.ms.userprofile.models.UserDetailsImplModel;
import com.ms.userprofile.models.UserProfileModel;
import com.ms.userprofile.repositories.UserProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserProfileRepository userProfileRepository;

    public UserDetailsServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfileModel userProfileModel = userProfileRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return new UserDetailsImplModel(userProfileModel);
    }
}
