package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Client implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "client_name", nullable = false)
    @NonNull
    private String clientName;

    @Column(name = "address_id", nullable = false)
    @NonNull
    private Integer addressId;

    @Column(name = "mail", nullable = true)
    private String mail;

    @Column(name = "phone", nullable = false, columnDefinition = "varchar")
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client other = (Client) o;
        return Objects.equals(id, other.id)
                && Objects.equals(clientName, other.clientName)
                && Objects.equals(addressId, other.addressId)
                && Objects.equals(mail, other.mail)
                && Objects.equals(phone, other.phone);
    }
}
