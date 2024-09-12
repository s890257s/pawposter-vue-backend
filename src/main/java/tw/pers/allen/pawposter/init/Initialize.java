package tw.pers.allen.pawposter.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tw.pers.allen.pawposter.model.entity.Comment;
import tw.pers.allen.pawposter.model.entity.Image;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.model.entity.MemberDetail;
import tw.pers.allen.pawposter.model.entity.MemberStatus;
import tw.pers.allen.pawposter.model.entity.Post;
import tw.pers.allen.pawposter.model.entity.Tag;
import tw.pers.allen.pawposter.model.repository.CommentRepository;
import tw.pers.allen.pawposter.model.repository.ImageRepository;
import tw.pers.allen.pawposter.model.repository.MemberRepository;
import tw.pers.allen.pawposter.model.repository.MemberStatusRepository;
import tw.pers.allen.pawposter.model.repository.PostRepository;
import tw.pers.allen.pawposter.model.repository.TagRepository;

@Component
public class Initialize implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private MemberStatusRepository memberStatusRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		try {
			buildMemberStatuses();
			buildMembers();
			buildTags();
			buildPosts();
			buildImages();
			buildComments();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildMemberStatuses() throws Exception {
		// 若表格中存在任何一筆資料，終止程式
		long count = memberStatusRepository.count();
		if (count != 0) {
			return;
		}

		// 從 src/main/resources 取得 .json 檔案
		File file = ResourceUtils.getFile("classpath:init\\member_statuses.json");

		// 將 .json 文字檔轉換成 java 物件
		ObjectMapper mapper = new ObjectMapper();
		List<MemberStatus> memberStatuses = mapper.readValue(file, new TypeReference<List<MemberStatus>>() {
		});

		// 存入資料庫
		memberStatusRepository.saveAll(memberStatuses);
	}

	private void buildMembers() throws Exception {
		// 若表格中存在任何一筆資料，終止程式
		long count = memberRepository.count();
		if (count != 0) {
			return;
		}

		// 從 src/main/resources 取得 .json 檔案
		File file = ResourceUtils.getFile("classpath:init\\members.json");

		// 將 .json 文字檔轉換成 java 物件
		ObjectMapper mapper = new ObjectMapper();
		List<Member> members = mapper.readValue(file, new TypeReference<List<Member>>() {
		});

		// 互設關聯 && 抓取圖片
		for (int i = 0; i < members.size(); i++) {
			Member m = members.get(i);
			MemberDetail md = m.getMemberDetail();
			md.setMember(m);

			File photo = ResourceUtils
					.getFile("classpath:init\\images\\members\\member-type-B-%02d.png".formatted(i + 1));
			byte[] photoContent = FileCopyUtils.copyToByteArray(photo);

			md.setMemberPhoto(photoContent);
		}

		// 存入資料庫
		memberRepository.saveAll(members);
	}

	private void buildTags() throws Exception {
		// 若表格中存在任何一筆資料，終止程式
		long count = tagRepository.count();
		if (count != 0) {
			return;
		}

		// 從 src/main/resources 取得 .json 檔案
		File file = ResourceUtils.getFile("classpath:init\\tags.json");

		// 將 .json 文字檔轉換成 java 物件
		ObjectMapper mapper = new ObjectMapper();
		List<Tag> tags = mapper.readValue(file, new TypeReference<List<Tag>>() {
		});

		// 存入資料庫
		tagRepository.saveAll(tags);
	}

	private void buildPosts() throws Exception {
		// 若表格中存在任何一筆資料，終止程式
		long count = postRepository.count();
		if (count != 0) {
			return;
		}

		// 從 src/main/resources 取得 .json 檔案
		File file = ResourceUtils.getFile("classpath:init\\posts.json");

		// 將 .json 文字檔轉換成 java 物件
		ObjectMapper mapper = new ObjectMapper();
		List<Post> posts = mapper.readValue(file, new TypeReference<List<Post>>() {
		});

		// 對 post 的 tag 做特殊處理
		List<Tag> tags = mapper.readValue(ResourceUtils.getFile("classpath:init\\tags.json"),
				new TypeReference<List<Tag>>() {
				});

		// 每三個 tag 屬於一個 post
		for (int i = 0; i < posts.size(); i++) {
			Post post = posts.get(i);

			ArrayList<Tag> postTags = new ArrayList<Tag>();
			postTags.add(tags.get(3 * i + 0));
			postTags.add(tags.get(3 * i + 1));
			postTags.add(tags.get(3 * i + 2));

			post.setTags(postTags);
		}

		// 存入資料庫
		postRepository.saveAll(posts);
	}

	private void buildImages() throws Exception {
		// 若表格中存在任何一筆資料，終止程式
		long count = imageRepository.count();
		if (count != 0) {
			return;
		}

		// 從 src/main/resources 取得 .json 檔案
		File file = ResourceUtils.getFile("classpath:init\\images.json");

		// 準備 Entity list
		List<Image> images = new ArrayList<Image>();

		// 將 .json 文字檔轉換成 arrayNode 物件，處理後放入 Entity list
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode imageNodes = (ArrayNode) mapper.readTree(file);

		for (JsonNode imageNode : imageNodes) {

			ObjectNode imageObject = (ObjectNode) imageNode;
			String imageName = imageObject.get("imageName").asText();
			String imagePath = imageObject.get("path").asText();

			byte[] imageConent = FileCopyUtils.copyToByteArray(ResourceUtils.getFile(imagePath));

			images.add(new Image(imageName, imageConent));
		}

		imageRepository.saveAll(images);

	}

	private void buildComments() throws Exception {
		// 若表格中存在任何一筆資料，終止程式
		long count = commentRepository.count();
		if (count != 0) {
			return;
		}

		// 從 src/main/resources 取得 .json 檔案
		File file = ResourceUtils.getFile("classpath:init\\comments.json");

		// 將 .json 文字檔轉換成 java 物件
		ObjectMapper mapper = new ObjectMapper();
		List<Comment> comments = mapper.readValue(file, new TypeReference<List<Comment>>() {
		});

		// 存入資料庫
		commentRepository.saveAll(comments);
	}

}
