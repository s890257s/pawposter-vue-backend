package tw.pers.allen.pawposter.service;

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
import tw.pers.allen.pawposter.model.dto.PaginatedDto;
import tw.pers.allen.pawposter.model.dto.PostDto;
import tw.pers.allen.pawposter.model.dto.PostResourceDto;
import tw.pers.allen.pawposter.model.dto.ReplyDto;
import tw.pers.allen.pawposter.model.dto.TagDto;

@SpringBootTest
@Transactional
class PostServiceTest {

	Logger log = LoggerFactory.getLogger(PostServiceTest.class);

	@Autowired
	PostService postService;

	@Test
	void testFindById() {

		// 測試正常查找 id 與其關聯設定
		PostDto post = postService.findById(1);

		// 檢查貼文者與其內容
		assertEquals("Alice", post.getMemberName(), "post 會員不符");
		assertEquals("今天帶狗狗去公園玩，牠特別喜歡追球球！", post.getPostText(), "post 內容不符");

		// 檢查附加檔案
		List<Integer> expectedResourceIds = List.of(1, 2);
		List<Integer> actualResourceIds = post.getResources().stream().map(PostResourceDto::getPostResourceId).toList();

		assertEquals(expectedResourceIds, actualResourceIds, "postResource id 不符");

		// 檢查標籤
		List<Integer> expectedTagIds = List.of(1, 2, 3);
		List<Integer> actualTagIds = post.getTags().stream().map(TagDto::getTagId).toList();
		assertEquals(expectedTagIds, actualTagIds, "Tag IDs do not match");

		// 檢查回覆
		List<Integer> expectedReplyIds = List.of(1, 2);
		List<Integer> actualReplyIds = post.getReplies().stream().map(ReplyDto::getReplyId).toList();
		assertEquals(expectedReplyIds, actualReplyIds, "Reply IDs do not match");

		log.info("PostService.findById 功能正常");
	}

	@Test
	void testFindAll() {

		// 測試查詢全部
		List<PostDto> posts = postService.findAll();

		assertTrue(posts.size() > 0);

		log.info("PostService.findAll 功能正常");
	}

	@Test
	void testfindByPaginated() {

		PaginatedDto paginatedDto;
		Page<PostDto> posts;

		// 測試查詢
		paginatedDto = new PaginatedDto(1, 20, "ASC", "postId");
		posts = postService.findByPaginated(paginatedDto);
		long total = posts.getTotalElements();
		assertTrue(() -> total > 0);

		// 測試分頁功能
		paginatedDto = new PaginatedDto(1, 2, "ASC", "postId");
		posts = postService.findByPaginated(paginatedDto);
		assertEquals("最近發現我的貓咪喜歡趴在鍵盤上，這樣我要怎麼工作啊？", posts.getContent().get(0).getPostText());

		log.info("PostService.findByPaginated 功能正常");
	}

//	@Test
//	void testInsertPost() {
//
//		// 測試新增
//		PostDto postDto = new PostDto();
//		postDto.setMemberId(1);
//		postDto.setMemberName("Alice");
//		postDto.setPostText("hello world!");
//
//		byte[] photo = FileCopyUtils.copyToByteArray(ResourceUtils.getFile("classpath:init\\image\\post-1-1.jpg"));
//		PostResourceDto postResourceDto = new PostResourceDto();
//		postResourceDto.setResourceContent(photo);
//		postResourceDto.setMimeType(CommonTool.guessMimeType(photo));
//
//		postDto.setResources(List.of(postResourceDto));
//
//		PostDto insertedPost = postService.insertPost(postDto);
//		assertEquals(postDto.getPostName(), insertedPost.getPostName());
//
//		log.info("PostService.insertPost 功能正常");
//	}
//
//	@Test
//	void testUpdatePost() {
//
//		// 測試更新
//		PostDto postDto = new PostDto();
//		postDto.setPostName("Jim");
//
//		PostDto updatedPost = postService.updatePost(1, postDto);
//		assertEquals(postDto.getPostName(), updatedPost.getPostName());
//		assertEquals("alice@mail.com", updatedPost.getPostMail());
//
//		log.info("PostService.updatePost 功能正常");
//	}
//
//	@Test
//	void testDeletePost() {
//
//		// 測試刪除
//		int initialPostCount = postService.findAll().size();
//
//		PostDto expectedDeletedPost = postService.deletePost(1);
//		assertEquals("Alice", expectedDeletedPost.getPostName());
//
//		int finalPostCount = postService.findAll().size();
//
//		assertEquals(initialPostCount - 1, finalPostCount);
//
//		log.info("PostService.deletePost 功能正常");
//	}
}
