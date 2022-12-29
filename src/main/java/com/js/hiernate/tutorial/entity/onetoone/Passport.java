package com.js.hiernate.tutorial.entity.onetoone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @EqualsAndHashCode.Include
    private UUID passportUuid;

    @EqualsAndHashCode.Include
    private Instant creationDate;

    @EqualsAndHashCode.Include
    private Instant validUntil;

    @OneToOne(mappedBy = "passport")
    private Citizen citizen;
}
