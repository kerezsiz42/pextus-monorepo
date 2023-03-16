package com.example.pextus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class BookData {
    private String id;
    private String title;
    private Integer publicationYear;
    private String writerId;

    @Override
    public String toString() {
        return "BookData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", publicationYear=" + publicationYear +
                ", writerId='" + writerId + '\'' +
                '}';
    }
}
