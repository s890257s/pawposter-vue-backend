package tw.pers.allen.pawposter.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import jakarta.transaction.Transactional;
import tw.pers.allen.pawposter.model.dto.PaginatedDto;
import tw.pers.allen.pawposter.model.dto.PostDto;
import tw.pers.allen.pawposter.model.dto.PostResourceDto;
import tw.pers.allen.pawposter.model.dto.ReplyDto;
import tw.pers.allen.pawposter.tools.CommonTool;

@SpringBootTest
@Transactional
class PostServiceTest {

	Logger log = LoggerFactory.getLogger(PostServiceTest.class);

	@Autowired
	PostService postService;

	@Test
	void testFindById() {

		// === 測試正常查找 id 與其關聯設定 ===
		PostDto post = postService.findById(1);

		// 檢查貼文者與其內容
		assertEquals("Alice", post.getMemberName(), "post 會員不符");
		assertEquals("今天帶狗狗去公園玩，牠特別喜歡追球球！", post.getPostText(), "post 內容不符");

		// 檢查附加檔案
		List<Integer> expectedResourceIds = List.of(1, 2);
		List<Integer> actualResourceIds = post.getResources().stream().map(PostResourceDto::getPostResourceId).toList();

		assertEquals(expectedResourceIds, actualResourceIds, "postResource id 不符");

		// 檢查標籤
		List<String> expectedTagIds = List.of("狗狗", "公園", "玩球");
		List<String> actualTagIds = post.getTagNames();
		assertEquals(expectedTagIds, actualTagIds, "tags 不符");

		// 檢查回覆
		List<Integer> expectedReplyIds = List.of(1, 2);
		List<Integer> actualReplyIds = post.getReplies().stream().map(ReplyDto::getReplyId).toList();
		assertEquals(expectedReplyIds, actualReplyIds, "replies id 不符");

		// === 測試查找刪除的貼文 ===
		PostDto deletedPost = postService.findById(6);
		assertEquals("Zoe", deletedPost.getMemberName(), "post 會員不符");
		assertEquals("此貼文已被刪除", deletedPost.getPostText(), "post 內容不符");

		// 檢查附加檔案
		assertEquals(0, deletedPost.getResources().size(), "resources 數量不符");

		// 檢查標籤
		assertEquals(0, deletedPost.getTagNames().size(), "tag names 數量不符");

		// 檢查回覆
		assertEquals(0, deletedPost.getReplies().size(), "replies 數量不符");

		log.info("PostService.findById 功能正常");
	}

	@Test
	void testFindAll() {

		// 測試查詢全部
		List<PostDto> posts = postService.findAll();

		assertTrue(posts.size() > 0);

		// 檢查刪除
		List<PostDto> deletedPosts = posts.stream().filter(PostDto::getIsDeleted).toList();

		deletedPosts.forEach(post -> {
			assertEquals("此貼文已被刪除", post.getPostText(), "post 內容不符");

			// 檢查附加檔案
			assertEquals(0, post.getResources().size(), "resources 數量不符");

			// 檢查標籤
			assertEquals(0, post.getTagNames().size(), "tag names 數量不符");

			// 檢查回覆
			assertEquals(0, post.getReplies().size(), "replies 數量不符");
		});

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

		// 檢查刪除
		List<PostDto> deletedPosts = posts.stream().filter(PostDto::getIsDeleted).toList();

		deletedPosts.forEach(post -> {
			assertEquals("此貼文已被刪除", post.getPostText(), "post 內容不符");

			// 檢查附加檔案
			assertEquals(0, post.getResources().size(), "resources 數量不符");

			// 檢查標籤
			assertEquals(0, post.getTagNames().size(), "tag names 數量不符");

			// 檢查回覆
			assertEquals(0, post.getReplies().size(), "replies 數量不符");
		});
		
		
		log.info("PostService.findByPaginated 功能正常");
	}

	@Test
	void testInsertPost() {

		// === 貼文 + 會員 + 標籤 + 貼文檔案 ===
		// 建立貼文
		PostDto postDto = new PostDto();
		postDto.setPostText("hello world!");

		// 設定貼文者
		postDto.setMemberId(1);
		postDto.setMemberName("Alice");

		try {
			// 設定貼文圖片
			byte[] photo = FileCopyUtils.copyToByteArray(ResourceUtils.getFile("classpath:init\\image\\post-1-1.jpg"));
			PostResourceDto postResourceDto = new PostResourceDto();
			postResourceDto.setResourceContent(photo);
			postResourceDto.setMimeType(CommonTool.guessMimeType(photo));
			postDto.setResources(List.of(postResourceDto));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 設定貼文標籤
		List<String> tagNames = List.of("狗狗", "可愛", "你好世界", "HelloWorld");
		postDto.setTagNames(tagNames);

		PostDto insertedPost = postService.insertPost(postDto);

		assertNotNull(insertedPost.getMemberId(), "新增後的 member id 為空");
		assertNotNull(insertedPost.getMemberName(), "新增後的 member name 為空");
		assertNotNull(insertedPost.getPostId(), "新增後的 post id 為空");
		assertNotNull(insertedPost.getPostText(), "新增後的 post text 為空");
		assertEquals(insertedPost.getTagNames(), tagNames, "新增後的 tag name 不符");
		assertEquals(1, insertedPost.getResources().size(), "新增後的 resource size 錯誤");

		// === 測試無會員錯誤 ===
		PostDto postWithoutMember = new PostDto();
		postWithoutMember.setPostText("hello world!");

		assertThatThrownBy(() -> postService.insertPost(postWithoutMember)) // 執行 insertPost
				.isInstanceOf(RuntimeException.class) // 預期拋出 RuntimeException 錯誤
				.hasMessageContaining("無法新增 post"); // 預期包含錯誤訊息"無法新增 post，因..."

		// === 測試無附件 && 無標籤 ===
		PostDto postWithoutResourcesAndTags = new PostDto();
		postWithoutResourcesAndTags.setPostText("hello world!");
		postWithoutResourcesAndTags.setMemberId(1);

		PostDto insertedPostWithResourcesAndTags = postService.insertPost(postWithoutResourcesAndTags);
		assertNotNull(insertedPostWithResourcesAndTags.getMemberId(), "新增後的 member id 為空");
		assertNotNull(insertedPostWithResourcesAndTags.getMemberName(), "新增後的 member name 為空");
		assertNotNull(insertedPostWithResourcesAndTags.getPostId(), "新增後的 post id 為空");
		assertNotNull(insertedPostWithResourcesAndTags.getPostText(), "新增後的 post text 為空");
		assertEquals(0, insertedPostWithResourcesAndTags.getTagNames().size(), "不應該有 tag names");
		assertEquals(0, insertedPostWithResourcesAndTags.getResources().size(), "不應該有 resource");

		log.info("PostService.insertPost 功能正常");
	}

	@Test
	@AfterEach
	void testUpdatePost() {
		// === 測試全更新 ===
		// 建立更新物件
		PostDto postDto = new PostDto();
		postDto.setPostText("更新貼文");

		// 設定新圖片
		try {

			byte[] p1 = FileCopyUtils
					.copyToByteArray(ResourceUtils.getFile("classpath:init\\image\\frontend_no_image.png"));
			PostResourceDto postResourceDto1 = new PostResourceDto();
			postResourceDto1.setResourceContent(p1);
			postResourceDto1.setMimeType(CommonTool.guessMimeType(p1));

			byte[] p2 = FileCopyUtils
					.copyToByteArray(ResourceUtils.getFile("classpath:init\\image\\frontend_sing_up_image.png"));
			PostResourceDto postResourceDto2 = new PostResourceDto();
			postResourceDto2.setResourceContent(p2);
			postResourceDto2.setMimeType(CommonTool.guessMimeType(p2));

			postDto.setResources(List.of(postResourceDto1, postResourceDto2));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 設定新標籤
		List<String> tagNames = List.of("兔兔", "貪吃鬼", "更新", "UpdatePost");
		postDto.setTagNames(tagNames);

		PostDto updatedPost = postService.updatePost(1, postDto);
		assertEquals(1, updatedPost.getMemberId(), "更新後的 member id 不該改變");
		assertEquals("Alice", updatedPost.getMemberName(), "更新後的 member name 不該改變");
		assertEquals(1, updatedPost.getPostId(), "更新後的 post id 不該改變");
		assertEquals("更新貼文", updatedPost.getPostText(), "更新貼文失敗");
		assertEquals(updatedPost.getTagNames(), tagNames, "更新後的 tag name 不符");
		assertEquals(2, updatedPost.getResources().size(), "更新後的 resource size 錯誤");

		log.info("PostService.updatePost 功能正常");
	}

	@Test
	void testDeletePost() {

		PostDto deletePost = postService.deletePost(1);

		assertTrue(deletePost.getIsDeleted());

		log.info("PostService.deletePost 功能正常");
	}
}
