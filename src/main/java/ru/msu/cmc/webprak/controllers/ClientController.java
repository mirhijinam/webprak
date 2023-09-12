package ru.msu.cmc.webprak.controllers;


import com.impossibl.postgres.types.PsuedoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.cmc.webprak.DAO.*;
import ru.msu.cmc.webprak.models.*;

import java.util.List;
import java.util.Objects;


@Controller
public class ClientController {

    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private ClientCityRelDAO clientCityRelDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderingBookRelDAO orderingBookRelDAO;
    @Autowired
    private OrderHistoryDAO orderHistoryDAO;

    @GetMapping("/addClient")
    public String showAddClientForm() {

        return "clientAdd";
    }

    @GetMapping("/searchClient")
    public String searchClient(@RequestParam(required=false) Long id,
                               @RequestParam(required=false) String name,
                               @RequestParam(required=false) String surname,
                               @RequestParam(required=false) String city,
                               @RequestParam(required=false) String phone,
                               @RequestParam(required=false) String email,
                               Model model) {

        if (name != null && name.isEmpty()) {
            name = null;
        }
        if (surname != null && surname.isEmpty()) {
            surname = null;
        }
        if (city != null && city.isEmpty()) {
            city = null;
        }
        if (phone != null && phone.isEmpty()) {
            phone = null;
        }
        if (email != null && email.isEmpty()) {
            email = null;
        }

        ClientDAO.Filter filter = ClientDAO.getFilterBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .city(city)
                .phone(phone)
                .email(email)
                .build();
        List<Client> client = clientDAO.searchClients(filter);

        model.addAttribute("clientList", client);
        model.addAttribute("clientDAO", clientDAO);

        return "clients";
    }

    @GetMapping("/clientDetails")
    public String PersonPage(@RequestParam(name = "clientId") Long clientId, Model model) {

        Client client = clientDAO.getById(clientId);
        List<OrderHistory> orderHistoryList = clientDAO.getOrderHistory(clientId);


        if (client == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + clientId);
            return "errorPage";
        }

        model.addAttribute("clientDAO", clientDAO);
        model.addAttribute("client", client);
        model.addAttribute("orderHistoryList", orderHistoryList);

        return "clientDetails";

    }

    @PostMapping("/addClient")
    public String addClient(RedirectAttributes redirectAttributes,
                            @RequestParam String name,
                            @RequestParam String city,
                            @RequestParam String street,
                            @RequestParam String email,
                            @RequestParam String phone) {

        if (clientDAO.getCityByName(city) == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Город не найден");
            return "redirect:/addClient";
        }

        for (Client clnt : clientDAO.getAll()) {
            if (Objects.equals(clnt.getEmail(), email)) {
                redirectAttributes.addFlashAttribute("error_msg",
                        "Пользователь с таким email уже существует");
                return "clientMailOrPhoneMatches";
            }
            if (Objects.equals(clnt.getPhone(), phone)) {
                redirectAttributes.addFlashAttribute("error_msg",
                        "Пользователь с таким номером телефона уже существует");
                return "clientMailOrPhoneMatches";
            }
        }

        Client newClient = new Client();
        newClient.setClientName(name);
        newClient.setEmail(email);
        newClient.setPhone(phone);
        Client savedClient = clientDAO.save(newClient);

        ClientCityRel newClientCityRel = new ClientCityRel();
        newClientCityRel.setClient(savedClient);
        newClientCityRel.setCity(clientDAO.getCityByName(city));
        newClientCityRel.setStreetName(street);
        clientCityRelDAO.save(newClientCityRel);

        redirectAttributes.addFlashAttribute("message", "Клиент успешно добавлен");
        return "redirect:/clients";
    }

    @PostMapping("/editClient")
    public String editClient(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam String city,
                             @RequestParam String street,
                             @RequestParam String email,
                             @RequestParam String phone,
                             RedirectAttributes redirectAttributes) {

        Client client = clientDAO.getById(id);

        if (client == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Невозможно изменить клиента с ID = " + id);
            return "redirect:/clients";
        }

        for (Client eachClient : clientDAO.getAll()) {
            if (Objects.equals(eachClient.getEmail(), email) && !Objects.equals(eachClient.getId(), id)) {
                redirectAttributes.addFlashAttribute("error_msg",
                        "Пользователь с таким email уже существует");
                return "redirect:/clients";
            }
            if (Objects.equals(eachClient.getPhone(), phone) && !Objects.equals(eachClient.getId(), id)) {
                redirectAttributes.addFlashAttribute("error_msg",
                        "Пользователь с таким номером телефона уже существует");
                return "redirect:/clients";
            }
        }

        client.setClientName(name);

        client.setEmail(email);
        client.setPhone(phone);

        List<ClientCityRel> clientCityRelList = clientDAO.getClientCityRel(id);

        clientCityRelList.get(0).setStreetName(street);
        clientCityRelList.get(0).setCity(clientDAO.getCityByName(city));

        clientCityRelDAO.update(clientCityRelList.get(0));

        clientDAO.update(client);

        redirectAttributes.addFlashAttribute("message", "Клиент успешно обновлен");

        return "redirect:/clients";
    }

    @PostMapping("/deleteClient")
    public String deleteClient(@RequestParam Long clientId, RedirectAttributes redirectAttributes) {
        Client client = clientDAO.getById(clientId);

        if (client == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Невозможно удалить клиента с ID = " + clientId);
            return "redirect:/clients";
        }

        List<Order> orderListToDelete = clientDAO.getOrdersById(clientId);
        for (Order order : orderListToDelete) {
            List<OrderingBookRel> orderingBookRelList = orderDAO.getOrderingBookRelList(order.getId());
            for (OrderingBookRel orderingBookRel : orderingBookRelList) {
                orderingBookRelDAO.delete(orderingBookRel);
            }
            List<OrderHistory> orderHistoryList = clientDAO.getOrderHistory(client.getId());
            for (OrderHistory orderHistory : orderHistoryList) {
                orderHistoryDAO.delete(orderHistory);
            }
            orderDAO.delete(order);
        }
        clientDAO.delete(client);


        redirectAttributes.addFlashAttribute("message", "Клиент успешно удален");

        return "redirect:/clients";
    }
}
