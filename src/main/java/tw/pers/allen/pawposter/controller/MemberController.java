package tw.pers.allen.pawposter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.model.dto.PaginatedDto;
import tw.pers.allen.pawposter.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberController implements BaseRestController<MemberDto> {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	/* === Read === */

	@Override
	@GetMapping("/{id}")
	public MemberDto getById(@PathVariable Integer id) {
		return memberService.findById(id);
	}

	@Override
	@GetMapping("/")
	public List<MemberDto> getAll() {
		return memberService.findAll();
	}

	@Override
	@GetMapping("/page")
	public Page<MemberDto> getPaginatedByConditions(@RequestParam(defaultValue = "0") Integer page, // 當前頁面
			@RequestParam(defaultValue = "20") Integer size, // 每頁顯示筆數
			@RequestParam(defaultValue = "ASC") String direction, // 升冪 asc 或降冪 desc
			@RequestParam(defaultValue = "memberId") String sort // 排序欄位
	) {
		PaginatedDto paginatedDto = new PaginatedDto(page, size, direction, sort);

		return memberService.findByPaginated(paginatedDto);
	}

	/* === Create === */

	@Override
	@PostMapping("/")
	public MemberDto create(@Valid MemberDto object) {
		// TODO Auto-generated method stub
		return null;
	}

	/* === Update === */

	@Override
	@PutMapping("/{id}")
	public MemberDto update(@PathVariable Integer id, @Valid MemberDto object) {
		// TODO Auto-generated method stub
		return null;
	}

	/* === Delete === */

	@Override
	@DeleteMapping("/{id}")
	public MemberDto delete(@PathVariable Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* === Other === */
}
