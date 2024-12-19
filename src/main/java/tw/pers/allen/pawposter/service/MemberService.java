package tw.pers.allen.pawposter.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.model.dto.PaginatedDto;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.repository.MemberRepository;
import tw.pers.allen.pawposter.tools.CommonTool;

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

	/**
	 * 跟據 id 查找 member。
	 */
	public MemberDto findById(Integer memberId) {
		Member member = getById(memberId);

		return new MemberDto(member);
	}

	/**
	 * 查找所有 members。
	 */
	public List<MemberDto> findAll() {
		List<Member> members = memberRepository.findAll();
		List<MemberDto> memberDtos = members.stream().map(MemberDto::new).toList();

		return memberDtos;
	}

	/**
	 * 根據分頁資訊查找 members。
	 */
	public Page<MemberDto> findByPaginated(PaginatedDto dto) {

		/**
		 * 建立分頁物件，依參數順序: </br>
		 * 參數一: page，表示當前頁數，起始值為 0 </br>
		 * 參數二: size，表示每頁顯示筆數 </br>
		 * 參數三: direction，表示排序順序由小到大(升冪 ASC)，或大到小(降冪 DESC) </br>
		 * 參數四: sort，排序目標欄位
		 */
		PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirectionEnum(), dto.getSort());

		Page<Member> pageMembers = memberRepository.findAll(pageRequest);

		Page<MemberDto> pageMemberDtos = pageMembers.map(MemberDto::new);

		return pageMemberDtos;
	}

	/* === Create === */
	public MemberDto insertMember(MemberDto memberDto) {
		Member member = memberDto.toMember();

		Member savedMember = memberRepository.save(member);

		return new MemberDto(savedMember);
	}

	/* === Update === */
	public MemberDto updateMember(Integer memberId, MemberDto memberDto) {
		// 不得修改密碼
		memberDto.setMemberPassword(null);

		// 掃瞄出 null 屬性，整理成陣列
		String[] nullPropertyNames = CommonTool.getNullPropertyNames(memberDto);

		Member member = getById(memberId);

		BeanUtils.copyProperties(memberDto, member, nullPropertyNames);
		BeanUtils.copyProperties(memberDto, member.getMemberDetail(), nullPropertyNames);

		Member savedMember = memberRepository.save(member);

		return new MemberDto(savedMember);
	}

	/* === Delete === */
	public MemberDto deleteMember(Integer memberId) {

		Member member = getById(memberId);

		memberRepository.delete(member);

		return new MemberDto(member);
	}

	/* === Other === */
}
