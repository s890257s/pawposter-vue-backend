package tw.pers.allen.pawposter.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tw.pers.allen.pawposter.model.dto.PaginatedDto;
import tw.pers.allen.pawposter.model.dto.PostDto;
import tw.pers.allen.pawposter.model.entity.Post;
import tw.pers.allen.pawposter.repository.PostRepository;

@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	/* === private method === */
	private Post getById(Integer postId) {
		return postRepository.findById(postId)
				.orElseThrow(() -> new RuntimeException("找不到貼文。id: %s".formatted(postId)));
	}

	/* === Read === */

	/**
	 * 跟據 id 查找 post。
	 */
	public PostDto findById(Integer postId) {
		Post post = getById(postId);

		return new PostDto(post);
	}

	/**
	 * 查找所有 posts。
	 */
	public List<PostDto> findAll() {
		List<Post> posts = postRepository.findAll();
		List<PostDto> postDtos = posts.stream().map(PostDto::new).toList();

		return postDtos;
	}

	/**
	 * 根據分頁資訊查找 posts。
	 */
	public Page<PostDto> findByPaginated(PaginatedDto dto) {

		/**
		 * 建立分頁物件，依參數順序: </br>
		 * 參數一: page，表示當前頁數，起始值為 0 </br>
		 * 參數二: size，表示每頁顯示筆數 </br>
		 * 參數三: direction，表示排序順序由小到大(升冪 ASC)，或大到小(降冪 DESC) </br>
		 * 參數四: sort，排序目標欄位
		 */
		PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirectionEnum(), dto.getSort());

		Page<Post> pagePosts = postRepository.findAll(pageRequest);

		Page<PostDto> pagePostDtos = pagePosts.map(PostDto::new);

		return pagePostDtos;
	}

	/* === Create === */
	public PostDto insertPost(PostDto postDto) {
		Post post = postDto.toPost();

		Post savedPost = postRepository.save(post);

		return new PostDto(savedPost);
	}

	/* === Update === */
	public PostDto updatePost(Integer postId, PostDto postDto) {

		Post post = postDto.toPost();
		Post savedPost = postRepository.save(post);

		return new PostDto(savedPost);
	}

	/* === Delete === */
	public PostDto deletePost(Integer postId) {

		Post post = getById(postId);

		postRepository.delete(post);

		return new PostDto(post);
	}

	/* === Other === */
}
