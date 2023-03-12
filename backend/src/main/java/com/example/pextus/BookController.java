package com.example.pextus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(name = "q", defaultValue = "") String title) {
        logger.info("HTTP GET /api/v1/books");
        if (title.equals("")) {
            return ResponseEntity.ok(this.bookService.getAllBooks());
        }
        return ResponseEntity.ok(this.bookService.searchBooksByTitle(title));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable String id) {
        logger.info("HTTP GET /api/v1/books/{id}");
        return ResponseEntity.ok(this.bookService.findBookById(id));
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<Book> putBook(@RequestBody Book book) {
        logger.info("HTTP PUT /api/v1/books");
        return ResponseEntity.ok(this.bookService.createOrModifyBook(book));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        logger.info("HTTP DELETE /api/v1/books/{id}");
        this.bookService.deleteBookById(id);
        return ResponseEntity.ok(id);
    }
}
