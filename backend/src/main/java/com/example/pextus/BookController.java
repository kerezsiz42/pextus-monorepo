package com.example.pextus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(this.bookService.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> findBookById(@PathVariable String id) {
        return ResponseEntity.ok(this.bookService.findBookById(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(this.bookService.createBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable String id) {
        this.bookService.deleteBookById(id);
        return ResponseEntity.ok(id);
    }
}
