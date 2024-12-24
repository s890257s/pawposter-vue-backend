package tw.pers.allen.pawposter.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDto {

	private Integer replyId;

	private String replyText;

	private Boolean isDeleted;

	private Integer memberId;

	private String memberName;

	private String memberPhoto;

	private Date createdDate;
}
