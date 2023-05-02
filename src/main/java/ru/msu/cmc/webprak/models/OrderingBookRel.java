package ru.msu.cmc.webprak.models;


import lombok.*;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "ordering_book_rel")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderingBookRel implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Order order;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private Book book;

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
                && order.equals(other.order)
                && book.equals(other.book)
                && amount.equals(other.amount)
                && total_cost.equals(other.total_cost);
    }
}
