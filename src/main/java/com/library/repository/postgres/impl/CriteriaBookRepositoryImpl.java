package com.library.repository.postgres.impl;

import com.library.model.entity.Book;
import com.library.model.entity.Book_;
import com.library.repository.postgres.CriteriaBookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CriteriaBookRepositoryImpl implements CriteriaBookRepository {

    private final EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<Book> findByStackAndUnit(Integer stack, String unit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> from = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Object, Object> book = (Join<Object, Object>) from.fetch(Book_.PUBLISHER);
        if (stack != null) predicates.add(cb.equal(from.get("stack"), unit));
        if (unit != null) predicates.add(cb.like(from.get("unit"), unit));

        cq.select(from).where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaUpdate<Book> cu = cb.createCriteriaUpdate(Book.class);
        Root<Book> from = cu.from(Book.class);
        CriteriaUpdate<Book> softDelete = cu.set("deleted", true).where(cb.equal(from.get("id"), id));

        em.createQuery(softDelete).executeUpdate();
    }
}

