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

	public PostResourceDto(PostResource postResource) {
		BeanUtils.copyProperties(postResource, this);
	}

	private Integer postResourceId;

	private String filePath;

	private String mimeType;

	private byte[] resourceContent;

	public PostResource toPostResource() {
		PostResource postResource = new PostResource();
		BeanUtils.copyProperties(this, postResource);
		return postResource;
	}
}
