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
import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.model.dto.PaginatedDto;
import tw.pers.allen.pawposter.model.dto.PostUploadDto;
import tw.pers.allen.pawposter.model.dto.PostViewDto;
import tw.pers.allen.pawposter.service.PostService;
import tw.pers.allen.pawposter.tools.LoggedInMemberTool;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	/* === Read === */

	@GetMapping("/{id}")
	public PostViewDto getById(@PathVariable Integer id) {
		return postService.getById(id);
	}

	@GetMapping
	public List<PostViewDto> getAll() {
		return postService.getAll();
	}

	@GetMapping("/page")
	public Page<PostViewDto> getPaginatedByConditions(@RequestParam(defaultValue = "0") Integer page, // 當前頁面
			@RequestParam(defaultValue = "20") Integer size, // 每頁顯示筆數
			@RequestParam(defaultValue = "ASC") String direction, // 升冪 asc 或降冪 desc
			@RequestParam(defaultValue = "postId") String sort // 排序欄位
	) {
		PaginatedDto paginatedDto = new PaginatedDto(page, size, direction, sort);

		return postService.getByPaginated(paginatedDto);
	}

	/* === Create === */

	@PostMapping
	public PostViewDto create(PostUploadDto postUploadDto) {
		return postService.createPost(LoggedInMemberTool.getLoggedInMemberId(), postUploadDto);
	}

	/* === Delete === */

	@DeleteMapping("/{postId}")
	public PostViewDto delete(@PathVariable Integer postId) {
		return postService.deletePost(LoggedInMemberTool.getLoggedInMemberId(), postId);
	}

	/* === Other === */
}
