package ru.msu.cmc.webprak.controllers;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.msu.cmc.webprak.DAO.BookAuthorRelDAO;
import ru.msu.cmc.webprak.DAO.BookDAO;
import ru.msu.cmc.webprak.DAO.BookGenreRelDAO;
import ru.msu.cmc.webprak.DAO.impl.BookAuthorRelDAOImpl;
import ru.msu.cmc.webprak.DAO.impl.BookDAOImpl;
import ru.msu.cmc.webprak.DAO.impl.BookGenreRelDAOImpl;
import ru.msu.cmc.webprak.models.*;

import java.util.List;


@Controller
public class BookController {
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookGenreRelDAO bookGenreRelDAO;
    @Autowired
    private BookAuthorRelDAO bookAuthorRelDAO;

//    @GetMapping("/addBook")
//    public String showAddBookForm(@RequestParam(value = "id", required = false) Long id,
//                                  @RequestParam(value = "bookName", required = false) String name,
//                                  @RequestParam(value = "genres", required = false) String genres,
//                                  @RequestParam(value = "price", required = false) Integer price,
//                                  @RequestParam(value = "authors", required = false) String authors,
//                                  @RequestParam(value = "is_available", required = false) String isAvailable,
//                                  @RequestParam(value = "num_of_copies", required = false) Integer numOfCopies,
//                                  Model model) {
//
//        Book book = new Book();
//
//        book.setBookName(name);
//        book.setPrice(price);
//        book.setIsAvailable(isAvailable);
//        book.setNumOfCopies(numOfCopies);
//
//        if (genres != null && !genres.isEmpty()) {
//            String[] genreArray = genres.split(",");
//            for (String genre : genreArray) {
//                BookGenreRel bookGenreRel = new BookGenreRel();
//                bookGenreRel.setBook(book);
//                bookGenreRel.setGenre(new Genre(genre.trim()));
//                bookGenreRelDAO.save(bookGenreRel);
//                model.addAttribute("bookGenreRel", bookGenreRel);
//            }
//        }
//
//        if (authors != null && !authors.isEmpty()) {
//            String[] authorArray = authors.split(",");
//            for (String author : authorArray) {
//                BookAuthorRel bookAuthorRel = new BookAuthorRel();
//                bookAuthorRel.setBook(book);
//                bookAuthorRel.setAuthor(new Author(author.trim()));
//                bookAuthorRelDAO.save(bookAuthorRel);
//                model.addAttribute("bookAuthorRel", bookAuthorRel);
//            }
//        }
//
//        model.addAttribute("book", book);
//
//        return "addBook";
//    }

    @GetMapping("/searchBook")
    public String searchBook(@RequestParam(required=false) Long id,
                             @RequestParam(required=false) String name,
                             @RequestParam(required=false) String isAvailable,
                             @RequestParam(required=false) Integer price,
                             @RequestParam(required=false) String author,
                             @RequestParam(required=false) String genre,
                             Model model) {

        if (name != null && name.isEmpty()) {
            name = null;
        }
        if (isAvailable != null && isAvailable.isEmpty()) {
            isAvailable = null;
        }
        if (price != null) {
            price = null;
        }
        if (author != null && author.isEmpty()) {
            author = null;
        }
        if (genre != null && genre.isEmpty()) {
            genre = null;
        }

        BookDAO.Filter filter = BookDAO.getFilterBuilder()
                .id(id)
                .name(name)
                .isAvailable(isAvailable)
                .price(price)
                .author(author)
                .genre(genre)
                .build();
        List<Book> book = bookDAO.searchBooks(filter);

        model.addAttribute("bookList", book);
        model.addAttribute("bookDAO", bookDAO);

        return "books";
    }

    @GetMapping("/bookDetails")
    public String BookPage(@RequestParam(name = "bookId") Long bookId,
                           Model model) {

        Book book = bookDAO.getById(bookId);

        if (book == null) {
            model.addAttribute("error_msg", "В базе нет книги с ID = " + bookId);
            return "errorPage";
        }

        model.addAttribute("book", book);
        model.addAttribute("bookDAO", bookDAO);

        return "bookDetails";
    }

    @PostMapping("/editBook")
    public String editBook(@RequestParam Long id,
                           @RequestParam String name,
//                           @RequestParam String authors,
//                           @RequestParam String genre,
                           @RequestParam Integer price,
                           @RequestParam String isAvailable,
                           @RequestParam Integer numOfCopies,
                           RedirectAttributes redirectAttributes) {

        Book book = bookDAO.getById(id);

        if (book == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Невозможно изменить книгу с ID = " + id);
            return "redirect:/books";
        }

        if (price < 0) {
            redirectAttributes.addFlashAttribute("error_msg", "Цена не может быть отрицательной");
            return "redirect:/books";
        }

        book.setBookName(name);
        book.setPrice(price);
        if (numOfCopies == 0) {
            book.setIsAvailable("no");
            book.setNumOfCopies(numOfCopies);
        }
        else if (isAvailable.equals("no")) {
            book.setIsAvailable("no");
            book.setNumOfCopies(0);
        }
        else {
            book.setIsAvailable("yes");
            book.setNumOfCopies(numOfCopies);
        }

        bookDAO.update(book);

        redirectAttributes.addFlashAttribute("message", "Книга успешно обновлена");

        return "redirect:/books";
    }
}
