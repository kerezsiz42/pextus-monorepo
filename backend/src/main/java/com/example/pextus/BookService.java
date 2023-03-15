package com.example.pextus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final WriterService writerService;

    @Autowired
    public BookService(BookRepository bookRepository, WriterService writerService) {
        this.bookRepository = bookRepository;

        this.writerService = writerService;
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Optional<Book> findBookById(@Valid @NotNull String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return this.bookRepository.findById(uuid);
        } catch(IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<Book> createOrModifyBook(PutBookData putBookData) {
        Optional<Writer> writer = this.writerService.findWriterById(putBookData.getWriterId());
        if (writer.isEmpty()) {
            return Optional.empty();
        }
        Book book = new Book(putBookData.getTitle(), putBookData.getPublicationYear(), writer.get());
        return Optional.of(this.bookRepository.save(book));
    }

    public List<Book> searchBooksByTitle(@Valid @NotNull String title) {
        return this.bookRepository.searchBooksByTitle(title);
    }

    public void deleteBookById(@Valid @NotNull String id) {
        try {
            UUID uuid = UUID.fromString(id);
            this.bookRepository.deleteById(uuid);
        } catch(EmptyResultDataAccessException err) {}
    }
}
