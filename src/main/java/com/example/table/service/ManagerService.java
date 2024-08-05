package com.example.table.service;

import com.example.table.dto.StoreRequestDto;
import com.example.table.entity.Store;
import com.example.table.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public void saveStore(StoreRequestDto storeRequestDto) {
        Store store = new Store();
        store.setName(storeRequestDto.getName());
        store.setAddress(storeRequestDto.getAddress());
        store.setDescription(storeRequestDto.getDescription());
        storeRepository.save(store);
    }

    public Store getStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Store not found"));
    }

    public void updateStore(Long id, StoreRequestDto storeRequestDto) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Store not found"));
        store.setName(storeRequestDto.getName());
        store.setAddress(storeRequestDto.getAddress());
        store.setDescription(storeRequestDto.getDescription());
        storeRepository.save(store);
    }

    public void deleteStore(Long id) {
        // 매장 존재 여부 확인
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 매장이 존재하지 않습니다. ID: " + id));

        // 매장과 연결된 데이터 처리 (예: 예약 삭제)
        // 예약 삭제나 관련 데이터 해제 로직을 추가합니다.

        storeRepository.delete(store);
    }
}