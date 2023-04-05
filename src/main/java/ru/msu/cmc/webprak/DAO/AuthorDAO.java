package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.sql.SQLException;

import ru.msu.cmc.webprak.models.Author;

public interface AuthorDAO extends CommonDAO<Author, Long> {
    Collection searchAuthors(OrderDAO.Filter filter) throws SQLException;
    Collection getAllAuthors() throws SQLException;

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
