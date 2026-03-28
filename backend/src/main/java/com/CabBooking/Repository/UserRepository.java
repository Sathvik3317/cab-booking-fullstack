package com.CabBooking.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CabBooking.Model.User;

public interface UserRepository extends JpaRepository<User, Long> { //Provides built-in CRUD methods and pagination support without writing SQL queries.
	// Used for login
	Optional<User> findByEmail(String email); //SELECT * FROM users WHERE email = ?

	 // Used for register validation
    boolean existsByEmail(String email);
	////Why repository?
///Spring Data JPA will now auto-generate SQL
//
//No SQL writing
//
//Spring Data JPA handles CRUD
//
//🧠 Interview:
//
//Repository layer abstracts database access using Spring Data JPA, improving maintainability and reducing boilerplate code.
	

}
