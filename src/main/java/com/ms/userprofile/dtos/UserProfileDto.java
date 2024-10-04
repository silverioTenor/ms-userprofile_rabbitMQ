package com.ms.userprofile.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserProfileDto(@NotBlank String name, @NotBlank @Email String email) {
}
