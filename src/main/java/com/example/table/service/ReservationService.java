package com.example.table.service;
import com.example.table.entity.Reservation;
import com.example.table.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

  @Transactional
    public Reservation save(Reservation reservation) {
     return reservationRepository.save(reservation);
    }
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

}



