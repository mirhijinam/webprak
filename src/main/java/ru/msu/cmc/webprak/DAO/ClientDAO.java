package ru.msu.cmc.webprak.DAO;


import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.sql.SQLException;

import ru.msu.cmc.webprak.models.Client;

public interface ClientDAO extends CommonDAO<Client, Long> {

    Collection searchClients(Filter filter) throws SQLException;
    Collection getAllClients() throws SQLException;
    Collection getAllOrdersByClientId(Long clientId) throws SQLException;

    @Builder
    @Getter
    class Filter {
        private Long id;
        private String name;
        private String surname;
        private String phone;
        private String mail;
        private String city;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
