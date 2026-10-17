package com.example.Miss.Minutes.Repository;

import com.example.Miss.Minutes.Dto.CreateTodoDto;
import com.example.Miss.Minutes.Entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("""
    SELECT t FROM Todo t
    WHERE t.createdAt >= :start
      AND t.createdAt < :end
    """)
    List<Todo> findAllByDay(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
