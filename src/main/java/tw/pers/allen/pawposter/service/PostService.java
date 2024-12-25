package tw.pers.allen.pawposter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.pers.allen.pawposter.model.dto.PaginatedDto;
import tw.pers.allen.pawposter.model.dto.PostViewDto;
import tw.pers.allen.pawposter.model.dto.ReplyDto;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.model.entity.Post;
import tw.pers.allen.pawposter.model.entity.PostResource;
import tw.pers.allen.pawposter.model.entity.PostTag;
import tw.pers.allen.pawposter.model.entity.Tag;
import tw.pers.allen.pawposter.repository.MemberRepository;
import tw.pers.allen.pawposter.repository.PostRepository;
import tw.pers.allen.pawposter.repository.PostResourceRepository;
import tw.pers.allen.pawposter.repository.PostTagRepository;
import tw.pers.allen.pawposter.repository.TagRepository;
import tw.pers.allen.pawposter.tools.EntityMapperTool.PostMapper;

@Service
public class PostService {

	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final PostTagRepository postTagRepository;
	private final PostResourceRepository postResourceRepository;
	private final TagRepository tagRepository;

	public PostService(PostRepository postRepository, PostTagRepository postTagRepository, TagRepository tagRepository,
			MemberRepository memberRepository, PostResourceRepository postResourceRepository) {
		this.memberRepository = memberRepository;
		this.postRepository = postRepository;
		this.postTagRepository = postTagRepository;
		this.postResourceRepository = postResourceRepository;
		this.tagRepository = tagRepository;
	}

	/* === private method === */
	private Post findOrFail(Integer postId) {
		return postRepository.findById(postId)
				.orElseThrow(() -> new RuntimeException("找不到貼文。id: %s".formatted(postId)));
	}

	private void setContentEmpty(PostViewDto postDto) {
		postDto.setPostText("此貼文已被刪除");
		postDto.setTagNames(Collections.emptyList());
		postDto.setReplies(Collections.emptyList());
		postDto.setResources(Collections.emptyList());
	}

	private void setContentEmpty(ReplyDto replyDto) {
		replyDto.setReplyText("此回覆已被刪除");
	}

	private PostViewDto hideDeletedContent(PostViewDto postDto) {
		if (postDto.getIsDeleted()) {
			setContentEmpty(postDto);
		}

		postDto.getReplies().forEach(replyDto -> {
			if (replyDto.getIsDeleted()) {
				setContentEmpty(replyDto);
			}
		});

		return postDto;
	}

	private List<PostViewDto> hideDeletedContent(List<PostViewDto> postDtos) {
		postDtos.forEach(postDto -> {
			if (postDto.getIsDeleted()) {
				setContentEmpty(postDto);
			}

			postDto.getReplies().forEach(replyDto -> {
				if (replyDto.getIsDeleted()) {
					setContentEmpty(replyDto);
				}
			});

		});

		return postDtos;
	}

	/**
	 * 將標籤名稱轉換成對應的 Tag Entity
	 */
	private List<Tag> getTags(List<String> tagNames) {
		if (tagNames == null) {
			return Collections.emptyList();
		}

		// 去除重複並清理字串
		List<String> distinctTagNames = tagNames.stream().filter(Objects::nonNull).map(String::trim)
				.filter(name -> !name.isEmpty()).distinct().toList();

		if (distinctTagNames.isEmpty()) {
			return Collections.emptyList();
		}

		// 查詢已存在的 Tag
		List<Tag> existingTags = tagRepository.findByTagNameIn(distinctTagNames);
		Set<String> existingTagNames = existingTags.stream().map(Tag::getTagName).collect(Collectors.toSet());

		// 過濾出需要新增的 Tag name
		List<String> newTagNames = distinctTagNames.stream().filter(name -> !existingTagNames.contains(name)).toList();

		// 將新 Tag 保存進資料庫
		List<Tag> newTags = newTagNames.isEmpty() ? Collections.emptyList()
				: tagRepository.saveAll(newTagNames.stream().map(Tag::new).toList());

		// 合併已存在與新建 Tag
		List<Tag> finalTags = new ArrayList<>(existingTags);
		finalTags.addAll(newTags);

		return finalTags;
	}

	/* === Read === */

	/**
	 * 跟據 id 查找 post。
	 */
	public PostViewDto getById(Integer postId) {
		Post post = findOrFail(postId);

		return hideDeletedContent(PostMapper.toDto(post));
	}

	/**
	 * 查找所有 posts。
	 */
	public List<PostViewDto> getAll() {
		List<Post> posts = postRepository.findAll();
		List<PostViewDto> postDtos = posts.stream().map(PostMapper::toDto).toList();

		return hideDeletedContent(postDtos);
	}

	/**
	 * 根據分頁資訊查找 posts。
	 */
	public Page<PostViewDto> getByPaginated(PaginatedDto dto) {

		/**
		 * 建立分頁物件，依參數順序: </br>
		 * 參數一: page，表示當前頁數，起始值為 0 </br>
		 * 參數二: size，表示每頁顯示筆數 </br>
		 * 參數三: direction，表示排序順序由小到大(升冪 ASC)，或大到小(降冪 DESC) </br>
		 * 參數四: sort，排序目標欄位
		 */
		PageRequest pageRequest = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirectionEnum(), dto.getSort());

		Page<Post> pagePosts = postRepository.findAll(pageRequest);

		Page<PostViewDto> pagePostDtos = pagePosts.map(post -> hideDeletedContent(PostMapper.toDto(post)));

		return pagePostDtos;
	}

	/* === Create === */
	@Transactional
	public PostViewDto createPost(Integer memberId,PostViewDto postDto) {
		// STEP 1: 資料檢查
		// member 不得為空或不存在
		if (postDto.getMemberId() == null) {
			throw new RuntimeException("無法新增 post，因傳入的 member id 為空");
		}

		memberRepository.findById(postDto.getMemberId()).orElseThrow(
				() -> new RuntimeException("無法新增 post，因找不到對應的 member。member id: %s".formatted(postDto.getMemberId())));

		// STEP 2: 新增
		// 建立 Post 實體
		Post post = new Post();
		post.setPostText(postDto.getPostText());

		Member member = new Member();
		member.setMemberId(postDto.getMemberId());
		post.setMember(member);

		// 設定 Post 附帶檔案
		List<PostResource> postResources = postDto.getResources().stream() // 流化
				.map(PostMapper::toEntity) // 轉換成實體
				.peek(r -> r.setPost(post)).toList(); // 設定關聯
		post.setPostResources(postResources);

		// 標籤處理
		List<Tag> tags = getTags(postDto.getTagNames());

		// 建立 Post 與 Tag 關聯
		List<PostTag> postTags = tags.stream().map(tag -> {
			PostTag postTag = new PostTag();
			postTag.setTag(tag);
			postTag.setPost(post);
			return postTag;
		}).toList();
		post.setPostTags(postTags);

		// 保存 Post
		Post savedPost = postRepository.save(post);

		Member m = memberRepository.findById(savedPost.getMember().getMemberId()).get();
		savedPost.setMember(m);

		return PostMapper.toDto(savedPost);
	}

	/* === Update === */
	@Transactional
	public PostViewDto updatePost(Integer postId, PostViewDto postDto) {
		Post existingPost = findOrFail(postId);

		// 更新文字
		existingPost.setPostText(postDto.getPostText());

		// 更新附加檔案
		postResourceRepository.deleteByPost(existingPost); // 先刪除舊關聯

		List<PostResource> newResources = postDto.getResources().stream() // 流化
				.map(PostMapper::toEntity) // 轉換成實體
				.peek(r -> r.setPost(existingPost)).collect(Collectors.toList()); // 設定關聯
		existingPost.setPostResources(newResources);

		// 更新標籤
		postTagRepository.deleteByPost(existingPost); // 先刪除舊關聯

		List<Tag> tags = getTags(postDto.getTagNames());
		List<PostTag> postTags = tags.stream().map(tag -> {
			PostTag postTag = new PostTag();
			postTag.setTag(tag);
			postTag.setPost(existingPost);
			return postTag;
		}).collect(Collectors.toList());
		existingPost.setPostTags(postTags);

		Post updatedPost = postRepository.save(existingPost);

		Post post = postRepository.findById(updatedPost.getPostId()).get();

		return PostMapper.toDto(post);
	}

	/* === Delete === */
	@Transactional
	public PostViewDto deletePost(Integer postId) {

		Post post = findOrFail(postId);

		// 軟刪除
		post.setIsDeleted(true);

		postRepository.save(post);

		return PostMapper.toDto(post);
	}

	/* === Other === */
}
