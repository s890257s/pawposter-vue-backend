package tw.pers.allen.pawposter.model.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "memberMail", "memberName" })
public class MemberDto {

	private Integer memberId;

	private String memberName;

	@NotBlank(message = "屬性不得為空")
	private String memberMail;

	@NotBlank(message = "屬性不得為空")
	private String memberPassword;

	private Date loggedInDate;

	private Boolean isEnabled;

	private Date memberBirthday;

	private String memberGender;

	private String memberPhoto;

}
