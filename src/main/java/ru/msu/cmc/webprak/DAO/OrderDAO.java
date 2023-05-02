package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import ru.msu.cmc.webprak.models.Order;
import ru.msu.cmc.webprak.models.Order.OrderStatus;

import java.util.List;


public interface OrderDAO extends CommonDAO<Order, Long> {

    List<Order> searchOrders(Filter filter);

    @Builder
    @Getter
    class Filter {
        private Long orderId;
        private OrderStatus orderStatus;
        private Integer price;
        private String book;
    }

    static ClientDAO.Filter.FilterBuilder getFilterBuilder() {
        return ClientDAO.Filter.builder();
    }
}
