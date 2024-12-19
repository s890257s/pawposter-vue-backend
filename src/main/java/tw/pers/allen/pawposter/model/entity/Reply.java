package tw.pers.allen.pawposter.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tw.pers.allen.pawposter.model.audit.AbstractAuditEntity;

@Table
@Entity
@Getter
@Setter
public class Reply extends AbstractAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer replyId;

	private String replyText;

	private Boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "fk_post_id")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "fk_member_id")
	private Member member;

}
