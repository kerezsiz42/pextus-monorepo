package com.example.pextus;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name= "writers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
public class Writer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "writer_id")
    private UUID id;

    @Size(min = 2, max = 255, message = "Full name must be between 2 and 255 characters")
    @NotNull(message = "You must provide a valid name")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull(message = "You must provide a valid birth date")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    public @Valid Writer(String fullName, LocalDate birthDate) {
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    public @Valid Writer(UUID id, String fullName, LocalDate birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
    }
}
