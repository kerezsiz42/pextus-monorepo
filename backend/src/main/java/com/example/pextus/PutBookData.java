package com.example.pextus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class PutBookData {
    private String title;
    private Integer publicationYear;
    private String writerId;
}
