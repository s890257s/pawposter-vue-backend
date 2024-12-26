package tw.pers.allen.pawposter.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostUploadDto {

	private String text;

	private List<MultipartFile> files = new ArrayList<>();

	private List<String> tags = new ArrayList<>();
}
