package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "city")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class City implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Long id;

    @Column(name = "city_name", nullable = false)
    @NonNull
    private String cityName;

    @Column(name = "delivery_days", nullable = false)
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
