package com.example.pextus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class Config {
    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository, WriterRepository writerRepository) {
        return args -> {
            Writer w1 = new Writer("John Anthony Burgess Wilson", LocalDate.of(1917, Month.FEBRUARY, 25));
            Writer w2 = new Writer("John Ronald Reuel Tolkien", LocalDate.of(1892, Month.JANUARY, 3));
            Writer w3 = new Writer("Ursula Kroeber Le Guin", LocalDate.of(1929, Month.OCTOBER, 21));
            w1 = writerRepository.save(w1);
            w2 = writerRepository.save(w2);
            w3 = writerRepository.save(w3);
            Book b5 = new Book("A Clockwork Orange", 1962, w1);
            Book b1 = new Book("The Fellowship of the Ring", 1954, w2);
            Book b2 = new Book("The Two Towers", 1954, w2);
            Book b3 = new Book("The Return of the King", 1955, w2);
            Book b4 = new Book("A Wizard of Earthsea", 1968, w3);

            bookRepository.saveAll(List.of(b1, b2, b3, b4, b5));
        };
    }
}
