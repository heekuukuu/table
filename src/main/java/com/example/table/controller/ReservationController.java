package com.example.table.controller;

import com.example.table.entity.Reservation;
import com.example.table.entity.Store;
import com.example.table.service.ReservationService;
import com.example.table.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private StoreService storeService;

    @Autowired
    private ReservationService reservationService;

    // 예약 폼을 보여주는 메서드
    @GetMapping("/new/{id}")
    public String showReservationForm(@PathVariable(name = "id") Long id, Model model) {
        Store store = storeService.getStoreById(id);
        if (store == null) {
            model.addAttribute("errorMessage", "매장이 존재하지 않습니다.");
            return "error"; // 매장 없음 오류 페이지로 이동
        }

        LocalDate today = LocalDate.now();
        LocalDate nextMonth = today.plusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        model.addAttribute("store", store);
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("minDate", today.format(formatter));
        model.addAttribute("maxDate", nextMonth.format(formatter));
        return "reservation"; // 예약 폼 페이지
    }



// 예약을 생성하는 메서드
@PostMapping("/save")
public String createReservation(@ModelAttribute Reservation reservation,
                                @RequestParam(name = "storeId") Long storeId,
                                RedirectAttributes redirectAttributes) {
    logger.info("Creating reservation for store ID: {}", storeId);

    Store store = storeService.getStoreById(storeId);
    if (store == null) {
        logger.error("Store with id {} not found during reservation creation", storeId);
        redirectAttributes.addFlashAttribute("errorMessage", "매장이 존재하지 않습니다.");
        return "redirect:/error";
    }
    logger.info("Store found: {}", store);
    // 예약에 매장 정보 설정
    reservation.setStore(store);
    try {
        logger.info("Trying to save reservation for store id: {}", storeId);
        reservationService.save(reservation);
        logger.info("Reservation saved successfully: {}", reservation);
        redirectAttributes.addFlashAttribute("successMessage", "예약이 성공적으로 생성되었습니다.");
    } catch (Exception e) {
        logger.error("Error saving reservation", e);
        redirectAttributes.addFlashAttribute("errorMessage", "예약 생성 중 오류가 발생했습니다.");
        return "redirect:/error";
    }
    return "redirect:/reservations/confirmation";
}

    // 예약 확인 페이지를 보여주는 메서드
    @GetMapping("/confirmation")
    public String reservationConfirmation(@ModelAttribute("reservation") Reservation reservation, Model model) {
        model.addAttribute("reservation", reservation);
        return "reservationConfirmation";
    }

    @GetMapping("/list")
    public String listReservations(Model model) {
        List<Reservation> reservation = reservationService.findAll();
        model.addAttribute("reservation", reservation);
        return "reservationList";
    }
}
