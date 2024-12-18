package tw.pers.allen.pawposter.model.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.Member;
import tw.pers.allen.pawposter.model.entity.MemberDetail;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto {

	public MemberDto(Member member) {
		BeanUtils.copyProperties(member, this);
		BeanUtils.copyProperties(member.getMemberDetail(), this);
	}

	private String memberName;

	private String memberMail;

	private String memberPassword;

	private Date loggedInDate;

	private Boolean isEnabled;

	private Date memberBirthday;

	private String memberGender;

	private byte[] memberPhoto;

	public Member toMember() {
		Member member = new Member();
		BeanUtils.copyProperties(this, member);

		return member;
	}
}
