package com.js.hiernate.tutorial.repository;

import com.js.hiernate.tutorial.PostgresInitializer;
import com.js.hiernate.tutorial.entity.SimpleStudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    void findByLastNameUsingQuery() {
        final var studentEntity1 = SimpleStudentEntity.builder()
                                                      .firstName("John")
                                                      .lastName("AB")
                                                      .build();
        final var studentEntity2 = SimpleStudentEntity.builder()
                                                      .firstName("Not John")
                                                      .lastName("BA")
                                                      .build();
        final var studentEntity3 = SimpleStudentEntity.builder()
                                                      .firstName("Tom")
                                                      .lastName("CC")
                                                      .build();
        hibernateRepository.save(studentEntity1);
        hibernateRepository.save(studentEntity2);
        hibernateRepository.save(studentEntity3);

        final var foundEntities = hibernateRepository.getStudentsByLastNameLikeUsingQuery("A");

        assertThat(foundEntities)
                .isNotEmpty()
                .hasSize(2)
                .doesNotHaveDuplicates()
                .contains(studentEntity1, studentEntity2)
                .doesNotContain(studentEntity3);
    }

    @Test
    void findByLastNameUsingNamedQuery() {
        final var studentEntity1 = SimpleStudentEntity.builder()
                                                      .firstName("John")
                                                      .lastName("AB")
                                                      .build();
        final var studentEntity2 = SimpleStudentEntity.builder()
                                                      .firstName("Not John")
                                                      .lastName("BA")
                                                      .build();
        final var studentEntity3 = SimpleStudentEntity.builder()
                                                      .firstName("Tom")
                                                      .lastName("CC")
                                                      .build();
        hibernateRepository.save(studentEntity1);
        hibernateRepository.save(studentEntity2);
        hibernateRepository.save(studentEntity3);

        final var foundEntities = hibernateRepository.getStudentsByLastNameLikeUsingNamedQuery("A");

        assertThat(foundEntities)
                .isNotEmpty()
                .hasSize(2)
                .doesNotHaveDuplicates()
                .contains(studentEntity1, studentEntity2)
                .doesNotContain(studentEntity3);
    }

    @Test
    void findByLastNameUsingCriteriaQuery() {
        final var studentEntity1 = SimpleStudentEntity.builder()
                                                      .firstName("John")
                                                      .lastName("AB")
                                                      .build();
        final var studentEntity2 = SimpleStudentEntity.builder()
                                                      .firstName("Not John")
                                                      .lastName("BA")
                                                      .build();
        final var studentEntity3 = SimpleStudentEntity.builder()
                                                      .firstName("Tom")
                                                      .lastName("CC")
                                                      .build();
        hibernateRepository.save(studentEntity1);
        hibernateRepository.save(studentEntity2);
        hibernateRepository.save(studentEntity3);

        final var foundEntities = hibernateRepository.getStudentsByLastNameLikeUsingCriteriaQuery("A");

        assertThat(foundEntities)
                .isNotEmpty()
                .hasSize(2)
                .doesNotHaveDuplicates()
                .contains(studentEntity1, studentEntity2)
                .doesNotContain(studentEntity3);
    }

    @Test
    void findByLastNameUsingNativeQuery() {
        final var studentEntity1 = SimpleStudentEntity.builder()
                                                      .firstName("John")
                                                      .lastName("AB")
                                                      .build();
        final var studentEntity2 = SimpleStudentEntity.builder()
                                                      .firstName("Not John")
                                                      .lastName("BA")
                                                      .build();
        final var studentEntity3 = SimpleStudentEntity.builder()
                                                      .firstName("Tom")
                                                      .lastName("CC")
                                                      .build();
        hibernateRepository.save(studentEntity1);
        hibernateRepository.save(studentEntity2);
        hibernateRepository.save(studentEntity3);

        final var foundEntities = hibernateRepository.getStudentsByLastNameLikeUsingNativeQuery("A");

        assertThat(foundEntities)
                .isNotEmpty()
                .hasSize(2)
                .doesNotHaveDuplicates()
                .contains(studentEntity1, studentEntity2)
                .doesNotContain(studentEntity3);
    }

    @Test
    void getAllStudentsWithPageable() {
        final var studentEntity1 = SimpleStudentEntity.builder()
                                                      .firstName("John")
                                                      .lastName("AB")
                                                      .build();
        final var studentEntity2 = SimpleStudentEntity.builder()
                                                      .firstName("Not John")
                                                      .lastName("BA")
                                                      .build();
        final var studentEntity3 = SimpleStudentEntity.builder()
                                                      .firstName("Tom")
                                                      .lastName("CC")
                                                      .build();
        hibernateRepository.save(studentEntity1);
        hibernateRepository.save(studentEntity2);
        hibernateRepository.save(studentEntity3);

        final var foundEntities = hibernateRepository.getAllStudents(PageRequest.of(0, 2,
                                                                                    Sort.by(Sort.Order.desc("lastName"))));

        assertThat(foundEntities)
                .isNotEmpty()
                .hasSize(2)
                .doesNotHaveDuplicates()
                .contains(studentEntity3, studentEntity2)
                .doesNotContain(studentEntity1);
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