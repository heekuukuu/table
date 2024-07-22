package com.example.table.dto;

import com.example.table.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// 회원정보


public class MemberDTO {
            private Long id;
            private String memberEmail;
            private String memberPassword;
            private String memberName;
            private String memberNumber;

            public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
                MemberDTO memberDTO = new MemberDTO();
                memberDTO.setId(memberEntity.getId());
                memberDTO.setMemberEmail(memberEntity.getMemberEmail());
                memberDTO.setMemberPassword(memberEntity.getMemberPassword());
                memberDTO.setMemberName(memberEntity.getMemberName());
                memberDTO.setMemberNumber(memberEntity.getMemberNumber());
                return memberDTO;


            }
}