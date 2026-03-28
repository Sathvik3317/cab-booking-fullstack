package com.CabBooking.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CabBooking.Dto.LoginRequestDto;
import com.CabBooking.Dto.RegisterRequestDto;
import com.CabBooking.Dto.UserResponseDto;
import com.CabBooking.Model.User;
import com.CabBooking.Service.UserService;

/*
 * This controller handles APIs related to users.
 * It exposes endpoints to create users.
 */
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api/users")   // Base URL for user APIs
public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // -------------------------
    // Register API
    // -------------------------
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody RegisterRequestDto request) {
        return userService.register(request);
    }

    // -------------------------
    // Login API
    // -------------------------
    @PostMapping("/login")
    public UserResponseDto login(@RequestBody LoginRequestDto request) {
        return userService.login(request);
    }

    /*
     * Creates a new user in the system.
     * URL  : POST /api/users
     * Body : JSON (User object)
     */
    @PostMapping
    public User createUser(@RequestBody User user) {

        // Delegates saving logic to service layer
        return userService.createUser(user);
    }
}
