package tw.pers.allen.pawposter.service;

import org.springframework.stereotype.Service;

import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.repository.MemberRepository;

@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	/* === private method === */
	private Member getById(Integer memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new RuntimeException("找不到會員。id: %s".formatted(memberId)));
	}

	/* === Read === */
	public MemberDto findById(Integer memberId) {
		Member member = getById(memberId);

		return new MemberDto(member);
	}

	/* === Create === */

	/* === Update === */

	/* === Delete === */
	
	/* === Other === */
}
