package ru.msu.cmc.webprak.models;


import lombok.*;

import com.fasterxml.jackson.databind.JsonNode;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

import java.util.Objects;



@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Book implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Long id;

    @Column(name = "book_name", nullable = false)
    @NonNull
    private String bookName;

    @Column(name = "price", nullable = false)
    @NonNull
    private Integer price;

    @Column(name = "is_available", nullable = false)
    @NonNull
    private String isAvailable;

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
                && price.equals(other.price)
                && isAvailable.equals(other.isAvailable)
                && numOfCopies.equals(other.numOfCopies);
    }

    public String getName() {
        return bookName;
    }
}
