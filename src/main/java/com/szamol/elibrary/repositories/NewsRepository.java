package com.szamol.elibrary.repositories;


import com.szamol.elibrary.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface NewsRepository extends JpaRepository<News, Integer> {

    News findById(int id);

    @Modifying
    @Query("UPDATE News n SET n.isActive = :newStatus WHERE n.id = :id")
    void updateNewsStatus(@Param("newStatus") int newStatus, @Param("id") int id);
}
