package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import ru.msu.cmc.webprak.models.Client;
import ru.msu.cmc.webprak.models.Order;
import ru.msu.cmc.webprak.models.Order.OrderStatus;
import ru.msu.cmc.webprak.models.OrderingBookRel;

import java.util.Date;
import java.util.List;


public interface OrderDAO extends CommonDAO<Order, Long> {

    List<OrderingBookRel> getOrderingBookRelList(Long clientId);

    List<Order> searchOrders(Filter filter);

    @Builder
    @Getter
    class Filter {
        private Long orderId;
        private OrderStatus orderStatus;
        private Integer price;
        private String book;
        private Date creationData;
        private Date deliveryData;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
