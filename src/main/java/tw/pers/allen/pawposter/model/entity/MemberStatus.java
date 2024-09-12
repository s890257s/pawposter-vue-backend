package tw.pers.allen.pawposter.model.entity;

import java.util.List;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class MemberStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberStatusId;

	@Column(length = 10)
	@Nationalized
	private String memberStatus;

	@OneToMany(mappedBy = "memberStatus")
	private List<Member> members;

	public Integer getMemberStatusId() {
		return memberStatusId;
	}

	public void setMemberStatusId(Integer memberStatusId) {
		this.memberStatusId = memberStatusId;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

}
