package com.example.table.controller;

import com.example.table.dto.StoreRequestDto;
import com.example.table.entity.Store;
import com.example.table.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    // 모든 매장 조회 메서드
    @GetMapping
    public String getAllStores(Model model) {
        List<Store> stores = storeService.getAllStores();
        model.addAttribute("stores", stores);
        return "store-list";
    }

    // ID로 특정 매장 조회 메서드
    @GetMapping("/{id}")
    public String getStoreById(@PathVariable Long id, Model model) {
        Store store = storeService.getStoreById(id);
        model.addAttribute("store", store);
        return "store-detail";
    }

    // 이름순으로 매장 조회 메서드
    @GetMapping("/sortedByName")
    public String getStoresOrderedByName(Model model) {
        List<Store> stores = storeService.getStoresOrderedByName();
        model.addAttribute("stores", stores);
        return "store-list";
    }

    // 별점순으로 매장 조회 메서드
    @GetMapping("/sortedByRating")
    public String getStoresOrderedByRating(Model model) {
        List<Store> stores = storeService.getStoresOrderedByRating();
        model.addAttribute("stores", stores);
        return "store-list";
    }

    // 거리순으로 매장 조회 메서드 (현재 위치를 기준으로)
    @GetMapping("/sortedByDistance")
    public String getStoresOrderedByDistance(@RequestParam double latitude, @RequestParam double longitude, Model model) {
        List<Store> stores = storeService.getStoresOrderedByDistance(latitude, longitude);
        model.addAttribute("stores", stores);
        return "store-list";
    }




}