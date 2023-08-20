package com.example.UserLocationApp.controllers;

import com.example.UserLocationApp.models.UserModel;
import com.example.UserLocationApp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user_location")
public class UserController {
    @Autowired
    IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @PostMapping("/create_data")
    @ResponseStatus(HttpStatus.OK)
    public String createUserLocationTable() {
        userService.createTable();
        return "user_location table created";
    }

    @PostMapping("/update_data")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> updateUserLocation(
            @RequestBody UserModel userModel
    ) {
        var res = new HashMap<String, Object>();
        res.put("message", "User location updated");
        res.put("userLocation", userService.save(userModel));
        return res;
    }

    @GetMapping("/get_users/{limit}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<UserModel>> getNearestUsers(
            @PathVariable Integer limit, @RequestParam Double latitude, @RequestParam Double longitude
    ) {
        var res = new HashMap<String, List<UserModel>>();
        res.put("users", userService.findNearest(limit, latitude, longitude));
        return res;
    }
}
