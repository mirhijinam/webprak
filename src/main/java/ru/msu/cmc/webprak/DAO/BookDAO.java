package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import java.sql.SQLException;
import java.util.Collection;

import ru.msu.cmc.webprak.models.Book;

public interface BookDAO extends CommonDAO<Book, Long> {
    Collection searchBooks(Filter filter) throws SQLException;
    Collection getAllBooksByOrderId(Long orderId) throws SQLException;

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

    static BookDAO.Filter.FilterBuilder getFilterBuilder() {
        return BookDAO.Filter.builder();
    }
}
