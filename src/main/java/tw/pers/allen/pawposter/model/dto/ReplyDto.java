package tw.pers.allen.pawposter.model.dto;

import org.springframework.beans.BeanUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.Reply;

@Getter
@Setter
public class ReplyDto {

	public ReplyDto(Reply reply) {
		BeanUtils.copyProperties(reply, this);
	}

	private Integer replyId;

	private String replyText;

	private Boolean isDeleted;
}
