package tw.pers.allen.pawposter.model.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tw.pers.allen.pawposter.model.audit.AbstractAuditEntity;

@Table
@Entity
@Getter
@Setter
public class MemberDetail extends AbstractAuditEntity {

	@Id
	private Integer fkMemberId;

	private Date memberBirthday;

	private String memberGender;

	@Lob
	private byte[] memberPhoto;

	@JoinColumn(name = "fk_member_id", foreignKey = @ForeignKey(name = "fk_member_detail_member", foreignKeyDefinition = "FOREIGN KEY (fk_member_id) REFERENCES Member(member_id) ON DELETE CASCADE ON UPDATE CASCADE"))
	@OneToOne
	@MapsId
	private Member member;

}
