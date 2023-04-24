package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.stereotype.Repository;

import ru.msu.cmc.webprak.DAO.GenreDAO;
import ru.msu.cmc.webprak.models.Genre;


@Repository
public class GenreDAOImpl extends CommonDAOImpl<Genre, Long> implements GenreDAO {

    public GenreDAOImpl() {
        super(Genre.class);
    }
}
