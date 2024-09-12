package tw.pers.allen.pawposter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.model.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public Member findById(Integer memberId) {

		return memberRepository.findById(memberId).get();
	}

}
