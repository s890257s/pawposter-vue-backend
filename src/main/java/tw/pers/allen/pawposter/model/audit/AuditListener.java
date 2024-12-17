package tw.pers.allen.pawposter.model.audit;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Component
public class AuditListener {

	@PrePersist
	public void beforeInsert(AbstractAuditEntity entity) {
		Date now = new Date();
		entity.setCreatedDate(now);
	}

	@PreUpdate
	public void beforeUpdate(AbstractAuditEntity entity) {
		Date now = new Date();
		entity.setUpdatedDate(now);
	}

}
