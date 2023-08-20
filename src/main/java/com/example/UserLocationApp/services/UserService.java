package com.example.UserLocationApp.services;

import com.example.UserLocationApp.models.UserModel;
import com.example.UserLocationApp.repositories.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@Transactional
public class UserService implements IUserService{
    @Autowired
    IUserRepo userRepo;
    @Override
    public void createTable() {
        userRepo.createTable();
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepo.save(userModel);
    }

    @Override
    public List<UserModel> findNearest(Integer limit, Double latitude, Double longtitude) {
        return userRepo.findNearest(limit, latitude, latitude);
    }

}
