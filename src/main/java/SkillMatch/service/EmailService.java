package SkillMatch.service;

import SkillMatch.util.AbstractEmailContext;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendMail(final AbstractEmailContext email) throws MessagingException;
}
