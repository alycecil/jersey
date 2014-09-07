package org.wcecil.beans;

import java.util.UUID;

import org.junit.Test;
import org.wcecil.TestBase;

public class UserBeanTest extends TestBase {
	
	public static UserBean getTestBean() {
		UserBean bean = new UserBean();

		bean.setUuid(UUID.randomUUID().toString());

		return bean;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void serializeTest() {
		UserBean bean = getTestBean();
		
		assertNotNull("Test may not be on a null bean",bean);
		
		String json = bean.getObjectAsJsonString();
		
		assertNotNull("Json not expected to be null",json);
		
		UserBean u2 = (UserBean)BeanBase.getBeanFromJson(json, UserBean.class);
		
		assertEquals("User Beans must be the same.", bean, u2);
	}
}
