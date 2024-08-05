package com.example.table.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID 필드를 자동 증가하도록 설정
    private Long id;
    @Column(nullable = false)
    private String name; // 가게이름
    //   private int rating; 가게별점
    private String location; //가게정보
    private String address; //가게주소
    private String number; //가게번호
    @Lob
    private String description; //가게거리
    private double rating;
    private double latitude;  // 위도
    private double longitude; // 경도


    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}