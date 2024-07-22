package com.example.table.service;


import com.example.table.dto.MemberDTO;
import com.example.table.entity.MemberEntity;
import com.example.table.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
         //Repository 의 save 메서드호출  (조건. entity객체를 넘겨줘야함 )
       MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);

         // 1.dto -> entity 변환
        //  2.repository save 메서드의 호출


    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
        1. 회원이 입력한 이메일로 DB에서 조회를함
        2. DB에서 조회한 비밀번호외 입력한 비밀번호가 일치하는지판단
         */
        Optional<MemberEntity> byMemberEmail =
        memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
    if (byMemberEmail.isPresent()) {
        // 조회결과가 있다 (해당이메일을 가진회원정보가 있다)
        MemberEntity memberEntity = byMemberEmail.get();
        if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
              //비밀번호 일치
             //entity -> dto
            MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
            return dto;
        }else {
            //비밀번호 불일치(로그인 실패)
            return null;
        }
        }else {
       // 조회한 결과가 없다 (해당 이메일을 가진 회원이 없다)
        return null;
    }


    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
            //MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            //memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
           // MemberEntity memberEntity = optionalMemberEntity.get();
           // MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
           // return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else {
            return null;

        }


    }

    public MemberDTO updateForm(String myEmail) {
      Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {  //정보 수정
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));


    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);

    }

    public String emailCheck(String memberEmail) {
      Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
     if (byMemberEmail.isPresent()) {
         //조회결과있음 -> 사용 X
         return null;
     }else{
        // 조회결과 없음 -> 사용 o
         return "ok";
     }

    }
}