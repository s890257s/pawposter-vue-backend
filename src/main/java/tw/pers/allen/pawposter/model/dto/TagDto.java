package tw.pers.allen.pawposter.model.dto;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.Tag;

@Getter
@Setter
public class TagDto {

	public TagDto(Tag tag) {
		BeanUtils.copyProperties(tag, this);
	}

	private Integer tagId;

	private String tagName;

	public Tag toTag() {
		Tag tag = new Tag();

		BeanUtils.copyProperties(this, tag);

		return tag;
	}
}
