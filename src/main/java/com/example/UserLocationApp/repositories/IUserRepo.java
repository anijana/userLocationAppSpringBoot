package com.example.UserLocationApp.repositories;

import com.example.UserLocationApp.models.UserModel;

import java.util.List;

public interface IUserRepo {
    void createTable();
    UserModel save(UserModel usermodel);
    List<UserModel> findNearest(Integer limit, Double latitude, Double longitude);
}
