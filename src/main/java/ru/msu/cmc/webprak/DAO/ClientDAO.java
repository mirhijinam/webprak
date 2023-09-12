package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import org.springframework.data.util.Pair;

import ru.msu.cmc.webprak.models.*;

import java.util.List;


public interface ClientDAO extends CommonDAO<Client, Long> {
    List<Client> searchClients(Filter filter);
    List<OrderHistory> getOrderHistory(Long clientId);
    List<ClientCityRel> getClientCityRel(Long clientId);
    List<Pair<City, String>> getClientCityAndStreet(Long clientId);
    City getCityByName(String name);
    Client getClientById(Long id);
    List<Order> getOrdersById(Long id);

    @Builder
    @Getter
    class Filter {
        private Long id;
        private String name;
        private String surname;
        private String city;
        private String phone;
        private String email;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
