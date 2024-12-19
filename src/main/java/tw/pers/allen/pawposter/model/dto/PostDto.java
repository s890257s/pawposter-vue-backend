package tw.pers.allen.pawposter.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.model.entity.Post;
import tw.pers.allen.pawposter.model.entity.PostResource;
import tw.pers.allen.pawposter.model.entity.PostTag;
import tw.pers.allen.pawposter.model.entity.Tag;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	public PostDto(Post post) {
		BeanUtils.copyProperties(post, this);
		BeanUtils.copyProperties(post.getMember(), this);

		this.resources = post.getPostResources().stream().map(PostResourceDto::new).toList();
		this.tags = post.getPostTags().stream().map(pt -> new TagDto(pt.getTag())).toList();
		this.replies = post.getReplies().stream().map(ReplyDto::new).toList();
	}

	private Integer postId;

	private String postText;

	private Integer memberId;

	private String memberName;

	private List<PostResourceDto> resources = new ArrayList<>();

	private List<TagDto> tags = new ArrayList<>();

	private List<ReplyDto> replies = new ArrayList<>();

	public Post toPost() {
		// 準備好 entity
		Post post = new Post();
		Member member = new Member();

		// 複製 dto 屬性至 entity
		BeanUtils.copyProperties(this, post);
		BeanUtils.copyProperties(this, member);

		// 根據 dto 屬性建立 postResource 集合，並設定關聯 post
		List<PostResource> postResources = this.resources.stream().map(resourceDto -> {
			PostResource postResource = resourceDto.toPostResource();
			postResource.setPost(post);
			return postResource;
		}).toList();

		// 根據 dto 屬性建立 postTag 集合，並設定關聯 post
		List<PostTag> postTags = this.tags.stream().map(tagDto -> {
			PostTag postTag = new PostTag();
			postTag.setPost(post);
			postTag.setTag(new Tag(tagDto.getTagId()));

			return postTag;
		}).toList();

		// 互設關聯
		post.setMember(member);
		post.setPostResources(postResources);
		post.setPostTags(postTags);

		return post;
	}
}
