package com.ssq.invoice.security.jwt;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import static com.ssq.invoice.constant.SecurityConstant.JWT_ISSUER;

@Component
public class JweUtil {
    @Autowired
    KeyStoreManager keyStoreManager;

    public String decryptPasswordInJwe(String password) {

        RSADecrypter decrypter = new RSADecrypter((RSAPrivateKey) keyStoreManager.getPrivateKey());

        String decryptedPw = "";

        try {
            EncryptedJWT jwt = EncryptedJWT.parse(password);
            jwt.decrypt(decrypter);
            decryptedPw = jwt.getJWTClaimsSet().getClaim("password").toString();
            System.out.println("Decryption pw: " + decryptedPw);
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
        }
        return decryptedPw;
    }

}
