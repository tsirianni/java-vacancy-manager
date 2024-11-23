package org.personal.Vacancy_Manager.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.personal.Vacancy_Manager.modules.company.dto.AuthCompanyDTO;
import org.personal.Vacancy_Manager.modules.company.dto.AuthCompanyResponseDTO;
import org.personal.Vacancy_Manager.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secret;

    @Autowired
    private CompanyRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company =
                repository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not " +
                                                                                                                                "found"));

        var passwordsAreEqual = passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if (!passwordsAreEqual) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresAt = Instant.now().plus(Duration.ofHours(1));
        var token = JWT.create().withIssuer(company.getName()).withSubject(company.getId().toString()).withClaim("roles", Arrays.asList(
                "COMPANY")).withExpiresAt(expiresAt).sign(algorithm);

        return new AuthCompanyResponseDTO(token, expiresAt.toEpochMilli());
    }
}
