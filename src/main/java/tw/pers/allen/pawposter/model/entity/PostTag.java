package tw.pers.allen.pawposter.model.entity;

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
import tw.pers.allen.pawposter.model.audit.AbstractAuditEntity;

@Table
@Entity
@Getter
@Setter
public class PostTag extends AbstractAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postTagId;

	@ManyToOne
	@JoinColumn(name = "fk_post_id", foreignKey = @ForeignKey(name = "fk_post_post_tag", foreignKeyDefinition = "FOREIGN KEY (fk_post_id) REFERENCES Post(post_id) ON DELETE CASCADE ON UPDATE CASCADE"))
	private Post post;

	@ManyToOne
	@JoinColumn(name = "fk_tag_id", foreignKey = @ForeignKey(name = "fk_tag_post_tag", foreignKeyDefinition = "FOREIGN KEY (fk_tag_id) REFERENCES Tag(tag_id) ON DELETE CASCADE ON UPDATE CASCADE"))
	private Tag tag;
}
