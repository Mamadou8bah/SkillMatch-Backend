package SkillMatch.service;

import SkillMatch.model.SecureToken;
import SkillMatch.repository.SecureTokenRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SecureTokenService {
    private static BytesKeyGenerator DEFAULT_TOKEN_GENERATOR= KeyGenerators.secureRandom(12);
    @Value("${token.validity.minutes:1440}") // 24 hours default
    private int tokenValidityInMinutes;
    @Autowired
    private SecureTokenRepository repository;

    public SecureToken saveToken(SecureToken token){
        return repository.save(token);
    }
    public SecureToken findByToken(String token){
        return repository.findByToken(token);
    }
    public void removeToken(SecureToken token){
        repository.delete(token);
    }
    public SecureToken createToken(){
        String tokenValue=new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()));
        SecureToken secureToken=new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpiredAt(LocalDateTime.now().plusMinutes(tokenValidityInMinutes));
        this.saveToken(secureToken);
        return secureToken;
    }

}
