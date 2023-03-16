package com.example.pextus;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "books")
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "book_id")
    private UUID id;
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "You must provide a valid publication year")
    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false, foreignKey = @ForeignKey(name = "books_writer_id_fk"))
    @JsonIdentityReference(alwaysAsId = true)
    private Writer writer;

    public @Valid Book(String title, Integer publicationYear, Writer writer) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.writer = writer;
    }

    public @Valid Book(UUID id, String title, Integer publicationYear, Writer writer) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.writer = writer;
    }
}
