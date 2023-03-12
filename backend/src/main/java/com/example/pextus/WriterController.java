package com.example.pextus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/writers")
public class WriterController {
    private static final Logger logger = LoggerFactory.getLogger(WriterController.class);
    private final WriterService writerService;
    @Autowired
    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Writer>> getWriters() {
        logger.info("HTTP GET /api/v1/writers");
        return ResponseEntity.ok(this.writerService.getWriters());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Optional<Writer>> getWriter(@PathVariable String id) {
        logger.info("HTTP GET /api/v1/writers/{id}");
        Optional<Writer> writer = this.writerService.findWriterById(id);
        return ResponseEntity.ok(writer);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<Writer> putWriter(@RequestBody Writer writer) {
        logger.info("HTTP PUT /api/v1/writers");
        return ResponseEntity.ok(this.writerService.createOrModifyWriter(writer));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteWriter(@PathVariable String id) {
        logger.info("HTTP DELETE /api/v1/writers/{id}");
        this.writerService.deleteBookById(id);
        return ResponseEntity.ok(id);
    }
}
