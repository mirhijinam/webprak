package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.stereotype.Repository;

import ru.msu.cmc.webprak.DAO.ClientCityRelDAO;
import ru.msu.cmc.webprak.models.ClientCityRel;


@Repository
public class ClientCityRelDAOImpl extends CommonDAOImpl<ClientCityRel, Long> implements ClientCityRelDAO {

    public ClientCityRelDAOImpl() {
        super(ClientCityRel.class);
    }
}
