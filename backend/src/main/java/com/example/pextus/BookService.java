package com.example.pextus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    public Optional<Book> findBookById(@Valid @NotNull String id) {
        UUID uuid = UUID.fromString(id);
        return this.bookRepository.findById(uuid);
    }

    public Book createBook(@Valid Book book) {
        return this.bookRepository.save(book);
    }

    public List<Book> searchBooksByTitle(@Valid @NotNull String title) {
        return this.bookRepository.searchBooksByTitle(title);
    }

    public void deleteBookById(@Valid @NotNull String id) {
        UUID uuid = UUID.fromString(id);
        try {
            this.bookRepository.deleteById(uuid);
        } catch(EmptyResultDataAccessException err) {}
    }
}
