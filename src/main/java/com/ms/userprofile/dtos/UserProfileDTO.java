package com.ms.userprofile.dtos;

import com.ms.userprofile.enums.Role;

import java.util.List;
import java.util.UUID;

public record UserProfileDTO(UUID userId, String email, List<Role> roles) {
}
