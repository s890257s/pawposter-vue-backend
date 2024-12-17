package tw.pers.allen.pawposter.config;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.model.entity.MemberDetail;
import tw.pers.allen.pawposter.model.entity.Post;
import tw.pers.allen.pawposter.model.entity.PostResource;
import tw.pers.allen.pawposter.model.entity.PostTag;
import tw.pers.allen.pawposter.model.entity.Reply;
import tw.pers.allen.pawposter.model.entity.Tag;
import tw.pers.allen.pawposter.repository.MemberRepository;
import tw.pers.allen.pawposter.repository.PostRepository;
import tw.pers.allen.pawposter.repository.PostTagRepository;
import tw.pers.allen.pawposter.repository.ReplyRepository;
import tw.pers.allen.pawposter.repository.TagRepository;
import tw.pers.allen.pawposter.tools.JwtTool;

@Component
public class Initialize implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(Initialize.class);

	private final ObjectMapper jsonMapper;
	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final TagRepository tagRepository;
	private final PostTagRepository postTagRepository;
	private final ReplyRepository replyRepository;

	public Initialize(ObjectMapper jsonMapper, MemberRepository memberRepository, PostRepository postRepository,
			TagRepository tagRepository, PostTagRepository postTagRepository, ReplyRepository replyRepository) {
		this.jsonMapper = jsonMapper;
		this.memberRepository = memberRepository;
		this.postRepository = postRepository;
		this.tagRepository = tagRepository;
		this.postTagRepository = postTagRepository;
		this.replyRepository = replyRepository;
	}

	@Autowired
	private JwtTool jwtTool;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		String token = jwtTool.generateToken("1");

		System.out.println(jwtTool.getValue(
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJpYXQiOjE3MzQ0MjgyNDUsImV4cCI6MTczNDQzMTg0NSwiREREIjoiZWUifQ.dmJcOOLByYOOUXZWc5kPE4qV0TJA2NjRo1vZWBp5qYI",
				"sub"));

		try {
			readAndCreateMember();
			readAndCreatePost();
			readAndCreateTag();
			readAndCreatePostTag();
			readAndCreateReply();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 讀取並新增 member 與 member_detail。
	 */
	private void readAndCreateMember() throws Exception {
		if (memberRepository.count() != 0) {
			log.debug("member 表格已有資料，不進行新增。");
			return;
		}

		// 讀取 JSON 檔案
		List<Member> members = jsonMapper.readValue(ResourceUtils.getFile("classpath:init\\members.json"),
				new TypeReference<>() {
				});

		for (Member member : members) {
			MemberDetail memberDetail = member.getMemberDetail();
			File file = ResourceUtils.getFile("classpath:init\\image\\member-" + member.getMemberName() + ".png");

			// 設定圖片
			memberDetail.setMemberPhoto(FileCopyUtils.copyToByteArray(file));

			// 互設關聯
			memberDetail.setMember(member);
		}

		// 存入資料庫
		memberRepository.saveAll(members);
		log.info("新增 members 資料完成");
	}

	/**
	 * 讀取並新增 post 與 post_resource。
	 */
	private void readAndCreatePost() throws Exception {
		if (postRepository.count() != 0) {
			log.debug("post 表格已有資料，不進行新增。");
			return;
		}

		// 讀取 JSON 檔案
		List<Post> posts = jsonMapper.readValue(ResourceUtils.getFile("classpath:init\\posts.json"),
				new TypeReference<>() {
				});

		for (Post post : posts) {
			List<PostResource> postResources = post.getPostResources();

			for (PostResource r : postResources) {
				File file = ResourceUtils.getFile(r.getFilePath());
				r.setResourceContent(FileCopyUtils.copyToByteArray(file));
				r.setMimeType(Files.probeContentType(file.toPath()));
				r.setPost(post);
			}
		}

		// 存入資料庫
		postRepository.saveAll(posts);
		log.info("新增 posts 資料完成");
	}

	/**
	 * 讀取並新增 tag。
	 */
	private void readAndCreateTag() throws Exception {
		if (tagRepository.count() != 0) {
			log.debug("tag 表格已有資料，不進行新增。");
			return;
		}

		// 讀取 JSON 檔案
		List<Tag> tags = jsonMapper.readValue(ResourceUtils.getFile("classpath:init\\tags.json"),
				new TypeReference<>() {
				});

		// 存入資料庫
		tagRepository.saveAll(tags);
		log.info("新增 tags 資料完成");
	}

	/**
	 * 讀取並新增 post_tag 關聯表。
	 */
	private void readAndCreatePostTag() throws Exception {
		if (postTagRepository.count() != 0) {
			log.debug("post_tag 表格已有資料，不進行新增。");
			return;
		}

		// 讀取 JSON 檔案
		List<PostTag> postTags = jsonMapper.readValue(ResourceUtils.getFile("classpath:init\\post_tag.json"),
				new TypeReference<>() {
				});

		postTagRepository.saveAll(postTags);
		log.info("新增 post_tag 資料完成");

	}

	/**
	 * 讀取並新增 reply。
	 */
	private void readAndCreateReply() throws Exception {
		if (replyRepository.count() != 0) {
			log.debug("reply 表格已有資料，不進行新增。");
			return;
		}

		// 讀取 JSON 檔案
		List<Reply> replies = jsonMapper.readValue(ResourceUtils.getFile("classpath:init\\replies.json"),
				new TypeReference<>() {
				});

		replyRepository.saveAll(replies);
		log.info("新增 replies 資料完成");
	};
}
