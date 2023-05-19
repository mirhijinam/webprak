package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "client")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Client implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Long id;

    @Column(name = "client_name", nullable = false)
    @NonNull
    private String clientName;

    @Column(name = "mail")
    private String email;

    @Column(name = "phone", nullable = false)
    @NonNull
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client other = (Client) o;
        return Objects.equals(id, other.id)
                && Objects.equals(clientName, other.clientName)
                && Objects.equals(email, other.email)
                && Objects.equals(phone, other.phone);
    }

    public String getName() {
        return clientName;
    }
    public Long getId() {
        return id;
    }
}
