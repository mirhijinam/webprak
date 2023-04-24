package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.stereotype.Repository;

import ru.msu.cmc.webprak.DAO.BookGenreRelDAO;
import ru.msu.cmc.webprak.models.BookGenreRel;


@Repository
public class BookGenreRelDAOImpl extends CommonDAOImpl<BookGenreRel, Long> implements BookGenreRelDAO {

    public BookGenreRelDAOImpl() {
        super(BookGenreRel.class);
    }
}
