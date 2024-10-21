package com.ms.userprofile.services;

import com.ms.userprofile.dtos.CreateUserProfileDTO;
import com.ms.userprofile.dtos.JwtTokenDTO;
import com.ms.userprofile.dtos.LoginUserDTO;
import com.ms.userprofile.models.RoleModel;
import com.ms.userprofile.models.UserDetailsImplModel;
import com.ms.userprofile.models.UserProfileModel;
import com.ms.userprofile.producer.UserProfileProducer;
import com.ms.userprofile.repositories.UserProfileRepository;
import com.ms.userprofile.security.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserProfileService {

    final UserProfileRepository userProfileRepository;

    final UserProfileProducer userProfileProducer;

    final SecurityConfig securityConfig;

    final AuthenticationManager authenticationManager;

    final JwtTokenService jwtTokenService;

    public UserProfileService(
            UserProfileRepository userProfileRepository,
            UserProfileProducer userProfileProducer,
            SecurityConfig securityConfig,
            AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService
    ) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileProducer = userProfileProducer;
        this.securityConfig = securityConfig;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Transactional
    public UserProfileModel save(CreateUserProfileDTO userProfileDTO) {
//      BeanUtils.copyProperties(userProfileDTO, userProfileModel);

        var userProfileModel = UserProfileModel.builder()
                .name(userProfileDTO.name())
                .email(userProfileDTO.email())
                .password(securityConfig.passwordEncoder().encode(userProfileDTO.password()))
                .roles(List.of(RoleModel.builder().name(userProfileDTO.role()).build()))
                .build();

        userProfileRepository.save(userProfileModel);
        userProfileProducer.publishMessageNotification(userProfileModel);

        return userProfileModel;
    }

    public JwtTokenDTO authenticateUser(LoginUserDTO loginUserDTO) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDTO.email(),
                loginUserDTO.password()
        );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImplModel userDetailsImplModel = (UserDetailsImplModel) authentication.getPrincipal();

        return new JwtTokenDTO(jwtTokenService.generateToken(userDetailsImplModel));
    }
}
