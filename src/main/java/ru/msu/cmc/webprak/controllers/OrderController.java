package ru.msu.cmc.webprak.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.msu.cmc.webprak.DAO.*;
import ru.msu.cmc.webprak.models.*;

import java.util.*;


@Controller
public class OrderController {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private OrderingBookRelDAO orderingBookRelDAO;
    @Autowired
    private OrderHistoryDAO orderHistoryDAO;

    @GetMapping("/addOrder")
    public String viewClientsAndBooks(Model model) {

        List<Client> clientList = (List<Client>) clientDAO.getAll();
        List<Book> bookList = (List<Book>) bookDAO.getAll();

        model.addAttribute("clientList", clientList);
        model.addAttribute("bookList", bookList);

        return "orderAdd";
    }

    public static List<Integer> removeZeros(List<Integer> inputList) {
        List<Integer> cleanedList = new ArrayList<>();

        for (Integer num : inputList) {
            if (num != 0) {
                cleanedList.add(num);
            }
        }

        return cleanedList;
    }

    @PostMapping("/addOrder")
    public String addOrder(RedirectAttributes redirectAttributes,
                           @RequestParam(name = "selectedBooks") List<Long> selectedBooksIds,
                           @RequestParam(name = "bookQuantities") List<Integer> bookQuantities,
                           @RequestParam(name = "selectedClient") Long selectedClientId) {

        bookQuantities = removeZeros(bookQuantities);
        int orderCost = 0;
        System.out.println(bookQuantities);
        Map<Long, Integer> association = new HashMap<>();
        for (int i = 0; i < selectedBooksIds.size(); i++) {
            Long bookId = selectedBooksIds.get(i);
            Integer quantity = bookQuantities.get(i);
            System.out.println(bookId + " " + quantity);
            association.put(bookId, quantity);
            orderCost += quantity * bookDAO.getBookById(bookId).getPrice();
            System.out.println(bookId + " " + quantity + " " + bookDAO.getBookById(bookId).getPrice());

            if (bookDAO.getBookById(bookId).getIsAvailable().equals("no")) {
                redirectAttributes.addFlashAttribute("error_msg", "Книга на складе недоступна");
                return "booksNotEnough";
            }
            if (bookDAO.getBookById(bookId).getNumOfCopies() < association.get(bookId)) {
                System.out.println("Hello");
                redirectAttributes.addFlashAttribute("error_msg", "Недостаточно книг на складе");
                return "booksNotEnough";
            }
        }

        Order newOrder = new Order();
        newOrder.setOrderStatus(Order.OrderStatus.valueOf("NEW"));
        Date now = new Date();
        newOrder.setCreationData(now);
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        int minDelivery = 2;
        int maxDelivery = 5;
        int diff = maxDelivery - minDelivery;
        Random random = new Random();
        int delivery = random.nextInt(diff + 1) + minDelivery;
        c.add(Calendar.DATE, delivery);
        newOrder.setDeliveryData(now);
        newOrder.setPrice(orderCost);
        Order savedOrder = orderDAO.save(newOrder);

        for (Long bookId : selectedBooksIds) {
            int quantity = association.get(bookId);
            OrderingBookRel newOrderingBookRel = new OrderingBookRel();
            newOrderingBookRel.setOrder(savedOrder);
            newOrderingBookRel.setBook(bookDAO.getBookById(bookId));
            newOrderingBookRel.setAmount(quantity);
            newOrderingBookRel.setTotal_cost(orderCost);
            orderingBookRelDAO.save(newOrderingBookRel);

            Book book = bookDAO.getBookById(bookId);
            book.setNumOfCopies(book.getNumOfCopies() - quantity);
            System.out.println("AAAAAAAAAA" + quantity);
            if (book.getNumOfCopies() == 0) {
                book.setIsAvailable("no");
            }
            bookDAO.update(book);
        }

        OrderHistory newOrderHistory = new OrderHistory();
        newOrderHistory.setOrder(savedOrder);
        newOrderHistory.setClient(clientDAO.getClientById(selectedClientId));
        orderHistoryDAO.save(newOrderHistory);

        redirectAttributes.addFlashAttribute("message", "Заказ успешно добавлен");

        return "redirect:/orders";
    }

    @GetMapping("/searchOrder")
    public String searchOrder(@RequestParam(required=false) Long id,
                              @RequestParam(required=false) Order.OrderStatus orderStatus,
                              @RequestParam(required=false) Integer price,
                              @RequestParam(required=false) Date creationData,
                              @RequestParam(required=false) Date deliveryData,
                              Model model) {

        if (price != null) {
            price = null;
        }
        if (creationData != null) {
            creationData = null;
        }
        if (deliveryData != null) {
            deliveryData = null;
        }
        if (orderStatus != null) {
            orderStatus = null;
        }

        OrderDAO.Filter filter = OrderDAO.getFilterBuilder()
                .orderId(id)
                .orderStatus(orderStatus)
                .price(price)
                .creationData(creationData)
                .deliveryData(deliveryData)
                .build();
        List<Order> order = orderDAO.searchOrders(filter);
        model.addAttribute("orderList", order);
        model.addAttribute("orderDAO", orderDAO);

        return "orders";
    }

    @GetMapping("/orderDetails")
    public String OrderPage(@RequestParam(name = "orderId") Long orderId,
                            Model model) {

        Order order = orderDAO.getById(orderId);

        if (order == null) {
            model.addAttribute("error_msg", "В базе нет заказа с ID = " + orderId);
            return "errorPage";
        }

        List<Book> books = new ArrayList<>();
        for (OrderingBookRel orderingBookRel : orderingBookRelDAO.getAll()) {
            if (Objects.equals(orderingBookRel.getOrder().getId(), orderId)) {
                books.add(bookDAO.getBookById(orderingBookRel.getBook().getId()));
            }

            model.addAttribute("orderingBookRel", orderingBookRel);
        }

        List<OrderingBookRel> orderingBookRelList = new ArrayList<>();
        for (OrderingBookRel orderingBookRel : orderingBookRelDAO.getAll()) {
            if (Objects.equals(orderingBookRel.getOrder().getId(), orderId)) {
                orderingBookRelList.add(orderingBookRelDAO.getById(orderingBookRel.getId()));
            }
        }

        model.addAttribute("orderingBookRelList", orderingBookRelList);
        model.addAttribute("order", order);
        model.addAttribute("orderDAO", orderDAO);
        model.addAttribute("books", books);
        model.addAttribute("bookDAO", bookDAO);
        model.addAttribute("orderingBookRelDAO", orderingBookRelDAO);

        return "orderDetails";
    }

    @PostMapping("/DeleteBooksFromOrder")
    public String deleteBooksFromOrder(RedirectAttributes redirectAttributes,
                              @RequestParam(name = "orderId") Long orderId,
                              @RequestParam(name = "selectedBooksToDelete") List<Long> selectedBooksToDelete,
                              @RequestParam(name = "quantityToDelete") List<Integer> quantityToDelete,
                              Model model) {

        Order order = orderDAO.getById(orderId);

        if (order == null) {
            model.addAttribute("error_msg", "В базе нет заказа с ID = " + orderId);
            return "errorPage";
        }

        Map<Long, Integer> association = new HashMap<>();
        for (int i = 0; i < selectedBooksToDelete.size(); i++) {
            Long bookId = selectedBooksToDelete.get(i);
            Integer quantity = quantityToDelete.get(i);
            association.put(bookId, quantity);
        }

        for (Long orderingBookId : selectedBooksToDelete) {
            int quantity = association.get(orderingBookId);

            OrderingBookRel orderingBook = orderingBookRelDAO.getById(orderingBookId);
            Book book = bookDAO.getBookById(orderingBook.getBook().getId());
            order.setPrice(order.getPrice() - book.getPrice() * quantity);
            orderDAO.update(order);
            int updatedNumOfCopies = book.getNumOfCopies() + quantity;
            book.setNumOfCopies(updatedNumOfCopies);
            if (updatedNumOfCopies > 0 && book.getIsAvailable().equals("no")) {
                book.setIsAvailable("yes");
            }
            bookDAO.update(book);
            if (quantity == orderingBookRelDAO.getById(orderingBookId).getAmount()) {
                orderingBookRelDAO.delete(orderingBookRelDAO.getById(orderingBookId));
            } else {
                int newAmount = orderingBookRelDAO.getById(orderingBookId).getAmount() - quantity;
                orderingBook.setAmount(newAmount);
                int newTotal_cost = newAmount * book.getPrice();
                orderingBook.setTotal_cost(newTotal_cost);
                orderingBookRelDAO.update(orderingBook);
                if (newAmount == 0) {
                    orderingBookRelDAO.delete(orderingBookRelDAO.getById(orderingBookId));
                }
            }
            if (order.getPrice() == 0) {
                orderDAO.delete(order);

                return "redirect:/orders";
            }
        }

        redirectAttributes.addFlashAttribute("message", "Заказ успешно изменен");

        return "redirect:/orderDetails?orderId=" + orderId;
    }

    @PostMapping("/editOrderStatus")
    public String editOrderStatus(RedirectAttributes redirectAttributes,
                                  @RequestParam Long orderId,
                                  @RequestParam Order.OrderStatus orderStatus) {

        Order order = orderDAO.getById(orderId);

        if (order == null) {
            redirectAttributes.addFlashAttribute("error_msg",
                    "Невозможно изменить статус заказа с ID = " + orderId);
            return "redirect:/orders";
        }

        order.setOrderStatus(orderStatus);
        orderDAO.update(order);

        redirectAttributes.addFlashAttribute("message", "Статус заказа успешно изменен");

        return "redirect:/orderDetails?orderId=" + orderId;
    }
}
