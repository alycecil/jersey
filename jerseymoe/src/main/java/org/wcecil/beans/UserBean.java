package org.wcecil.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class UserBean extends BeanBase {
	@Id
	private String id;

	@Indexed(unique=true)
	private String uuid;

	private Long createdOn;

	private Long updatedOn;

	public Long getCreatedOn() {
		return createdOn;
	}

	public String getId() {
		return id;
	}

	public Long getUpdatedOn() {
		return updatedOn;
	}

	public String getUuid() {
		return uuid;
	}

	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUpdatedOn(Long updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
