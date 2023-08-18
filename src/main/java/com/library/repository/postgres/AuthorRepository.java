package com.library.repository.postgres;

import com.library.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Modifying
    @Query(value = "UPDATE Author a SET a.deleted = true WHERE a.id = :id")
    void delete(@Param(value = "id") Long authorId);
}
