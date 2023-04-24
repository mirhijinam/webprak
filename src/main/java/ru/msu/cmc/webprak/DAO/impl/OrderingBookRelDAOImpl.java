package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.stereotype.Repository;

import ru.msu.cmc.webprak.DAO.OrderingBookRelDAO;
import ru.msu.cmc.webprak.models.OrderingBookRel;


@Repository
public class OrderingBookRelDAOImpl extends CommonDAOImpl<OrderingBookRel, Long> implements OrderingBookRelDAO {

    public OrderingBookRelDAOImpl() {
        super(OrderingBookRel.class);
    }
}
