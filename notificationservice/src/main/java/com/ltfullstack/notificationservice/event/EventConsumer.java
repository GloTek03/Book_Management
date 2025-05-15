package com.ltfullstack.notificationservice.event;

import com.ltfullstack.commonservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EventConsumer {

    @Autowired
    private EmailService emailService;

    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000,multiplier = 2),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetryException.class, RuntimeException.class}
    )
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void  listen(String message){
        log.info("Received message: {}", message);
        throw new RuntimeException("Error Test");
    }

    @DltHandler
    void processDltMessage(@Payload String message){
        log.info("DLT received message: {}",message);
    }

    @KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
            attempts = "2",
            backoff = @Backoff(delay = 1000,multiplier = 1),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetryException.class, RuntimeException.class}
    )
    public void  testEmail(String message){
        log.info("Received message: {}", message);
        //emailService.sendEmail("sonnguyen0610031@gmail.com", "Test mail flow", message);
    }

    @KafkaListener(topics = "emailTemplate", containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
            attempts = "2",
            backoff = @Backoff(delay = 1000,multiplier = 1),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            include = {RetryException.class, RuntimeException.class}
    )
    public void  emailTemplate(String message){
        log.info("Received message: {}", message);

        Map<String, Object> placeholders = new HashMap<>();
        placeholders.put("name", "Msr.Thuy");
        emailService.sendEmailWithTemplate("sonnguyen0610031@gmail.com", "MotherDay",
                "emailTemplate.ftl", placeholders, null);
    }
}
