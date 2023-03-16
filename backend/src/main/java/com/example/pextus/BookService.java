package com.example.pextus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final WriterService writerService;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

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

    public Optional<Book> createOrModifyBook(BookData bookData) {
        Optional<Writer> writer = this.writerService.findWriterById(bookData.getWriterId());
        if (writer.isEmpty()) {
            return Optional.empty();
        }
        Book book;
        if (bookData.getId() == null) {
            book = new Book(bookData.getTitle(), bookData.getPublicationYear(), writer.get());
        } else {
            try {
                UUID id = UUID.fromString(bookData.getId().trim());
                book = new Book(id, bookData.getTitle(), bookData.getPublicationYear(), writer.get());
                logger.info("Done");
            } catch(IllegalArgumentException err) {
                return Optional.empty();
            }
        }
        return Optional.of(this.bookRepository.save(book));
    }

    public List<Book> searchBooksByTitle(@Valid @NotNull String title) {
        return this.bookRepository.searchBooksByTitle(title);
    }

    public Optional<String> deleteBookById(@Valid @NotNull String id) {
        try {
            UUID uuid = UUID.fromString(id);
            this.bookRepository.deleteById(uuid);
            return Optional.of(id);
        } catch(EmptyResultDataAccessException err) {
            return Optional.empty();
        }
        catch(IllegalArgumentException err) {
            return Optional.empty();
        }
    }
}
