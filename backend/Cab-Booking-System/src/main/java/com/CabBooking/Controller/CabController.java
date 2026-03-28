package com.CabBooking.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CabBooking.Model.Cab;
import com.CabBooking.Service.CabService;

/*
 * This controller handles only cab related requests.
 * It does not contain business logic.
 * It simply calls CabService methods.
 */
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api/cabs")   // Base URL for all cab APIs
public class CabController {

    private final CabService cabService;

    /*
     * Constructor injection of CabService.
     * Spring automatically provides the CabService object.
     */
    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    /*
     * Get all available cabs.
     *
     * URL  : GET /api/cabs/available
     */
    @GetMapping("/available")
    public List<Cab> getAvailableCabs() {

        // Call service layer to fetch available cabs
        return cabService.getAvailableCabs();
    }
    @PostMapping
    public Cab addCab(@RequestBody Cab cab) {
        return cabService.saveCab(cab);
    }
//    addCab is a controller method mapped to a POST API using @PostMapping. Spring automatically calls it when a matching HTTP request 
//    is received, converts the request body into a Cab object, and delegates business logic to the service layer.
}
