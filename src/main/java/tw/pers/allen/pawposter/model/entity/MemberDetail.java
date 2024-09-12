package tw.pers.allen.pawposter.model.entity;

import java.util.Date;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class MemberDetail {

	@Id
	private Integer memberId;

	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_member_detail_member", foreignKeyDefinition = "FOREIGN KEY (member_id) REFERENCES Member(member_id) ON DELETE CASCADE ON UPDATE CASCADE"))
	@OneToOne
	@MapsId
	private Member member;

	private Date memberBirthday;

	@Column(length = 50)
	@Nationalized
	private String memberGender;

	@Lob
	private byte[] memberPhoto;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getMemberBirthday() {
		return memberBirthday;
	}

	public void setMemberBirthday(Date memberBirthday) {
		this.memberBirthday = memberBirthday;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public byte[] getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(byte[] memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

}
