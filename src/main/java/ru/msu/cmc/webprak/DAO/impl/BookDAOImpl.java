package ru.msu.cmc.webprak.DAO.impl;


import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import ru.msu.cmc.webprak.DAO.BookDAO;
import ru.msu.cmc.webprak.DAO.BookGenreRelDAO;
import ru.msu.cmc.webprak.DAO.BookAuthorRelDAO;
import ru.msu.cmc.webprak.models.*;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class BookDAOImpl extends CommonDAOImpl<Book, Long> implements BookDAO {

    public BookDAOImpl() {
        super(Book.class);
    }

    @Autowired
    private BookGenreRelDAO bookGenreRelDAO = new BookGenreRelDAOImpl();
    @Autowired
    private BookAuthorRelDAO bookAuthorRelDAO = new BookAuthorRelDAOImpl();

    @Override
    public List<Book> searchBooks(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = builder.createQuery(Book.class);
            Root<Book> bookRoot = criteriaQuery.from(Book.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null)
                predicates.add(builder.equal(bookRoot.get("id"), filter.getId()));

            if (filter.getName() != null)
                predicates.add(builder.like(bookRoot.get("bookName"), "%" + filter.getName() + "%"));

            if (filter.getPrice() != null)
                predicates.add(builder.equal(bookRoot.get("price"), filter.getPrice()));

            if (filter.getIsAvailable() != null && !filter.getIsAvailable().isEmpty()) {
                predicates.add(builder.like(bookRoot.get("isAvailable"), "%" + filter.getIsAvailable() + "%"));
            }

            if (filter.getAuthor() != null && !filter.getAuthor().isEmpty()) {
                Subquery<Long> bookAuthorRelSubquery = criteriaQuery.subquery(Long.class);
                Root<BookAuthorRel> bookAuthorRelRoot = bookAuthorRelSubquery.from(BookAuthorRel.class);
                bookAuthorRelSubquery.select(bookAuthorRelRoot.get("book").get("id"))
                        .where(bookAuthorRelRoot.get("author").get("authorName").in(filter.getAuthor()));
                predicates.add(bookRoot.get("id").in(bookAuthorRelSubquery));
            }

            if (filter.getGenre() != null && !filter.getGenre().isEmpty()) {
                Subquery<Long> bookGenreRelSubquery = criteriaQuery.subquery(Long.class);
                Root<BookGenreRel> bookGenreRelRoot = bookGenreRelSubquery.from(BookGenreRel.class);
                bookGenreRelSubquery.select(bookGenreRelRoot.get("book").get("id"))
                        .where(bookGenreRelRoot.get("genre").get("genreName").in(filter.getGenre()));
                predicates.add(bookRoot.get("id").in(bookGenreRelSubquery));
            }

            if (!predicates.isEmpty())
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Genre> getBookGenres(Long bookId) {
        List<Genre> ret = new ArrayList<>();

        for(BookGenreRel bookGenreRel : bookGenreRelDAO.getAll()) {
            if (Objects.equals(bookGenreRel.getBook().getId(), bookId)) {
                ret.add(bookGenreRel.getGenre());
            }
        }
        return ret;
    }

    @Override
    public List<Author> getBookAuthors(Long bookId) {
        List<Author> ret = new ArrayList<>();

        for(BookAuthorRel bookAuthorRel : bookAuthorRelDAO.getAll()) {
            if (Objects.equals(bookAuthorRel.getBook().getId(), bookId)) {
                ret.add(bookAuthorRel.getAuthor());
            }
        }
        return ret;
    }
}

