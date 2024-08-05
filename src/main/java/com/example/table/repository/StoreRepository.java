package com.example.table.repository;

import com.example.table.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 이름 오름차순으로 정렬된 매장 목록 조회
    List<Store> findAllByOrderByNameAsc();

    // 별점 내림차순으로 정렬된 매장 목록 조회
    List<Store> findAllByOrderByRatingDesc();

    // 거리순으로 정렬된 매장 목록 조회 (거리순 정렬은 가정된 메소드로 구현이 필요함)

    List<Store> findByLocationContaining(String location);
}

