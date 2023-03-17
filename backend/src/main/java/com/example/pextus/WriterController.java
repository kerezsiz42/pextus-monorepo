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
        return ResponseEntity.ok(this.writerService.getAllWriters());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Writer> getWriter(@PathVariable String id) {
        logger.info("HTTP GET /api/v1/writers/{id}");
        Optional<Writer> writer = this.writerService.findWriterById(id);
        if (writer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(writer.get());
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<ApiResponse> putWriter(@RequestBody WriterData writerData) {
        logger.info("HTTP PUT /api/v1/writers");
        Optional<Writer> writer = this.writerService.createOrModifyWriter(writerData);
        if (writer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ApiResponse(writer.get().getId().toString()));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ApiResponse> deleteWriter(@PathVariable String id) {
        logger.info("HTTP DELETE /api/v1/writers/{id}");
        Optional<String> deletedId = this.writerService.deleteBookById(id);
        if (deletedId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ApiResponse(deletedId.get()));
    }
}
