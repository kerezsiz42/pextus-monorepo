package com.example.pextus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/writers")
public class WriterController {
    private final WriterService writerService;
    @Autowired
    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @GetMapping
    public ResponseEntity<List<Writer>> getWriters() {
        return ResponseEntity.ok(this.writerService.getWriters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Writer>> findWriterById(@PathVariable String id) {
        Optional<Writer> writer = this.writerService.findWriterById(id);
        return ResponseEntity.ok(writer);
    }

    @PostMapping
    public ResponseEntity<Writer> createWriter(@RequestBody Writer writer) {
        return ResponseEntity.ok(this.writerService.createWriter(writer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWriterById(@PathVariable String id) {
        this.writerService.deleteBookById(id);
        return ResponseEntity.ok(id);
    }
}
