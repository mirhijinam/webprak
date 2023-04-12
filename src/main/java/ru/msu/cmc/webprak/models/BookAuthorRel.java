package ru.msu.cmc.webprak.models;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.*;


@Entity
@Table(name = "book_author_rel")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class BookAuthorRel implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @NonNull
    private Book bookId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @NonNull
    private Author authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthorRel other = (BookAuthorRel) o;
        return Objects.equals(id, other.id)
                && Objects.equals(bookId, other.bookId)
                && Objects.equals(authorId, other.authorId);
    }
}
