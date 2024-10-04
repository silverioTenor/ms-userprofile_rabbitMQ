package com.ms.userprofile.producer;

import com.ms.userprofile.dtos.NotificationDto;
import com.ms.userprofile.models.UserProfileModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProfileProducer {

    final RabbitTemplate rabbitTemplate;

    public UserProfileProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.notification.name}")
    private String routingKey;

    public void publishMessageNotification(UserProfileModel userProfileModel) {
        var notificationDto = new NotificationDto();
        notificationDto.setUserId(userProfileModel.getUserId());
        notificationDto.setEmailTo(userProfileModel.getEmail());
        notificationDto.setSubject("Cadastro realizado com sucesso!");
        notificationDto.setText(
                userProfileModel.getName() +
                        ", seja bem vindo(a)! " +
                        "\nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma"
        );

        rabbitTemplate.convertAndSend("", routingKey, notificationDto);
    }
}
