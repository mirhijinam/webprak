package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "author")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Author implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "author_name", nullable = false)
    @NonNull
    private String authorName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author other = (Author) o;
        return Objects.equals(id, other.id)
                && authorName.equals(other.authorName);
    }
}
