package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.BookAuthorRelDAO;
import ru.msu.cmc.webprak.models.BookAuthorRel;


@Repository
public class BookAuthorRelDAOImpl extends CommonDAOImpl<BookAuthorRel, Long> implements BookAuthorRelDAO {

    public BookAuthorRelDAOImpl() {
        super(BookAuthorRel.class);
    }
}
