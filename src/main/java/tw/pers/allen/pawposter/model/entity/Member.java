package tw.pers.allen.pawposter.model.entity;

import java.util.Date;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;

	@Column(length = 50)
	@Nationalized
	private String memberName;

	@Column(length = 50)
	@Nationalized
	private String memberMail;

	@Column(length = 60)
	@Nationalized
	private String memberPassword;

	private Date createdAt;

	private Date loggedInAt;

	@JoinColumn(name = "member_status_id")
	@ManyToOne
	private MemberStatus memberStatus;

	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
	private MemberDetail memberDetail;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberMail() {
		return memberMail;
	}

	public void setMemberMail(String memberMail) {
		this.memberMail = memberMail;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLoggedInAt() {
		return loggedInAt;
	}

	public void setLoggedInAt(Date loggedInAt) {
		this.loggedInAt = loggedInAt;
	}

	public MemberStatus getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}

	public MemberDetail getMemberDetail() {
		return memberDetail;
	}

	public void setMemberDetail(MemberDetail memberDetail) {
		this.memberDetail = memberDetail;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberName=" + memberName + ", memberMail=" + memberMail
				+ ", memberPassword=" + memberPassword + ", createdAt=" + createdAt + ", loggedInAt=" + loggedInAt
				+ ", memberStatus=" + memberStatus + ", memberDetail=" + memberDetail + "]";
	}

}
