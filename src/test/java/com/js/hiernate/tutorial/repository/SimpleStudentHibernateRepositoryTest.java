package com.js.hiernate.tutorial.repository;

import com.js.hiernate.tutorial.PostgresInitializer;
import com.js.hiernate.tutorial.entity.SimpleStudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleStudentHibernateRepositoryTest extends PostgresInitializer {

    @Autowired
    private SimpleStudentHibernateRepository hibernateRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void cleanUp() {
        final var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        final var query = entityManager.createQuery("DELETE FROM simple_student");
        query.executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();
    }

    @Test
    void save() {
        final var studentEntity = prepareSampleEntity();

        final var savedEntity = hibernateRepository.save(studentEntity);

        assertThat(savedEntity.getId()).isNotZero();
    }

    @Test
    void getById() {
        final var studentEntity = prepareSampleEntity();
        final var savedEntity = hibernateRepository.save(studentEntity);

        final var foundEntity = hibernateRepository.getBy(savedEntity.getId());

        assertThat(foundEntity.getId()).isEqualTo(studentEntity.getId());
        assertThat(foundEntity.getFirstName()).isEqualTo(studentEntity.getFirstName());
        assertThat(foundEntity.getLastName()).isEqualTo(studentEntity.getLastName());
        assertThat(foundEntity.getEmail()).isEqualTo(studentEntity.getEmail());
    }

    @Test
    void findByFirstAndLastName() {
        final var studentEntity = prepareSampleEntity();
        hibernateRepository.save(studentEntity);

        final var foundEntity = hibernateRepository.getBy(studentEntity.getFirstName(), studentEntity.getLastName());

        assertThat(foundEntity.getId()).isNotZero();
        assertThat(foundEntity.getFirstName()).isEqualTo(studentEntity.getFirstName());
        assertThat(foundEntity.getLastName()).isEqualTo(studentEntity.getLastName());
        assertThat(foundEntity.getEmail()).isEqualTo(studentEntity.getEmail());
    }

    @Test
    void update() {
        final var studentEntity = prepareSampleEntity();
        final var savedEntity = hibernateRepository.save(studentEntity);

        savedEntity.setFirstName("X");
        savedEntity.setLastName("Y");
        savedEntity.setEmail("x@y.com");

        final var updatedEntity = hibernateRepository.update(savedEntity);

        assertThat(updatedEntity.getId()).isEqualTo(savedEntity.getId());
        assertThat(updatedEntity.getFirstName()).isEqualTo(savedEntity.getFirstName());
        assertThat(updatedEntity.getLastName()).isEqualTo(savedEntity.getLastName());
        assertThat(updatedEntity.getEmail()).isEqualTo(savedEntity.getEmail());
    }

    @Test
    void delete() {
        final var studentEntity = prepareSampleEntity();
        final var savedEntity = hibernateRepository.save(studentEntity);

        hibernateRepository.delete(savedEntity);

        final var shouldBeNotFound = hibernateRepository.getBy(savedEntity.getId());
        assertThat(shouldBeNotFound).isNull();
    }

    private SimpleStudentEntity prepareSampleEntity() {
        return SimpleStudentEntity.builder()
                                  .firstName("A")
                                  .lastName("B")
                                  .email("a@b.com")
                                  .build();
    }
}