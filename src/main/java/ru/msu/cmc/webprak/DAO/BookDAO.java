package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import ru.msu.cmc.webprak.models.Book;
import ru.msu.cmc.webprak.models.Genre;
import ru.msu.cmc.webprak.models.Author;

import java.util.List;


public interface BookDAO extends CommonDAO<Book, Long> {

    List<Book> searchBooks(Filter filter);
    List<Genre> getBookGenres(Long bookId);
    List<Author> getBookAuthors(Long bookId);

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
