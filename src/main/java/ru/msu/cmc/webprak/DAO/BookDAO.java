package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import java.sql.SQLException;
import java.util.Collection;

import ru.msu.cmc.webprak.models.Book;

public interface BookDAO extends CommonDAO<Book, Long> {
    Collection searchBooks(Filter filter) throws SQLException;
    public Collection getAllBooks() throws SQLException;
    public Collection getAllBooksByOrderId(Long orderId) throws SQLException;

    @Builder
    @Getter
    class Filter {
        private Long id;
        private String name;
        private String isAvailable;
        private String price;
        private String author;
        private String genre;
    }

    static ClientDAO.Filter.FilterBuilder getFilterBuilder() {
        return ClientDAO.Filter.builder();
    }
}
