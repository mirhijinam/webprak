package ru.msu.cmc.webprak.DAO;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.sql.SQLException;

import ru.msu.cmc.webprak.models.Order;

public interface OrderDAO extends CommonDAO<Order, Long> {
    Collection searchOrders(Filter filter) throws SQLException;
    Collection getAllOrders() throws SQLException;
    Collection getAllOrdersByClientId(Long clientId) throws SQLException;

    @Builder
    @Getter
    class Filter {
        private Long client_id;
        private enum Status {
            NEW,
            IN_PROGRESS,
            FINISHED
        }
        private Status status;
    }

    static ClientDAO.Filter.FilterBuilder getFilterBuilder() {
        return ClientDAO.Filter.builder();
    }
}
