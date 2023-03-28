package ru.msu.cmc.webprak.models;


import jakarta.persistence.*;
import java.util.Objects;
import lombok.*;



@Entity
@Table(name = "client_address_rel")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ClientAddressRel implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    @NonNull
    private Book addressId;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    @NonNull
    private Author cityId;

    @Column(name = "street_name", nullable = false)
    @NonNull
    private String bookName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientAddressRel other = (ClientAddressRel) o;
        return Objects.equals(id, other.id)
                && Objects.equals(addressId, other.addressId)
                && Objects.equals(cityId, other.cityId)
                && bookName.equals(other.bookName);
    }
}
