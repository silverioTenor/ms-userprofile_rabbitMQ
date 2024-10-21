package com.ms.userprofile.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDTO(@NotBlank @Email String email, @NotBlank String password) {
}
