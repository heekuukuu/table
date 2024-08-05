package com.example.table.dto;

import com.example.table.entity.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequestDto {
    private String name; // 가게이름
    private int rating; // 가게별점
    private String location; //가게정보
    private String address; //가게주소
    private String number; //가게번호
    private String description; //가게거리

    private double latitude;  // 위도
    private double longitude; // 경도


    // StoreRequestDto를 Store 엔티티로 변환하는 메서드
    public Store toEntity() {
        Store store = new Store();
        store.setName(this.name);
        store.setAddress(this.address);
        store.setRating(this.rating);
        store.setLocation(this.location);
        store.setDescription(this.description);
        store.setNumber(this.number);
        store.setLatitude(this.latitude);
        store.setLongitude(this.longitude);
        return store;
    }

}
