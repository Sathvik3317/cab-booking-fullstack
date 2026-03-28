//In my project, UserService acts as the business layer for user operations. It uses UserRepository to persist user data. 
//The save() method handles both insert and update operations depending on whether the user ID is present. 
//This keeps controllers clean and follows proper layered architecture.
package com.CabBooking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CabBooking.Dto.LoginRequestDto;
import com.CabBooking.Dto.RegisterRequestDto;
import com.CabBooking.Dto.UserResponseDto;
import com.CabBooking.Model.User;
import com.CabBooking.Repository.UserRepository;

@Service		//Field injection hides dependencies, making the class harder to understand and maintain.
public class UserService {

//    @Autowired
//    private UserRepository userRepository;
//
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
		
	    private final UserRepository userRepository;  //Immutability cannot be changed 
	    public UserService(UserRepository userRepository) {		//This class cannot exist without UserRepository Dependencies are EXPLICIT ⭐
	        this.userRepository = userRepository;
	    }
	    public User createUser(User user) {

	        // Executes: INSERT into users table
	        return userRepository.save(user);
	    }
	    // -------------------------
	    // Register new user
	    // -------------------------
	    public UserResponseDto register(RegisterRequestDto request) {

	        // Check email already exists
	        if (userRepository.existsByEmail(request.getEmail())) {
	            throw new RuntimeException("Email already registered");
	        }

	        // Create new user
	        User user = new User();
	        user.setName(request.getName());
	        user.setEmail(request.getEmail());
	        user.setPassword(request.getPassword()); // (plain for now)

	        User savedUser = userRepository.save(user);

	        return mapToDto(savedUser);
	    }

	    // -------------------------
	    // Login
	    // -------------------------
	    public UserResponseDto login(LoginRequestDto request) {

	        User user = userRepository.findByEmail(request.getEmail())
	        		.orElseThrow(() -> new RuntimeException("Invalid email"));

	        // Simple password check
	        if (!user.getPassword().equals(request.getPassword())) {
	            throw new RuntimeException("Invalid password");
	        }

	        return mapToDto(user);
	    }

	    // -------------------------
	    // Convert entity to safe DTO
	    // -------------------------
	    private UserResponseDto mapToDto(User user) {

	        UserResponseDto dto = new UserResponseDto();
	        dto.setId(user.getId());
	        dto.setName(user.getName());
	        dto.setEmail(user.getEmail());

	        return dto;
	    }
	
}
//In Spring, both field injection and constructor injection work, but constructor injection is preferred because 
//it makes dependencies explicit, improves testability, prevents partially constructed objects, and supports immutability. 
//Field injection hides dependencies and tightly couples the class to the Spring framework.

