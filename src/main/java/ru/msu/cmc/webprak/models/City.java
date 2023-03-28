package ru.msu.cmc.webprak.models;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.*;


@Entity
@Table(name = "city")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class City implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "city_name", nullable = false)
    @NonNull
    private String cityName;

    @Column(name = "delivery_days", nullable = false, columnDefinition = "interval")
    @NonNull
    private Integer deliveryDays;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City other = (City) o;
        return Objects.equals(id, other.id)
                && Objects.equals(cityName, other.cityName)
                && Objects.equals(deliveryDays, other.deliveryDays);
    }
}
