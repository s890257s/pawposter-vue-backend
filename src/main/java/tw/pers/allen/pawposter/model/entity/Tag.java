package tw.pers.allen.pawposter.model.entity;

import java.util.List;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Tag {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tagId;

	@Column(length = 50)
	@Nationalized
	private String tagName;

	@ManyToMany(mappedBy = "tags")
	private List<Post> posts;

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
