package tw.pers.allen.pawposter.model.dto;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.pers.allen.pawposter.model.entity.Member;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto {

	public MemberDto(Member member) {
		BeanUtils.copyProperties(member, this);
	}

	public Member toMember() {
		Member member = new Member();
		BeanUtils.copyProperties(this, member);
		
		return member;
	}
}
