package tw.pers.allen.pawposter.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tw.pers.allen.pawposter.model.audit.AbstractAuditEntity;

@Table
@Entity
@Getter
@Setter
public class Post extends AbstractAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	private String postText;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<PostResource> postResources = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "fk_member_id", foreignKey = @ForeignKey(name = "fk_member_post", foreignKeyDefinition = "FOREIGN KEY (fk_member_id) REFERENCES Member(member_id) ON DELETE CASCADE ON UPDATE CASCADE"))
	private Member member;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<PostTag> postTags = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
	private List<Reply> replies = new ArrayList<>();
}
