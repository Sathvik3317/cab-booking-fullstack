package com.CabBooking.Dto;

/**
 * DTO used to expose cab details safely.
 */
public class CabResponseDto {

    private Long id;
    private String driverName;
    private String carNumber;
    private boolean available;

    public CabResponseDto() {
    }

    public CabResponseDto(Long id, String driverName, String carNumber, boolean available) {
        this.id = id;
        this.driverName = driverName;
        this.carNumber = carNumber;
        this.available = available;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
