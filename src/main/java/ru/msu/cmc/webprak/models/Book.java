package ru.msu.cmc.webprak.models;


import lombok.*;

import com.fasterxml.jackson.databind.JsonNode;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

import java.util.Objects;



@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Book implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "book_name", nullable = false)
    @NonNull
    private String bookName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "publication_info", columnDefinition = "jsonb")
    private JsonNode publicationInfo;

    @Column(name = "price", nullable = false, columnDefinition = "price")
    @NonNull
    private Double price;

    @Column(name = "is_available", nullable = false, columnDefinition = "boolean")
    @NonNull
    private Boolean isAvailable;

    @Column(name = "num_of_copies", nullable = false)
    @NonNull
    private Integer numOfCopies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book other = (Book) o;
        return Objects.equals(id, other.id)
            && bookName.equals(other.bookName)
                && publicationInfo.equals(other.publicationInfo)
                && price.equals(other.price)
                && isAvailable.equals(other.isAvailable)
                && numOfCopies.equals(other.numOfCopies);
    }
}
