package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.sql.SQLException;

import ru.msu.cmc.webprak.models.Genre;

public interface GenreDAO extends CommonDAO<Genre, Long> {
    Collection searchGenres(OrderDAO.Filter filter) throws SQLException;
    Collection getAllGenres() throws SQLException;

    @Builder
    @Getter
    class Filter {
        private Long id;
        private String name;
    }

    static ClientDAO.Filter.FilterBuilder getFilterBuilder() {
        return ClientDAO.Filter.builder();
    }
}
