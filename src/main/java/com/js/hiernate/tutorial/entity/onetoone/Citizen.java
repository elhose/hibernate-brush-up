package com.js.hiernate.tutorial.entity.onetoone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @EqualsAndHashCode.Include
    private String firstName;

    @EqualsAndHashCode.Include
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Type(type = "com.js.hiernate.tutorial.entity.onetoone.HibernatePostgresGenderType")
    @EqualsAndHashCode.Include
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;
}
