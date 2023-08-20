package com.example.UserLocationApp.services;

import com.example.UserLocationApp.models.UserModel;

import java.util.List;

public interface IUserService {
    void createTable();
    UserModel save(UserModel usermodel);
    List<UserModel> findNearest(Integer limit, Double latitude, Double longtitude);
}
