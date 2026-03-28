package com.CabBooking.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CabBooking.Model.Cab;
import com.CabBooking.Repository.CabRepository;

@Service
public class CabService {

    @Autowired
    private CabRepository cabRepository;

    public List<Cab> getAvailableCabs() {
        return cabRepository.findByAvailableTrue();
    }

    public Cab saveCab(Cab cab) {      //Persistence Logic
        return cabRepository.save(cab);
    }
//    What happens internally?
//    		If id == null → INSERT				If id != null → UPDATE
//    		✔️ Same method handles both create & update
}
