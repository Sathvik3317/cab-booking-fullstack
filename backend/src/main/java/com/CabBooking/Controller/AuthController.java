package com.CabBooking.Controller;

import org.springframework.web.bind.annotation.*;

import com.CabBooking.Dto.LoginRequestDto;
import com.CabBooking.Dto.RegisterRequestDto;
import com.CabBooking.Dto.UserResponseDto;
import com.CabBooking.Service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
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
}
