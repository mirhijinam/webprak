package ru.msu.cmc.webprak.models;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;

import org.hibernate.annotations.Type;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;



@Entity
@Table(name = "`order`")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Order implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "creation_data", nullable = false, columnDefinition = "date")
    @NonNull
    private Date creationData;

    @Column(name = "delivery_data", nullable = false, columnDefinition = "date")
    @NonNull
    private Date deliveryData;

//    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//    public record OrderInfo(
//            boolean fragile,
//            boolean leave_at_the_door
//    ) implements Serializable {
//        @JsonCreator
//        public OrderInfo(
//                @JsonProperty("fragile") boolean fragile,
//                @JsonProperty("leave_at_the_door") boolean leave_at_the_door) {
//            this.fragile = fragile;
//            this.leave_at_the_door = leave_at_the_door;
//        }
//    }

    public static class OrderInfo {
        private String fragile;
        private boolean atTheDoor;
        public void setFragile(String fragile) {
            this.fragile = fragile;
        }
        public void setAtTheDoor(boolean atTheDoor) {
            this.atTheDoor = atTheDoor;
        }
    }

    public enum OrderStatus {
        NEW,
        IN_PROGRESS,
        FINISHED
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NonNull
    private Order.OrderStatus orderStatus;

    @Column(name = "price", nullable = false)
    @NonNull
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order other = (Order) o;
        return Objects.equals(id, other.id)
                && creationData.equals(other.creationData)
                && deliveryData.equals(other.deliveryData)
                && orderStatus.equals(other.orderStatus)
                && price.equals(other.price);
    }
}
