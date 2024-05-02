package com.sds.movieapp.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.movieapp.domain.Member;
import com.sds.movieapp.domain.MemberDetail;
import com.sds.movieapp.domain.Role;
import com.sds.movieapp.domain.Sns;
import com.sds.movieapp.exception.MemberException;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private SnsDAO snsDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private MemberDetailDAO memberDetailDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public void regist(Member member) throws MemberException{
		//홈페이지 회원의 sns 정보 가져오기
		Sns sns = snsDAO.selectByName(member.getSns().getSns_name());
		member.setSns(sns);//sns_idx가 채워진 DTO를 다시 Member DTO  에 대입
		
		//회원의 권한 가져오기
		Role role = roleDAO.selectByName(member.getRole().getRole_name());
		member.setRole(role); //role_idx가 채워진 DTO를 다시 Member DTO  에 대입
		
		int result = memberDAO.insert(member);
		
		if(result <1) {
			throw new MemberException("회원 등록 실패");
		}
		
		//회원 상세 정보 등록 
		MemberDetail memberDetail = member.getMemberDetail();
		memberDetail.setMember(member);
		memberDetail.setPassword(bCryptPasswordEncoder.encode(memberDetail.getPassword())); 
		
		//비밀번호 암호화 처리 
		
		
		result = memberDetailDAO.insert(memberDetail);//회원 상세 정보 등록
		if(result <1) {
			throw new MemberException("회원 추가정보 등록 실패");
		}
		
	}
	
}





