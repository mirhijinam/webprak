package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.stereotype.Repository;

import ru.msu.cmc.webprak.DAO.CityDAO;
import ru.msu.cmc.webprak.models.City;


@Repository
public class CityDAOImpl extends CommonDAOImpl<City, Long> implements CityDAO {

    public CityDAOImpl() {
        super(City.class);
    }
}
