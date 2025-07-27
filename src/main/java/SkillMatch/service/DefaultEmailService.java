package SkillMatch.service;

import SkillMatch.util.AbstractEmailContext;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
public class DefaultEmailService implements EmailService{
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Override
    public void sendMail(AbstractEmailContext email) throws MessagingException {
        MimeMessage mimeMessage=emailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        
        Context context=new Context();
        context.setVariables(email.getContext());
        String emailContent=templateEngine.process(email.getTemplateLocation(),context);
        
        messageHelper.setTo(email.getTo());
        messageHelper.setFrom(email.getFrom());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(emailContent, true); // true indicates HTML content

        emailSender.send(mimeMessage);
    }
}
