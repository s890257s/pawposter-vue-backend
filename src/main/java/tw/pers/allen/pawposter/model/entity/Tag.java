package tw.pers.allen.pawposter.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.pers.allen.pawposter.model.audit.AbstractAuditEntity;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag extends AbstractAuditEntity {

	public Tag(Integer tagId) {
		this.tagId = tagId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tagId;

	@Column(unique = true)
	private String tagName;

	@OneToMany(mappedBy = "tag")
	private List<PostTag> postTags = new ArrayList<>();
}
