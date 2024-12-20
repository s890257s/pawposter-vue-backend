package tw.pers.allen.pawposter.model.dto;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.PostResource;

@NoArgsConstructor
@Getter
@Setter
public class PostResourceDto {

	private Integer postResourceId;

	private String filePath;

	private String mimeType;

	private byte[] resourceContent;

}
