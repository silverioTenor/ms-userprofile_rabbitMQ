package com.ms.userprofile.repositories;

import com.ms.userprofile.models.UserProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileModel, UUID> {

    Optional<UserProfileModel> findByEmail(String email);
}
