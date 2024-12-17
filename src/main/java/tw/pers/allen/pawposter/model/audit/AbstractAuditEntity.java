package tw.pers.allen.pawposter.model.audit;

import java.util.Date;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass // 映射超類別，此標註不會真的產生 Table
@EntityListeners(AuditListener.class)
@Setter
@Getter
public class AbstractAuditEntity {

	private Date createdDate;
	
	private Date updatedDate;

}
