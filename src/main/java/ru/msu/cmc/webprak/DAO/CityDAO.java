package ru.msu.cmc.webprak.DAO;


import com.impossibl.postgres.api.data.Interval;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.sql.SQLException;

import ru.msu.cmc.webprak.models.City;

public interface CityDAO extends CommonDAO<City, Long> {
    Collection searchCities(OrderDAO.Filter filter) throws SQLException;
    Collection getAllCities() throws SQLException;

    @Builder
    @Getter
    class Filter {
        private Long id;
        private String name;
        private String country;
        private Interval deliveryDays;
    }

    static ClientDAO.Filter.FilterBuilder getFilterBuilder() {
        return ClientDAO.Filter.builder();
    }
}
