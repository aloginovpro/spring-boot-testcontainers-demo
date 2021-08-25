package ru.mts.trading.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class DbUtil {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void createRecord(Object record) {
        entityManager.persist(record);
        entityManager.flush();
    }

}
