package com.example.table.controller;

import com.example.table.dto.StoreRequestDto;
import com.example.table.entity.Store;
import com.example.table.service.ManagerService;
import com.example.table.service.StoreService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService; // 매니저 서비스 의존성 주입

    @Autowired
    private StoreService storeService; // StoreService 의존성 주입


    private Logger logger; // 로그를 기록하기 위한 Logger

    // 매니저 페이지를 보여주는 메서드
    @GetMapping("")
    public String managerPage() {
        return "manager"; // manager.html로 이동
    }

    // 모든 매장 목록을 가져오는 메서드
    @GetMapping("/stores")
    public String getAllStores(Model model) {
        List<Store> stores = managerService.getAllStores(); // 모든 매장을 서비스에서 가져옴
        model.addAttribute("stores", stores); // 모델에 매장 목록을 추가

        return "manager"; // manager 페이지로 이동
    }

    // 매장 추가 폼을 보여주는 메서드
    @GetMapping("/add-store")
    public String addStoreForm(Model model) {
        model.addAttribute("store", new StoreRequestDto()); // 새로운 StoreRequestDto를 모델에 추가
        return "store-form"; // store-form.html로 이동
    }

    // 새로운 매장을 저장하는 메서드
    @PostMapping("/save-store")
    public String saveStore(@ModelAttribute StoreRequestDto storeRequestDto) {
        managerService.saveStore(storeRequestDto); // 매장 정보를 저장
        return "redirect:/manager/stores"; // 매장 목록 페이지로 리다이렉트
    }

    // 매장 수정 폼을 보여주는 메서드
    @GetMapping("/edit-store/{id}")
    public String showEditStoreForm(@PathVariable("id") Long id, Model model) {
        Store store = storeService.getStoreById(id); // 매장 ID로 매장 정보 조회
        if (store == null) {
            return "error"; // 매장이 없으면 에러 페이지로 이동
        }
        model.addAttribute("store", store); // 모델에 매장 정보를 추가
        return "edit-store"; // edit-store.html 템플릿을 반환
    }

    // 매장 정보를 업데이트하는 메서드
    @PostMapping("/update-store/{id}")
    public String updateStore(@PathVariable("id") Long id, @ModelAttribute Store store,
                              RedirectAttributes redirectAttributes) {
        try {
            storeService.updateStore(id, store);
            redirectAttributes.addFlashAttribute("message", "매장이 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            // 예외 발생 시 로그 기록
            logger.error("Error updating store with id: " + id, e);
            redirectAttributes.addFlashAttribute("errorMessage", "매장을 업데이트하는 중 문제가 발생했습니다. 관리자에게 문의하세요.");
        }
        return "redirect:/manager/stores"; // 매장 목록 페이지로 리다이렉트
    }

    // 매장을 삭제하는 메서드
    @Transactional
    @PostMapping("/delete-store/{id}")
    public String deleteStore(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            managerService.deleteStore(id); // 매장을 삭제
            redirectAttributes.addFlashAttribute("message", "매장이 성공적으로 삭제되었습니다."); // 성공 메시지 추가
        } catch (Exception e) {
            // 예외 발생 시 로그 기록
            logger.error("Error deleting store with id: " + id, e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "매장을 삭제하는 중 문제가 발생했습니다. 관리자에게 문의하세요."); // 에러 메시지 추가
        }

        return "redirect:/manager/stores"; // 매장 목록 페이지로 리다이렉트
    }
}