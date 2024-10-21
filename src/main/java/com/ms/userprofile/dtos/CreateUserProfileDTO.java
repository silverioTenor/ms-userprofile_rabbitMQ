package com.ms.userprofile.dtos;

import com.ms.userprofile.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserProfileDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password,
        Role role
) {
}
