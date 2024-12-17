package tw.pers.allen.pawposter.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class PostResource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postResourceId;

	private String filePath;

	private String mimeType;

	@Lob
	private byte[] resourceContent;

	@ManyToOne
	@JoinColumn(name = "fk_post_id", foreignKey = @ForeignKey(name = "fk_post_post_resource", foreignKeyDefinition = "FOREIGN KEY (fk_post_id) REFERENCES Post(post_id) ON DELETE CASCADE ON UPDATE CASCADE"))
	private Post post;

}
