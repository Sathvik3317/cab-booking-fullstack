//CabRepository uses Spring Data JPA derived query methods to fetch available cabs without writing SQL, and 
//CabService encapsulates the business logic for cab management.
package com.CabBooking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CabBooking.Model.Cab;

public interface CabRepository extends JpaRepository<Cab, Long> {

    List<Cab> findByAvailableTrue();   //SELECT * FROM cab WHERE available = true;
//    JPQL
//    @Query("SELECT c FROM Cab c WHERE c.available = true")
//    List<Cab> findAvailableCabs();

    //findByAvailableTrue() is a convenience method for boolean fields and improves readability.
}
