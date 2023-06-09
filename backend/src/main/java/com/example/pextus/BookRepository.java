package com.example.pextus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE CONCAT(LOWER(:title ), '%')")
    List<Book> searchBooksByTitle(@Param("title") String title);
}
