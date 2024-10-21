package com.ms.userprofile.controllers;

import com.ms.userprofile.dtos.CreateUserProfileDTO;
import com.ms.userprofile.dtos.JwtTokenDTO;
import com.ms.userprofile.dtos.LoginUserDTO;
import com.ms.userprofile.models.UserProfileModel;
import com.ms.userprofile.services.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class  UserProfileController {

    final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfileModel> saveUser(@RequestBody @Valid CreateUserProfileDTO createUserProfileDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.save(createUserProfileDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> signIn(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.authenticateUser(loginUserDTO));
    }
}
