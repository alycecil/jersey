package org.wcecil.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

@CompoundIndexes({
    @CompoundIndex(name = "key_owner_idx", def = "{'owner': 1, 'key': 1}")
})
public class DataBean extends BeanBase {

	@Id
	private String id;
	
	@Indexed
	private String key;
	

	@Indexed
	private String owner;
	
	
	private Object value;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
