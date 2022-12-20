package com.js.hiernate.tutorial.repository.sample;

import com.js.hiernate.tutorial.PostgresInitializer;
import com.js.hiernate.tutorial.entity.sample.SimpleStudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleStudentJpaRepositoryTest extends PostgresInitializer {

    @Autowired
    private SimpleStudentJpaRepository studentRepository;

    @BeforeEach
    void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("Save of sample entity works")
    void saveWorks() {
        var simpleStudent = prepareSampleEntity();

        var savedStudent = studentRepository.save(simpleStudent);

        assertThat(savedStudent.getId()).isNotZero();
        assertThat(savedStudent.getFirstName()).isEqualTo(simpleStudent.getFirstName());
        assertThat(savedStudent.getLastName()).isEqualTo(simpleStudent.getLastName());
        assertThat(savedStudent.getEmail()).isEqualTo(simpleStudent.getEmail());
    }

    @Test
    @DisplayName("Retrieving of saved sample entity works")
    void readWorks() {
        var simpleStudent = prepareSampleEntity();
        var savedStudent = studentRepository.save(simpleStudent);

        var queriedStudent = studentRepository.findById(savedStudent.getId());

        assertThat(queriedStudent).isPresent();
        assertThat(savedStudent.getId()).isEqualTo(queriedStudent.get().getId());
        assertThat(savedStudent.getFirstName()).isEqualTo(queriedStudent.get().getFirstName());
        assertThat(savedStudent.getLastName()).isEqualTo(queriedStudent.get().getLastName());
        assertThat(savedStudent.getEmail()).isEqualTo(queriedStudent.get().getEmail());
    }

    @Test
    @DisplayName("Update of sample entity works")
    void updateWorks() {
        var simpleStudent = prepareSampleEntity();
        var savedStudent = studentRepository.save(simpleStudent);

        savedStudent.setFirstName("X");
        savedStudent.setLastName("Y");
        savedStudent.setEmail("x@y.com");

        var updatedStudent = studentRepository.save(savedStudent);

        assertThat(savedStudent.getId()).isEqualTo(updatedStudent.getId());
        assertThat(savedStudent.getFirstName()).isEqualTo(updatedStudent.getFirstName());
        assertThat(savedStudent.getLastName()).isEqualTo(updatedStudent.getLastName());
        assertThat(savedStudent.getEmail()).isEqualTo(updatedStudent.getEmail());
    }

    @Test
    @DisplayName("Delete of sample entity works")
    void deleteWorks() {
        var simpleStudent = prepareSampleEntity();
        var savedStudent = studentRepository.save(simpleStudent);

        studentRepository.delete(savedStudent);

        var allStudents = studentRepository.findAll();
        assertThat(allStudents).isEmpty();
    }

    private SimpleStudentEntity prepareSampleEntity() {
        return SimpleStudentEntity.builder()
                                  .firstName("A")
                                  .lastName("B")
                                  .email("a@b.com")
                                  .build();
    }
}