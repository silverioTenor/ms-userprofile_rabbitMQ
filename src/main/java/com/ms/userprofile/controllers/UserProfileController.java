package com.ms.userprofile.controllers;

import com.ms.userprofile.dtos.UserProfileDto;
import com.ms.userprofile.models.UserProfileModel;
import com.ms.userprofile.services.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class  UserProfileController {

    final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserProfileModel> saveUser(@RequestBody @Valid UserProfileDto userProfileDto) {
        var userProfileModel =  new UserProfileModel();
        BeanUtils.copyProperties(userProfileDto, userProfileModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.save(userProfileModel));
    }
}
