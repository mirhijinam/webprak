package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "book_genre_rel")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class BookGenreRel implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Book book;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookGenreRel other = (BookGenreRel) o;
        return Objects.equals(id, other.id)
                && book.equals(other.book)
                && genre.equals(other.genre);
    }
}
