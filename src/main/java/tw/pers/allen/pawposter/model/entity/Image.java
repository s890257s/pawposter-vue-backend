package tw.pers.allen.pawposter.model.entity;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table
public class Image {

	public Image() {
		super();
	}

	public Image(String imageName, byte[] imageContent) {
		super();
		this.imageName = imageName;
		this.imageContent = imageContent;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer imageId;

	@Column(length = 50)
	@Nationalized
	private String imageName;

	@Lob
	private byte[] imageContent;

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public byte[] getImageContent() {
		return imageContent;
	}

	public void setImageContent(byte[] imageContent) {
		this.imageContent = imageContent;
	}

}
