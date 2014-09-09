package org.wcecil.beans.reflection;

import org.wcecil.beans.BeanBase;

public class ParameterDescription extends BeanBase {
	private String name;
	private String type;
	private String form;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}
}
