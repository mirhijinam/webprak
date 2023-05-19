package ru.msu.cmc.webprak.DAO.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

import ru.msu.cmc.webprak.DAO.CityDAO;
import ru.msu.cmc.webprak.DAO.ClientDAO;
import ru.msu.cmc.webprak.DAO.OrderHistoryDAO;
import ru.msu.cmc.webprak.DAO.ClientCityRelDAO;
import ru.msu.cmc.webprak.models.*;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class ClientDAOImpl extends CommonDAOImpl<Client, Long> implements ClientDAO {

    public ClientDAOImpl() {
        super(Client.class);
    }

    @Autowired
    private OrderHistoryDAO orderHistoryDAO = new OrderHistoryDAOImpl();
    @Autowired
    private ClientCityRelDAO clientCityRelDAO = new ClientCityRelDAOImpl();
    @Autowired
    private CityDAO cityDAO = new CityDAOImpl();

    @Override
    public List<Client> searchClients(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);
            Root<Client> clientRoot = criteriaQuery.from(Client.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null)
                predicates.add(builder.equal(clientRoot.get("id"), filter.getId()));

            if (filter.getName() != null)
                predicates.add(builder.like(clientRoot.get("clientName"), "%" + filter.getName() + "%"));

            if (filter.getSurname() != null)
                predicates.add(builder.like(clientRoot.get("clientName"), "%" + filter.getSurname() + "%"));

            if (filter.getCity() != null && !filter.getCity().isEmpty()) {
                Subquery<Long> clientCityRelSubquery = criteriaQuery.subquery(Long.class);
                Root<ClientCityRel> clientCityRelRoot = clientCityRelSubquery.from(ClientCityRel.class);
                clientCityRelSubquery.select(clientCityRelRoot.get("client").get("id"))
                        .where(clientCityRelRoot.get("city").get("cityName").in(filter.getCity()));
                predicates.add(clientRoot.get("id").in(clientCityRelSubquery));
            }

            if (filter.getPhone() != null)
                predicates.add(builder.like(clientRoot.get("phone"), "%" + filter.getPhone() + "%"));

            if (filter.getEmail() != null) {
                predicates.add(builder.like(clientRoot.get("email"), "%" + filter.getEmail() + "%"));
            }

            if (!predicates.isEmpty())
                criteriaQuery.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<OrderHistory> getOrderHistory(Long clientId) {
        List<OrderHistory> ret = new ArrayList<>();
        for(OrderHistory orderHistory: orderHistoryDAO.getAll()) {
            if (Objects.equals(orderHistory.getClient().getId(), clientId)) {
                ret.add(orderHistory);
            }
        }
        return ret;
    }

    @Override
    public List<ClientCityRel> getClientCityRel(Long clientId) {
        List<ClientCityRel> ret = new ArrayList<>();
        for(ClientCityRel clientCityRel: clientCityRelDAO.getAll()) {
            if (Objects.equals(clientCityRel.getClient().getId(), clientId)) {
                ret.add(clientCityRel);
            }
        }
        return ret;
    }

    @Override
    public List<Pair<City, String>> getClientCityAndStreet(Long clientId) {
        List<Pair<City, String>> ret = new ArrayList<>();
        for (ClientCityRel clientCityRel : clientCityRelDAO.getAll()) {
            if (Objects.equals(clientCityRel.getClient().getId(), clientId)) {
                ret.add(Pair.of(clientCityRel.getCity(), clientCityRel.getStreetName()));
            }
        }
        return ret;
    }

    @Override
    public City getCityByName(String name) {
        for (City city : cityDAO.getAll()) {
            if (Objects.equals(city.getCityName(), name)) {
                return city;
            }
        }
        return null;
    }
}

