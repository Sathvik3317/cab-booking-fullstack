//We should not return entities directly.
//We use DTOs to control the response and hide sensitive fields like password.
package com.CabBooking.Dto;

/**
 * DTO returned to client after a booking is created.
 * It wraps user and cab DTOs instead of entities.
 */
public class BookingResponseDto {

    private Long id;
    private String pickupLocation;
    private String dropLocation;
    private String status;

    private UserResponseDto user;
    private CabResponseDto cab;

    public BookingResponseDto() {
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public CabResponseDto getCab() {
        return cab;
    }

    public void setCab(CabResponseDto cab) {
        this.cab = cab;
    }
}
