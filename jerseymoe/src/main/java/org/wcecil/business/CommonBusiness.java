package org.wcecil.business;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.wcecil.beans.BeanBase;
import org.wcecil.beans.reflection.ClassDescription;
import org.wcecil.beans.reflection.MethodDescription;
import org.wcecil.beans.reflection.ParameterDescription;

public class CommonBusiness {

	private static String resourcePath = "org.wcecil.resources";

	public static Response getAllResources() {
		///Reflections reflections = new Reflections(resourcePath);

		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(resourcePath))));
		
		Set<Class<?>> resources = reflections.getSubTypesOf(Object.class);//reflections.getTypesAnnotatedWith(Path.class);

		String relativepath = "";

		List<ClassDescription> classes = new ArrayList<ClassDescription>();
		for (Class<?> clz : resources) {
			relativepath="/webapi";
			ClassDescription _class = new ClassDescription();
			List<MethodDescription> methods = new ArrayList<MethodDescription>();

			for (Annotation a : clz.getDeclaredAnnotations()) {
				if (a != null) {
					if (a instanceof Path) {
						Path p = (Path) a;
						relativepath = p.value();
					}
				}
			}

			if (clz != null) {
				for (Method m : clz.getDeclaredMethods()) {
					methods.add(getMethodDescription(m, relativepath));
				}
//				for (Method m : clz.getMethods()) {
//					methods.add(getMethodDescription(m, relativepath));
//				}
			}

			_class.setPath(relativepath);
			_class.setName(clz.getName());
			_class.setMethods(methods);
			classes.add(_class);
		}

		return Response.ok().entity(BeanBase.getObjectAsJsonString(classes)).build();
	}

	private static MethodDescription getMethodDescription(Method m,
			String relativepath) {
		if (relativepath == null) {
			relativepath = "";
		}
		String method = "GET";
		String name = m.getName();
		String path = relativepath;

		List<ParameterDescription> params = new ArrayList<ParameterDescription>();

		for (Annotation a : m.getDeclaredAnnotations()) {
			if (a != null) {
				if (a instanceof Path) {
					Path p = (Path) a;
					path = relativepath + p.value();
				} else if (a instanceof HttpMethod) {
					HttpMethod _m = (HttpMethod) a;
					method = _m.value();
				} else if (a instanceof PUT) {

					method = "PUT";
				} else if (a instanceof GET) {

					method = "GET";
				} else if (a instanceof POST) {

					method = "POST";
				} else if (a instanceof DELETE) {

					method = "DELETE";
				}

			}
		}

		for (Parameter p : m.getParameters()) {
			ParameterDescription param = new ParameterDescription();
			param.setType(p.getType().getName());
			param.setName(p.getName());

			for (Annotation a : p.getDeclaredAnnotations()) {
				if (a != null) {
					if (a instanceof QueryParam) {
						QueryParam q = (QueryParam) a;
						param.setName(q.value());
						param.setForm("Query");
						break;
					} else if (a instanceof FormParam) {
						FormParam q = (FormParam) a;
						param.setName(q.value());
						param.setForm("Form");
						break;
					} else if (a instanceof PathParam) {
						PathParam q = (PathParam) a;
						param.setName(q.value());
						param.setForm("Path");
						break;
					} else if (a instanceof HeaderParam) {
						HeaderParam q = (HeaderParam) a;
						param.setName(q.value());
						param.setForm("Header");
						break;
					} else {
						param.setForm(a.annotationType().getName());
						break;
					}

				}

			}

			params.add(param);
		}

		MethodDescription desc = new MethodDescription();
		desc.setMethod(method);
		desc.setName(name);
		desc.setPath(path);
		desc.setParams(params);
		return desc;
	}

	
	public static void main(String [] args ){
		Response r = getAllResources();
		System.out.println(r.getEntity());
	}
}
