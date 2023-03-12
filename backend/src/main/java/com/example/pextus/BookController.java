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

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(name = "q", defaultValue = "") String title) {
        if (title.equals("")) {
            return ResponseEntity.ok(this.bookService.getAllBooks());
        }
        return ResponseEntity.ok(this.bookService.searchBooksByTitle(title));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable String id) {
        return ResponseEntity.ok(this.bookService.findBookById(id));
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<Book> putBook(@RequestBody Book book) {
        return ResponseEntity.ok(this.bookService.createOrModifyBook(book));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        this.bookService.deleteBookById(id);
        return ResponseEntity.ok(id);
    }
}
