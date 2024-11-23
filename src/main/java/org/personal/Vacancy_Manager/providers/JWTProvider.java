package org.personal.Vacancy_Manager.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {
    @Value("${security.token.secret}")
    private String secret;

    public DecodedJWT validateToken(String token) {
        String rawToken = token.split("\\s")[1];
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm).build().verify(rawToken);
        } catch (JWTVerificationException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
