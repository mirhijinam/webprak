package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "order_history_rel")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class OrderHistory implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    @NonNull
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @NonNull
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderHistory other = (OrderHistory) o;
        return Objects.equals(id, other.id)
                && client.equals(other.client)
                && order.equals(other.order);
    }
}
