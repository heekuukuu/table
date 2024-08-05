package com.example.table.service;

import com.example.table.dto.StoreRequestDto;
import com.example.table.entity.Store;
import com.example.table.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    // 모든 매장을 조회하는 메서드
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    // 상점을 이름순으로 정렬하여 조회하는 메서드
    public List<Store> getStoresOrderedByName() {
        return storeRepository.findAllByOrderByNameAsc();
    }

    // 상점을 별점순으로 정렬하여 조회하는 메서드
    public List<Store> getStoresOrderedByRating() {
        return storeRepository.findAllByOrderByRatingDesc();
    }

    // 특정 위치 기반으로 상점을 조회하는 메서드
    public List<Store> getStoresByLocation(String location) {
        return storeRepository.findByLocationContaining(location);
    }

    // 현재 위치를 기준으로 거리 순으로 상점을 조회하는 메서드
    public List<Store> getStoresOrderedByDistance(double currentLatitude, double currentLongitude) {
        List<Store> stores = storeRepository.findAll();
        stores.sort(Comparator.comparingDouble(store -> calculateDistance(currentLatitude, currentLongitude, store.getLatitude(), store.getLongitude())));
        return stores;
    }

    // 두 지점 간의 거리를 계산하는 메서드
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 평균 반지름 (킬로미터 단위)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // 상점 ID로 상점을 조회하는 메서드
    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));
    }

    // 상점 정보를 업데이트하는 메서드
    @Transactional
    public void updateStore(Long id, Store updatedStore) {
        Optional<Store> optionalStore = storeRepository.findById(id);

        if (optionalStore.isPresent()) {
            Store existingStore = optionalStore.get();

            // 업데이트할 정보로 기존 매장 정보 갱신
            existingStore.setName(updatedStore.getName());
            existingStore.setAddress(updatedStore.getAddress());
            existingStore.setLocation(updatedStore.getLocation());
            existingStore.setRating(updatedStore.getRating());
            existingStore.setDescription(updatedStore.getDescription());
            // 필요한 경우 다른 필드도 업데이트

            // 변경된 정보를 저장
            storeRepository.save(existingStore);
        } else {
            throw new RuntimeException("Store not found");
        }
    }
}