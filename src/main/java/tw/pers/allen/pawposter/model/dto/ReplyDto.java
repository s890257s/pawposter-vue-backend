package tw.pers.allen.pawposter.model.dto;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.Reply;

@Getter
@Setter
public class ReplyDto {

	public ReplyDto(Reply reply) {
		BeanUtils.copyProperties(reply, this);

		this.memberId = reply.getMember().getMemberId();
		this.memberName = reply.getMember().getMemberName();
	}

	private Integer replyId;

	private String replyText;

	private Boolean isDeleted;

	private Integer memberId;

	private String memberName;
}
