package com.CabBooking.Dto;

/**
 * DTO used to send user details to client.
 * Password is intentionally not included.
 */
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;

    // Empty constructor required for frameworks
    public UserResponseDto() {
    }

    // Convenience constructor to build DTO from values
    public UserResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters (simple and interview friendly)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
