package tw.pers.allen.pawposter.model.dto;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.Tag;

@NoArgsConstructor
@Getter
@Setter
public class TagDto {

	private Integer tagId;

	private String tagName;

}
