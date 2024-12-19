package tw.pers.allen.pawposter.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAndPasswordDto {

	@NotBlank(message = "屬性不得為空")
	private String email;

	@NotBlank(message = "屬性不得為空")
	private String password;
}
