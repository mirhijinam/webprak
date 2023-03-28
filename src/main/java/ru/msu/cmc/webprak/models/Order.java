package ru.msu.cmc.webprak.models;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.*;


@Entity
@Table(name = "order")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Order implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "creation_data", nullable = false, columnDefinition = "date")
    @NonNull
    private Long creationData;

    @Column(name = "delivery_data", nullable = false, columnDefinition = "date")
    @NonNull
    private Long deliveryData;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "delivery_terms", columnDefinition = "jsonb")
    private JsonNode publicationInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NonNull
    private OrderStatus status;

    @Column(name = "price", nullable = false, columnDefinition = "price")
    @NonNull
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order other = (Order) o;
        return Objects.equals(id, other.id)
                && creationData.equals(other.creationData)
                && deliveryData.equals(other.deliveryData)
                && publicationInfo.equals(other.publicationInfo)
                && status.equals(other.status)
                && price.equals(other.price);
    }
}