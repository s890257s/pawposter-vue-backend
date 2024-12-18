package tw.pers.allen.pawposter.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggedInMemberDto {

	private String jwtToken;

	private String memberName;

	private String memberMail;

	private byte[] memberPhoto;

}
