package tw.pers.allen.pawposter.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResourceDto {

	private Integer postResourceId;

	private String filePath;

	private String mimeType;

	private byte[] resourceContent;

}
