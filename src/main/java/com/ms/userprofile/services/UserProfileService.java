package com.ms.userprofile.services;

import com.ms.userprofile.models.UserProfileModel;
import com.ms.userprofile.producer.UserProfileProducer;
import com.ms.userprofile.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    final UserProfileRepository userProfileRepository;
    final UserProfileProducer userProfileProducer;

    public UserProfileService(UserProfileRepository userProfileRepository, UserProfileProducer userProfileProducer) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileProducer = userProfileProducer;
    }

    @Transactional
    public UserProfileModel save(UserProfileModel userProfileModel) {
        userProfileRepository.save(userProfileModel);
        userProfileProducer.publishMessageNotification(userProfileModel);
        return userProfileModel;
    }
}
