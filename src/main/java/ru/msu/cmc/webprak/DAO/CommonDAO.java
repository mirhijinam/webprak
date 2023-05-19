package ru.msu.cmc.webprak.DAO;


import ru.msu.cmc.webprak.models.CommonEntity;

import java.util.Collection;


public interface CommonDAO<T extends CommonEntity<ID>, ID> {

    T getById(ID id);

    Collection<T> getAll();

    T save(T entity);

    void saveCollection(Collection<T> entities);

    void delete(T entity);

    void deleteById(ID id);

    T update(T entity);
}
