package com.example.table.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "reservation")
@NoArgsConstructor
public class Reservation {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private LocalDate date;

        @Column(nullable = false)
        private String time;

        @Column(nullable = false)
        private String phone;



        // Reservation은 하나의 Store에 속합니다.
        // 이는 다대일 관계의 '다(Many)' 쪽에 해당합니다
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "store_id", nullable = false)
        private Store store;


        // 예약은 여러 개의 리뷰를 가질 수 있습니다.
        @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Review> reviews;

}


