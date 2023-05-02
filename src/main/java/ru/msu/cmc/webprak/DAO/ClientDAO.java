package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import org.springframework.data.util.Pair;

import ru.msu.cmc.webprak.models.Client;
import ru.msu.cmc.webprak.models.ClientCityRel;
import ru.msu.cmc.webprak.models.OrderHistory;
import ru.msu.cmc.webprak.models.City;

import java.util.List;


public interface ClientDAO extends CommonDAO<Client, Long> {

    List<Client> searchClients(Filter filter);
    List<OrderHistory> getOrderHistory(Long clientId);
    List<ClientCityRel> getClientCityRel(Long clientId);
    List<Pair<City, String>> getClientCityAndStreet(Long clientId);

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
