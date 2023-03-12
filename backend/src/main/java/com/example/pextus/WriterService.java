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
public class WriterService {
    private final WriterRepository writerRepository;

    @Autowired
    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public List<Writer> getWriters() {
        return this.writerRepository.findAll();
    }

    public Optional<Writer> findWriterById(@Valid @NotNull String id) {
        UUID uuid = UUID.fromString(id);
        return this.writerRepository.findById(uuid);
    }

    public Writer createWriter(@Valid Writer writer) {
        return this.writerRepository.save(writer);
    }

    public void deleteBookById(@Valid @NotNull String id) {
        UUID uuid = UUID.fromString(id);
        try {
            this.writerRepository.deleteById(uuid);
        } catch(EmptyResultDataAccessException err) {}
    }
}
