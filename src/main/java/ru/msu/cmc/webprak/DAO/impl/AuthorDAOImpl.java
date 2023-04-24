package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.AuthorDAO;
import ru.msu.cmc.webprak.models.Author;


@Repository
public class AuthorDAOImpl extends CommonDAOImpl<Author, Long> implements AuthorDAO {

    public AuthorDAOImpl() {
        super(Author.class);
    }
}
