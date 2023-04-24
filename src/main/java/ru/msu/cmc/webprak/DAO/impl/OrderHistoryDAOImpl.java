package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.stereotype.Repository;

import ru.msu.cmc.webprak.DAO.OrderHistoryDAO;
import ru.msu.cmc.webprak.models.OrderHistory;


@Repository
public class OrderHistoryDAOImpl extends CommonDAOImpl<OrderHistory, Long> implements OrderHistoryDAO {

    public OrderHistoryDAOImpl() {
        super(OrderHistory.class);
    }
}
