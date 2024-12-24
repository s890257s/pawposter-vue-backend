package tw.pers.allen.pawposter.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	private String memberPhoto;
	
	private Boolean isDeleted;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDate;

	private List<PostResourceDto> resources = new ArrayList<>();

	private List<String> tagNames = new ArrayList<>();

	private List<ReplyDto> replies = new ArrayList<>();
}
