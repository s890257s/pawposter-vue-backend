package tw.pers.allen.pawposter.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tw.pers.allen.pawposter.model.audit.AbstractAuditEntity;

@Table
@Entity
@Getter
@Setter
@ToString
public class PostTag extends AbstractAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postTagId;

	@ManyToOne
    @JoinColumn(name = "fk_post_id", foreignKey = @ForeignKey(name = "fk_post_post_tag"))
	private Post post;

	@ManyToOne
    @JoinColumn(name = "fk_tag_id", foreignKey = @ForeignKey(name = "fk_tag_post_tag"))
	private Tag tag;
}
