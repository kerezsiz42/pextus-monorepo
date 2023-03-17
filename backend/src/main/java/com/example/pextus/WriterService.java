package com.example.pextus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WriterService {
    private final WriterRepository writerRepository;
    private static final Logger logger = LoggerFactory.getLogger(WriterService.class);

    @Autowired
    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public List<Writer> getAllWriters() {
        logger.info("getAllWriters called");
        return this.writerRepository.findAll();
    }

    public Optional<Writer> findWriterById(@Valid @NotNull String id) {
        logger.info("findWriterById called with id: " + id);
        try {
            UUID uuid = UUID.fromString(id);
            return this.writerRepository.findById(uuid);
        } catch(IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Optional<Writer> createOrModifyWriter(@Valid WriterData writerData) {
        logger.info("createOrModifyWriter called width writerData: " + writerData.toString());
        LocalDate birthDate = LocalDate.parse(writerData.getBirthDate());
        Writer writer;
        if (writerData.getId() == null) {
            writer = new Writer(writerData.getFullName(), birthDate);
        } else {
            try {
                UUID id = UUID.fromString(writerData.getId().trim());
                writer = new Writer(id, writerData.getFullName(), birthDate);
            } catch(IllegalArgumentException err) {
                return Optional.empty();
            }
        }
        return Optional.of(this.writerRepository.save(writer));
    }

    public Optional<String> deleteWriterById(@Valid @NotNull String id) {
        logger.info("deleteBookById called with id: " + id);
        try {
            UUID uuid = UUID.fromString(id);
            this.writerRepository.deleteById(uuid);
            return Optional.of(id);
        } catch(EmptyResultDataAccessException e) {
            return Optional.of(id);
        } catch(IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
