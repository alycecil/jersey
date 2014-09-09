package org.wcecil.beans.reflection;

import java.util.List;

import org.wcecil.beans.BeanBase;

public class ClassDescription extends BeanBase {
	private List<MethodDescription> methods;
	private String name;
	private String path;

	public List<MethodDescription> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodDescription> methods) {
		this.methods = methods;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
