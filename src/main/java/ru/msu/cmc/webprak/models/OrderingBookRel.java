package ru.msu.cmc.webprak.models;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.*;



@Entity
@Table(name = "ordering_book_rel")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class OrderingBookRel implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @NonNull
    private Book orderId;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @NonNull
    private Book bookId;

    @Column(name = "amount", nullable = false)
    @NonNull
    private Integer amount;

    @Column(name = "total_cost", nullable = false)
    @NonNull
    private Integer total_cost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderingBookRel other = (OrderingBookRel) o;
        return Objects.equals(id, other.id)
                && Objects.equals(orderId, other.orderId)
                && Objects.equals(bookId, other.bookId)
                && amount.equals(other.amount)
                && total_cost.equals(other.total_cost);
    }
}