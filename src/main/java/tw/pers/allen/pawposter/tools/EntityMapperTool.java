package tw.pers.allen.pawposter.tools;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;

import tw.pers.allen.pawposter.model.dto.MemberDto;
import tw.pers.allen.pawposter.model.dto.PostDto;
import tw.pers.allen.pawposter.model.dto.PostResourceDto;
import tw.pers.allen.pawposter.model.dto.ReplyDto;
import tw.pers.allen.pawposter.model.dto.TagDto;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.model.entity.MemberDetail;
import tw.pers.allen.pawposter.model.entity.Post;
import tw.pers.allen.pawposter.model.entity.PostResource;
import tw.pers.allen.pawposter.model.entity.PostTag;
import tw.pers.allen.pawposter.model.entity.Tag;

public class EntityMapperTool {

	public static class MemberMapper {

		public static MemberDto toDto(Member member) {
			MemberDto memberDto = new MemberDto();
			BeanUtils.copyProperties(member, memberDto);
			BeanUtils.copyProperties(member.getMemberDetail(), memberDto);

			byte[] photo = member.getMemberDetail().getMemberPhoto();

			if (photo == null || photo.length == 0) {
				return memberDto;
			}

			try {
				memberDto.setMemberPhoto(CommonTool.convertByteArrayToBase64String(photo));
			} catch (IOException e) {
				e.printStackTrace();
			}

			return memberDto;
		}

		public static Member toEntity(MemberDto memberDto) {
			Member member = new Member();
			MemberDetail memberDetail = new MemberDetail();

			member.setMemberDetail(memberDetail);
			memberDetail.setMember(member);

			BeanUtils.copyProperties(memberDto, member);
			BeanUtils.copyProperties(memberDto, memberDetail);

			return member;
		}
	}

	public static class PostMapper {

		public static PostResourceDto toDto(PostResource postResource) {
			PostResourceDto postResourceDto = new PostResourceDto();
			BeanUtils.copyProperties(postResource, postResourceDto);
			return postResourceDto;
		}

		public static PostResource toEntity(PostResourceDto postResourceDto) {
			PostResource postResource = new PostResource();
			BeanUtils.copyProperties(postResourceDto, postResource);
			return postResource;
		}

		public static PostDto toDto(Post post) {
			PostDto postDto = new PostDto();

			BeanUtils.copyProperties(post, postDto);
			BeanUtils.copyProperties(post.getMember(), postDto);

			postDto.setResources(post.getPostResources().stream().map(PostMapper::toDto).toList());
			postDto.setTags(post.getPostTags().stream().map(pt -> TagMapper.toDto(pt.getTag())).toList());
			postDto.setReplies(post.getReplies().stream().map(ReplyDto::new).toList());

			return postDto;
		}

		public static Post toEntity(PostDto postDto) {
			// 準備好 entity
			Post post = new Post();
			Member member = new Member();

			// 複製 dto 屬性至 entity
			BeanUtils.copyProperties(postDto, post);
			BeanUtils.copyProperties(postDto, member);

			// 根據 dto 屬性建立 postResource 集合，並設定關聯 post
			List<PostResource> postResources = postDto.getResources().stream().map(resourceDto -> {
				PostResource postResource = PostMapper.toEntity(resourceDto);
				postResource.setPost(post);
				return postResource;
			}).toList();

			// 根據 dto 屬性建立 postTag 集合，並設定關聯 post
			List<PostTag> postTags = postDto.getTags().stream().map(tagDto -> {
				PostTag postTag = new PostTag();
				postTag.setPost(post);

				Tag tag = new Tag();
				tag.setTagId(tagDto.getTagId());
				tag.setTagName(tagDto.getTagName());
				postTag.setTag(tag);

				return postTag;
			}).toList();

			// 互設關聯
			post.setMember(member);
			post.setPostResources(postResources);
			post.setPostTags(postTags);

			return post;
		}
	}

	public static class TagMapper {
		public static TagDto toDto(Tag tag) {
			TagDto tagDto = new TagDto();

			BeanUtils.copyProperties(tag, tagDto);

			return tagDto;
		}

		public static Tag toEntity(TagDto tagDto) {

			Tag tag = new Tag();

			BeanUtils.copyProperties(tagDto, tag);

			return tag;
		}
	}
}
