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

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Writer>> getWriters() {
        return ResponseEntity.ok(this.writerService.getWriters());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Optional<Writer>> getWriter(@PathVariable String id) {
        Optional<Writer> writer = this.writerService.findWriterById(id);
        return ResponseEntity.ok(writer);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<Writer> putWriter(@RequestBody Writer writer) {
        return ResponseEntity.ok(this.writerService.createOrModifyWriter(writer));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteWriter(@PathVariable String id) {
        this.writerService.deleteBookById(id);
        return ResponseEntity.ok(id);
    }
}
