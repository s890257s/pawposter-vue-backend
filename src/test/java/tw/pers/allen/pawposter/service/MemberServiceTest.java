package tw.pers.allen.pawposter.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import jakarta.transaction.Transactional;
import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.model.dto.PaginatedDto;

@SpringBootTest
@Transactional
class MemberServiceTest {

	Logger log = LoggerFactory.getLogger(MemberServiceTest.class);

	@Autowired
	MemberService memberService;

	@Test
	void testFindById() {

		// 測試正常查找 id
		MemberDto member = memberService.findById(1);
		assertEquals("Alice", member.getMemberName());

		// 測試異常查找 id
		assertThatThrownBy(() -> memberService.findById(-1)) // 執行 findById(-1)
				.isInstanceOf(RuntimeException.class) // 預期拋出 RuntimeException 錯誤
				.hasMessageContaining("找不到會員"); // 預期包含錯誤訊息"找不到會員"

		log.info("MemberService.findById 功能正常");
	}

	@Test
	void testFindAll() {

		// 測試查詢全部
		List<MemberDto> members = memberService.findAll();

		assertTrue(members.size() > 0);

		log.info("MemberService.findAll 功能正常");

	}

	@Test
	void testfindByPaginated() {

		PaginatedDto paginatedDto;
		Page<MemberDto> members;

		// 測試查詢
		paginatedDto = new PaginatedDto(1, 20, "ASC", "memberId");
		members = memberService.findByPaginated(paginatedDto);
		long total = members.getTotalElements();
		assertTrue(() -> total > 0);

		// 測試分頁功能
		paginatedDto = new PaginatedDto(1, 2, "ASC", "memberId");
		members = memberService.findByPaginated(paginatedDto);
		assertEquals("Carol", members.getContent().get(0).getMemberName());

		log.info("MemberService.findByPaginated 功能正常");
	}

	@Test
	void testInsertMember() {

		// 測試新增
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberName("Jim");
		memberDto.setMemberGender("male");

		MemberDto savedMember = memberService.insertMember(memberDto);
		MemberDto foundMember = memberService.findById(savedMember.getMemberId());

		assertEquals(memberDto.getMemberName(), foundMember.getMemberName());
		assertEquals(memberDto.getMemberGender(), foundMember.getMemberGender());

		log.info("MemberService.insertMember 功能正常");
	}

	@Test
	void testUpdateMember() {

		// 測試更新
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberName("Jim");

		MemberDto updatedMember = memberService.updateMember(1, memberDto);
		assertEquals(memberDto.getMemberName(), updatedMember.getMemberName());
		assertEquals("alice@mail.com", updatedMember.getMemberMail());

		log.info("MemberService.updateMember 功能正常");
	}

	@Test
	void testDeleteMember() {

		// 測試刪除
		int initialMemberCount = memberService.findAll().size();

		MemberDto expectedDeletedMember = memberService.deleteMember(1);
		assertEquals("Alice", expectedDeletedMember.getMemberName());

		int finalMemberCount = memberService.findAll().size();

		assertEquals(initialMemberCount - 1, finalMemberCount);

		log.info("MemberService.deleteMember 功能正常");
	}
}
