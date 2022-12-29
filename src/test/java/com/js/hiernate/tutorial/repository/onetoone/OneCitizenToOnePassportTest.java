package com.js.hiernate.tutorial.repository.onetoone;

import com.js.hiernate.tutorial.PostgresInitializer;
import com.js.hiernate.tutorial.entity.onetoone.Citizen;
import com.js.hiernate.tutorial.entity.onetoone.Gender;
import com.js.hiernate.tutorial.entity.onetoone.Passport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OneCitizenToOnePassportTest extends PostgresInitializer {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private PassportRepository passportRepository;

    @BeforeEach
    void cleanUp() {
        citizenRepository.deleteAll();
        passportRepository.deleteAll();
    }

    @Test
    @DisplayName("Save Passport first, then Citizen")
    void save() {
        final var now = Instant.now();
        final var passport = Passport.builder()
                                     .passportUuid(UUID.randomUUID())
                                     .creationDate(now)
                                     .validUntil(now.plus(60, ChronoUnit.DAYS))
                                     .build();

        final var savedPassport = passportRepository.save(passport);
        assertThat(savedPassport.getId()).isNotZero();

        final var citizen = Citizen.builder()
                                   .firstName("Michael")
                                   .lastName("Scott")
                                   .gender(Gender.MALE)
                                   .passport(passport)
                                   .build();

        final var savedCitizen = citizenRepository.save(citizen);
        assertThat(savedCitizen).isEqualTo(citizen);
        assertThat(savedCitizen.getId()).isNotZero();
    }
}