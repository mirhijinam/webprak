package ru.msu.cmc.webprak.DAO.impl;


import ru.msu.cmc.webprak.DAO.BookDAO;
import ru.msu.cmc.webprak.models.*;
import ru.msu.cmc.webprak.models.Order;

import jakarta.persistence.criteria.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BookDAOImpl extends CommonDAOImpl<Book, Long> implements BookDAO {
    public BookDAOImpl() {
        super(Book.class);
    }

    public List<Book> searchBooks(Filter filter) throws SQLException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = builder.createQuery(Book.class);
            Root<Book> bookRoot = criteriaQuery.from(Book.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null)
                predicates.add(builder.equal(bookRoot.get("id"), filter.getId()));

            if (filter.getName() != null)
                predicates.add(builder.like(bookRoot.get("name"), "%" + filter.getName() + "%"));

            if (filter.getPrice() != null)
                predicates.add(builder.like(bookRoot.get("price"), "%" + filter.getPrice() + "%"));

            if (filter.getIsAvailable() != null && !filter.getIsAvailable().isEmpty()) {
                predicates.add(builder.like(bookRoot.get("isAvailable"), "%" + filter.getIsAvailable() + "%"));
            }

            if (filter.getAuthor() != null && !filter.getAuthor().isEmpty()) {
                Join<Book, BookAuthorRel> bookBookAuthorRelJoin = bookRoot.join("book_id");
                Join<BookAuthorRel, Author> authorJoin = bookBookAuthorRelJoin.join("author_id");
                criteriaQuery.select(bookRoot).distinct(true);
                predicates.add(bookRoot.get("book_id").in(criteriaQuery));
            }

            if (filter.getGenre() != null && !filter.getGenre().isEmpty()) {
                Join<Book, BookGenreRel> bookBookGenreRelJoin = bookRoot.join("book_id");
                Join<BookAuthorRel, Genre> authorJoin = bookBookGenreRelJoin.join("genre_id");
                criteriaQuery.select(bookRoot).distinct(true);
                predicates.add(bookRoot.get("book_id").in(criteriaQuery));
            }

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public List<Book> getAllBooksByOrderId(Long orderId) throws SQLException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = builder.createQuery(Book.class);
            Root<Book> bookRoot = criteriaQuery.from(Book.class);

            Join<Book, OrderingBookRel> bookOrderingBookRelJoin = bookRoot.join("book_id");
            Join<OrderingBookRel, Order> orderJoin = bookOrderingBookRelJoin.join("order_id");
            criteriaQuery.select(bookRoot).distinct(true);

            List<Book> books = session.createQuery(criteriaQuery).getResultList();
            return books;
        }
    }
}
