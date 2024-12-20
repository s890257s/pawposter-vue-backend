package tw.pers.allen.pawposter.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostDto {

	private Integer postId;

	private String postText;

	private Integer memberId;

	private String memberName;

	private Boolean isDeleted;

	private List<PostResourceDto> resources = new ArrayList<>();

	private List<String> tagNames = new ArrayList<>();

	private List<ReplyDto> replies = new ArrayList<>();
}
