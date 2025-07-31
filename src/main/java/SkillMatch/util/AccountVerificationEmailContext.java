package SkillMatch.util;

import SkillMatch.model.User;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext{
    private String token;
    
    @Override
    public <T> void init(T context) {
        User user= (User) context;
        put("firstName",user.getFullName());
        setTemplateLocation("mailing/email-verification");
        setSubject("Verify Your Email");
        setFrom("noreply@skillmatch.com"); // Use a proper no-reply email
        setTo(user.getEmail());
    }

    public void setToken(String token){
        this.token=token;
        put("token",token);
    }

    public void buildVerificationUrl(final String baseURL,final String token){
        final String url= UriComponentsBuilder.fromUriString(baseURL).path("/register/verify").queryParam("token",token).toUriString();
        put("verificationURL",url);
    }
}
