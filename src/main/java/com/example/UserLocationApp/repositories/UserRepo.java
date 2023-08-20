package com.example.UserLocationApp.repositories;

import com.example.UserLocationApp.models.UserModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserRepo implements IUserRepo{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createTable() {
        entityManager.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS user_location(" +
                        "    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY," +
                        "    name VARCHAR(50) NOT NULL," +
                        "    latitude DOUBLE NOT NULL," +
                        "    longitude DOUBLE NOT NULL," +
                        ")"
        ).executeUpdate();
    }
    @Override
    public UserModel save(UserModel userModel) {
        entityManager.persist(userModel);
        entityManager.flush();
        return userModel;
    }
    @Override
    public List<UserModel> findNearest(Integer limit, Double lat, Double lon) {
        String formula = "6371 * acos(cos(radians(:latitude)) * cos(radians(o.latitude))" +
                " * cos(radians(o.longitude) - radians(:longitude))" +
                " + sin(radians(:latitude)) * sin(radians(o.latitude)))";

        return entityManager
                .createNativeQuery(
                        "SELECT * FROM user_location o " +
                                "ORDER BY " + formula +
                                " ASC LIMIT :limit",
                        UserModel.class)
                .setParameter("latitude", lat)
                .setParameter("longitude", lon)
                .setParameter("limit", limit)
                .getResultList();
    }
}