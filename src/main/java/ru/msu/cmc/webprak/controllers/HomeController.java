package ru.msu.cmc.webprak.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.msu.cmc.webprak.models.Client;
import ru.msu.cmc.webprak.models.Book;
import ru.msu.cmc.webprak.models.Order;
import ru.msu.cmc.webprak.DAO.BookDAO;
import ru.msu.cmc.webprak.DAO.ClientDAO;
import ru.msu.cmc.webprak.DAO.OrderDAO;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private OrderDAO orderDAO;

    @RequestMapping(value={"/", "/clients"})
    public String index(Model model) {
        List<Client> client = (List<Client>) clientDAO.getAll();
        model.addAttribute("client", client);
        model.addAttribute("clientDAO", clientDAO);
        return "clients";
    }

    @RequestMapping(value={"/books"})
    public String books(Model model) {
        List<Book> book = (List<Book>) bookDAO.getAll();
        model.addAttribute("book", book);
        model.addAttribute("bookDAO", bookDAO);
        return "books";
    }

    @RequestMapping(value={"/orders"})
    public String orders(Model model) {
        List<Order> order = (List<Order>) orderDAO.getAll();
        model.addAttribute("order", order);
        return "orders";
    }
}
