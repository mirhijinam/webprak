package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "client_city_rel")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ClientCityRel implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @NonNull
    private Client client;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    @NonNull
    private City city;

    @Column(name = "street_name", nullable = false)
    @NonNull
    private String streetName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientCityRel other = (ClientCityRel) o;
        return Objects.equals(id, other.id)
                && client.equals(other.client)
                && city.equals(other.city)
                && streetName.equals(other.streetName);
    }
}
