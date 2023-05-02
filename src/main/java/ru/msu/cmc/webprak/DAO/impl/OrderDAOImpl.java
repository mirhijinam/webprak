package ru.msu.cmc.webprak.DAO.impl;



import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import ru.msu.cmc.webprak.DAO.OrderDAO;
import ru.msu.cmc.webprak.models.Order;
import ru.msu.cmc.webprak.models.*;

import jakarta.persistence.criteria.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;


@Repository
public class OrderDAOImpl extends CommonDAOImpl<Order, Long> implements OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public OrderDAOImpl() {
        super(Order.class);
    }

    public List<Order> searchOrders(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
            Root<Order> orderRoot = criteriaQuery.from(Order.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getOrderId() != null)
                predicates.add(builder.equal(orderRoot.get("id"), filter.getOrderId()));

            if (filter.getOrderStatus() != null)
                predicates.add(builder.equal(orderRoot.get("orderStatus"), filter.getOrderStatus()));

            if (filter.getPrice() != null)
                predicates.add(builder.equal(orderRoot.get("price"), filter.getPrice()));

            if (filter.getBook() != null && !filter.getBook().isEmpty()) {
                Subquery<Long> orderingBookRelSubquery = criteriaQuery.subquery(Long.class);
                Root<OrderingBookRel> orderingBookRelRoot = orderingBookRelSubquery.from(OrderingBookRel.class);
                orderingBookRelSubquery.select(orderingBookRelRoot.get("order").get("id"))
                        .where(orderingBookRelRoot.get("book").get("bookName").in(filter.getBook()));
                predicates.add(orderRoot.get("id").in(orderingBookRelSubquery));
            }
            if (!predicates.isEmpty())
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}

