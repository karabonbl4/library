package com.library.repository;

import com.library.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Modifying
    @Query("UPDATE Publisher p SET p.deleted = true WHERE p.id = :id")
    void delete(@Param(value = "id") Long publisherId);
}
