package org.personal.Vacancy_Manager.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.personal.Vacancy_Manager.modules.company.entities.CompanyEntity;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TestUtils {
    public static String objectToJson(Object object) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static String generateToken(UUID companyId, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresAt = Instant.now().plus(Duration.ofHours(1));

        return JWT.create().withIssuer("Company S/A").withSubject(companyId.toString()).withClaim("roles", List.of(
                "COMPANY")).withExpiresAt(expiresAt).sign(algorithm);
    }

    public static CompanyEntity generateTestCompany() {
        var company = new CompanyEntity();

        company.setId(UUID.randomUUID());
        company.setUsername("test-company");
        company.setPassword("12345678");
        company.setName("Test Company S/A");
        company.setCnpj("XXXXXXXXXXXXXX");
        company.setEmail("test@email.com");
        company.setWebsite("test.com.br");
        company.setDescription("Test company to be used in integration tests");

        return company;
    }

}
