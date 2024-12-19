package tw.pers.allen.pawposter.model.dto;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.model.entity.MemberDetail;
import tw.pers.allen.pawposter.tools.CommonTool;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "memberMail", "memberName" })
public class MemberDto {

	public MemberDto(Member member) {
		BeanUtils.copyProperties(member, this);
		BeanUtils.copyProperties(member.getMemberDetail(), this);

		byte[] photo = member.getMemberDetail().getMemberPhoto();
		if (photo == null || photo.length == 0) {
			return;
		}

		try {
			this.memberPhoto = CommonTool.convertByteArrayToBase64String(photo);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

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

	public Member toMember() {
		Member member = new Member();
		MemberDetail memberDetail = new MemberDetail();

		member.setMemberDetail(memberDetail);
		memberDetail.setMember(member);

		BeanUtils.copyProperties(this, member);
		BeanUtils.copyProperties(this, memberDetail);

		return member;
	}
}
