package ru.msu.cmc.webprak.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.cmc.webprak.DAO.CityDAO;
import ru.msu.cmc.webprak.DAO.ClientCityRelDAO;
import ru.msu.cmc.webprak.DAO.ClientDAO;
import ru.msu.cmc.webprak.DAO.impl.CityDAOImpl;
import ru.msu.cmc.webprak.DAO.impl.ClientCityRelDAOImpl;
import ru.msu.cmc.webprak.DAO.impl.ClientDAOImpl;
import ru.msu.cmc.webprak.models.Client;
import ru.msu.cmc.webprak.models.ClientCityRel;

import java.util.List;


@Controller
public class ClientController {

    @Autowired
    private ClientDAO clientDAO = new ClientDAOImpl();
    private ClientCityRelDAO clientCityRelDAO = new ClientCityRelDAOImpl();

    @GetMapping("/clients")
    public String getClients(Model model) {

        List<Client> client = (List<Client>) clientDAO.getAll();
        model.addAttribute("client", client);
        model.addAttribute("clientDAO", clientDAO);
        return "clients";
    }

    @GetMapping("/addClient")
    public String showAddClientForm() {

        return "addClient";
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
        Client newClient = new Client();
        newClient.setClientName(name);
        newClient.setEmail(email);
        newClient.setPhone(phone);
        Client savedClient = clientDAO.save(newClient);

        ClientCityRel newClientCityRel = new ClientCityRel();
        newClientCityRel.setClient(savedClient);
        newClientCityRel.setCity(clientDAO.getCityByName(city));
        newClientCityRel.setStreetName(street);
        System.out.println(savedClient.getClientName());
        ClientCityRel savedClientCityRel = clientCityRelDAO.save(newClientCityRel);

        // TODO: Реализовать выпадающий список городов

        redirectAttributes.addFlashAttribute("message", "Клиент успешно добавлен");
        return "redirect:/clients";
    }

    @GetMapping("/clientDetails")
    public String PersonPage(@RequestParam(name = "clientId") Long clientId, Model model) {

        Client client = clientDAO.getById(clientId);

        if (client == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + clientId);
            return "errorPage";
        }

        model.addAttribute("client", client);
        // TODO: Дополнить персональную страницу клиента

        return "clientDetails";

    }
    @PostMapping("/deleteClient")
    public String deleteClient(@RequestParam Long clientId, RedirectAttributes redirectAttributes) {
        Client client = clientDAO.getById(clientId);

        if (client == null) {
            redirectAttributes.addFlashAttribute("error_msg", "Невозможно удалить клиента с ID = " + clientId);
            return "redirect:/clients";
        }

        clientDAO.delete(client);

        redirectAttributes.addFlashAttribute("message", "Клиент успешно удален");

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

        client.setClientName(name);
        client.setEmail(email);
        client.setPhone(phone);

        Client updatedClient = clientDAO.update(client);

        // TODO: Реализовать выпадающий список городов
        // TODO: Добавить изменение улицы клиента

        redirectAttributes.addFlashAttribute("message", "Клиент успешно обновлен");

        return "redirect:/clients";
    }
}
