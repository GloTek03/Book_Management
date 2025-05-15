package com.ltfullstack.commonservice.service;

import freemarker.core.ParseException;
import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;
    /**
     *Send an email with optional HTML and attachment File
     *
     * @param to            The emails of receiver
     * @param subject       Description about email
     * @param text          Content email
     * @param isHtml        Where the email body is HTML
     * @param attachment    File attach to email
     */
    public void sendEmail(String to, String subject, String text, boolean isHtml, File attachment) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            if(attachment != null){
                FileSystemResource fileSystemResource = new FileSystemResource(attachment);
                helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
            }

            javaMailSender.send(message);
            log.info("Email send successfully to {}", to);
        }catch (MessagingException ex){
            log.error("Failed to send email to {}", to, ex);
        }
    }

    public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> placeholders
            , File attachment){
        try{
            Template template = configuration.getTemplate(templateName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, placeholders);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html,true);

            if(attachment != null){
                FileSystemResource fileSystemResource = new FileSystemResource(attachment);
                helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
            }

            javaMailSender.send(message);
            log.info("Email send successfully to {}", to);

        }catch (MessagingException | IOException | TemplateException ex){
            log.error("Failed to send email to {}", to, ex);
        }
    }
}
