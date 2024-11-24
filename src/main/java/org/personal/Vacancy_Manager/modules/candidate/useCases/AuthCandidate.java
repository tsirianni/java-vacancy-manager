package org.personal.Vacancy_Manager.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.personal.Vacancy_Manager.modules.candidate.repository.CandidateRepository;
import org.personal.Vacancy_Manager.modules.candidate.dto.AuthCandidateRequestDTO;
import org.personal.Vacancy_Manager.modules.candidate.dto.AuthCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidate {

    @Value("${security.token.secret.candidate}")
    private String secret;

    @Autowired
    private CandidateRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate =
                this.repository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(() -> new UsernameNotFoundException(
                        "Username/password incorrect"));

        var passwordsAreEqual = passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());
        if (!passwordsAreEqual) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresAt = Instant.now().plus(Duration.ofHours(1));
        String token = JWT.create().withIssuer(candidate.getName()).withSubject(candidate.getId().toString()).withClaim("roles", List.of(
                "CANDIDATE")).withExpiresAt(expiresAt).sign(algorithm);

        return new AuthCandidateResponseDTO(token, expiresAt.toEpochMilli());
    }
}
