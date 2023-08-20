package com.example.UserLocationApp;

import com.example.UserLocationApp.controllers.UserController;
import com.example.UserLocationApp.models.UserModel;
import com.example.UserLocationApp.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UserLocationAppApiTests {
    private final IUserService userService = mock(IUserService.class);

    private final UserController userController = new UserController(userService);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

    @Test
    public void testCreateUserLocationTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user_location/create_data"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user_location table created"));
    }

    @Test
    public void testUpdateUserLocation() throws Exception {
        UserModel userModel = new UserModel();
        userModel.setName("Balaji R");
        userModel.setLatitude(37.7749);
        userModel.setLongitude(-122.4194);

        when(userService.save(any(UserModel.class))).thenReturn(userModel);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user_location/update_data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"Balaji R\",\n" +
                                "    \"latitude\": 37.7749,\n" +
                                "    \"longitude\": -122.4194\n" +
                                "}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User location updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userLocation.name").value("Balaji R"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userLocation.latitude").value(37.7749))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userLocation.longitude").value(-122.4194));
    }

    @Test
    public void testGetNearestUsers() throws Exception {
        UserModel user1 = new UserModel();
        user1.setName("Balaji R");
        user1.setLatitude(37.7749);
        user1.setLongitude(-122.4194);

        UserModel user2 = new UserModel();
        user2.setName("Jadeja");
        user2.setLatitude(23.7749);
        user2.setLongitude(-22.4194);

        List<UserModel> users = Arrays.asList(user1, user2);

        when(userService.findNearest(2, 0.0, 0.0)).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user_location/get_users/2")
                        .param("latitude", "0.0")
                        .param("longitude", "0.0"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[0].name").value("Balaji R"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[1].name").value("Jadeja"));
    }
}
