package tw.pers.allen.pawposter.tools;

import java.util.Base64;

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

			memberDto.setMemberPhoto(CommonTool.convertByteArrayToBase64String(photo));

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

			byte[] content = postResource.getResourceContent();
			if (content != null && content.length != 0) {
				postResourceDto.setContent(CommonTool.convertByteArrayToBase64String(content));
			}

			return postResourceDto;
		}

		public static PostResource toEntity(PostResourceDto postResourceDto) {
			PostResource postResource = new PostResource();
			BeanUtils.copyProperties(postResourceDto, postResource);
			byte[] content = Base64.getDecoder().decode(postResourceDto.getContent().split(",")[1]);
			postResource.setResourceContent(content);

			return postResource;
		}

		public static PostDto toDto(Post post) {
			PostDto postDto = new PostDto();

			BeanUtils.copyProperties(post, postDto);
			BeanUtils.copyProperties(post.getMember(), postDto);

			postDto.setResources(post.getPostResources().stream().map(PostMapper::toDto).toList());
			postDto.setTagNames(post.getPostTags().stream().map(ps -> ps.getTag().getTagName()).toList());
			postDto.setReplies(post.getReplies().stream().map(ReplyDto::new).toList());

			return postDto;
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
