package com.example.pextus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class WriterData {
    private String id;

    private String fullName;

    private String birthDate;

    @Override
    public String toString() {
        return "WriterData{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
