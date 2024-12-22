package tw.pers.allen.pawposter.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailAndPasswordDto {

	public EmailAndPasswordDto(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@NotBlank(message = "屬性不得為空")
	private String email;

	@NotBlank(message = "屬性不得為空")
	private String password;
}
