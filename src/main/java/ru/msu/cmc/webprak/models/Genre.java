package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "genre")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Genre implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "genre_name", nullable = false)
    @NonNull
    private String genreName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre other = (Genre) o;
        return Objects.equals(id, other.id)
                && genreName.equals(other.genreName);
    }
}
