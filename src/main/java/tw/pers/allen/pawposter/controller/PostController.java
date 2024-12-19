package tw.pers.allen.pawposter.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tw.pers.allen.pawposter.model.dto.PaginatedDto;
import tw.pers.allen.pawposter.model.dto.PostDto;
import tw.pers.allen.pawposter.service.MemberService;

@RestController
@RequestMapping("/api/posts")
public class PostController implements BaseRestController<PostDto> {

	private final MemberService memberService;

	public PostController(MemberService memberService) {
		this.memberService = memberService;
	}

	/* === Read === */

	@Override
	@GetMapping("/{id}")
	public PostDto getById(@PathVariable Integer id) {
		return null;
	}

	@Override
	@GetMapping()
	public List<PostDto> getAll() {
		return null;
	}

	@Override
	@GetMapping("/page")
	public Page<PostDto> getPaginatedByConditions(@RequestParam(defaultValue = "0") Integer page, // 當前頁面
			@RequestParam(defaultValue = "20") Integer size, // 每頁顯示筆數
			@RequestParam(defaultValue = "ASC") String direction, // 升冪 asc 或降冪 desc
			@RequestParam(defaultValue = "memberId") String sort // 排序欄位
	) {
		PaginatedDto paginatedDto = new PaginatedDto(page, size, direction, sort);

		return null;
	}

	/* === Create === */

	@Override
	@PostMapping()
	public PostDto create(@Valid @RequestBody PostDto memberDto) {
		return null;
	}

	/* === Update === */

	@Override
	@PutMapping("/{memberId}")
	public PostDto update(@PathVariable Integer memberId, @Valid @RequestBody PostDto memberDto) {
		return null;
	}

	/* === Delete === */

	@Override
	@DeleteMapping("/{memberId}")
	public PostDto delete(@PathVariable Integer memberId) {
		return null;
	}

	/* === Other === */
}
