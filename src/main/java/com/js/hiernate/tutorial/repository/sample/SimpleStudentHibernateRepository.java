package com.js.hiernate.tutorial.repository.sample;

import com.js.hiernate.tutorial.entity.sample.SimpleStudentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SimpleStudentHibernateRepository {
    private final EntityManagerFactory entityManagerFactory;

    public SimpleStudentEntity save(SimpleStudentEntity entityToSave) {
        final var entityManager = getEntityManager();
        // all changes to DB have to be in explicit Transaction
        entityManager.getTransaction().begin();
        entityManager.persist(entityToSave);
        // force Hibernate to persist changes to DB
        entityManager.flush();
        // end Transaction
        entityManager.getTransaction().commit();
        // clear Hibernate Context
        entityManager.clear();
        // close Entity manager, so connection to DB could be freed
        entityManager.close();
        return entityToSave;
    }

    public SimpleStudentEntity getBy(int id) {
        return getEntityManager().find(SimpleStudentEntity.class, id);
    }

    public SimpleStudentEntity getBy(String firstName, String lastName) {
        final var query = getEntityManager().createQuery(
                """
                        SELECT s
                        FROM simple_student s
                        WHERE s.firstName = :firstName
                        AND s.lastName = :lastName
                             """, SimpleStudentEntity.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getSingleResult();
    }

    public List<SimpleStudentEntity> getStudentsByLastNameLikeUsingQuery(String lastName) {
        final var entityManager = getEntityManager();
        final TypedQuery<SimpleStudentEntity> query = entityManager.createQuery("""
                                                            SELECT s
                                                            FROM simple_student s
                                                            WHERE s.lastName LIKE :lastName
                                                            """, SimpleStudentEntity.class);
        query.setParameter("lastName", "%" + lastName + "%");
        final var resultList = query.getResultList();
        entityManager.clear();
        entityManager.close();
        return resultList;
    }

    public List<SimpleStudentEntity> getStudentsByLastNameLikeUsingNamedQuery(String lastName) {
        final var entityManager = getEntityManager();
        final TypedQuery<SimpleStudentEntity> query = entityManager.createNamedQuery("find_student_by_last_name", SimpleStudentEntity.class);
        query.setParameter("lastName", "%" + lastName + "%");
        final var resultList = query.getResultList();
        entityManager.clear();
        entityManager.close();
        return resultList;
    }

    public List<SimpleStudentEntity> getStudentsByLastNameLikeUsingCriteriaQuery(String lastName) {
        final var entityManager = getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<SimpleStudentEntity> criteriaQuery = criteriaBuilder.createQuery(SimpleStudentEntity.class);

        final Root<SimpleStudentEntity> root = criteriaQuery.from(SimpleStudentEntity.class);

        final ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

        final Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastName"), lastNameParam);

        criteriaQuery.select(root).where(lastNamePredicate);

        final TypedQuery<SimpleStudentEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(lastNameParam, "%" + lastName + "%");
        final var resultList = typedQuery.getResultList();
        entityManager.clear();
        entityManager.close();
        return resultList;
    }

    public List<SimpleStudentEntity> getStudentsByLastNameLikeUsingNativeQuery(String lastName) {
        final var entityManager = getEntityManager();
        final var nativeQuery = entityManager.createNativeQuery("SELECT * FROM  simple_student WHERE last_name LIKE ?",
                                                                SimpleStudentEntity.class);
        nativeQuery.setParameter(1, "%" + lastName + "%");
        final var resultList = ((List<SimpleStudentEntity>) nativeQuery.getResultList());
        entityManager.clear();
        entityManager.close();
        return resultList;
    }

    public List<SimpleStudentEntity> getAllStudents(Pageable pageable) {
        final var entityManager = getEntityManager();
        // shenanigans to make sorting somewhat generic, but it introduces the risk of SQL Injection, just for learning purposes
        final var tableAcronym = "s";
        final var orderClause = pageable.getSort().get()
                                        .map(order -> tableAcronym + "." + order.getProperty() + " " + order.getDirection())
                                        .collect(Collectors.joining(","));
        final var query = entityManager.createQuery(
                "SELECT %s FROM simple_student %s ORDER BY %s".formatted(tableAcronym, tableAcronym, orderClause),
                SimpleStudentEntity.class);
        query.setFirstResult(Math.toIntExact(pageable.getOffset()));
        query.setMaxResults(pageable.getPageSize());

        final var resultList = ((List<SimpleStudentEntity>) query.getResultList());
        entityManager.clear();
        entityManager.close();
        return resultList;
    }

    public SimpleStudentEntity update(SimpleStudentEntity updatedEntity) {
        final var entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        final var mergedEntity = entityManager.merge(updatedEntity);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();
        return mergedEntity;
    }

    public void delete(SimpleStudentEntity entityToDelete) {
        final var entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        final var student = entityManager.find(SimpleStudentEntity.class, entityToDelete.getId());
        entityManager.remove(student);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
