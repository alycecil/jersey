package org.wcecil.beans.reflection;

import java.util.List;

import org.wcecil.beans.BeanBase;

public class MethodDescription extends BeanBase {
	private String method;
	private String name;
	private String path;
	
	private List<ParameterDescription> params;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
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
	public List<ParameterDescription> getParams() {
		return params;
	}
	public void setParams(List<ParameterDescription> params) {
		this.params = params;
	}
	
	
}