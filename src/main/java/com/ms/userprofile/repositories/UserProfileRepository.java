package com.ms.userprofile.repositories;

import com.ms.userprofile.models.UserProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfileModel, UUID> {
}
