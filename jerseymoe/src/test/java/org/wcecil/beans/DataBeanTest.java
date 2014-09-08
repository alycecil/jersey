package org.wcecil.beans;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Test;
import org.wcecil.TestBase;

public class DataBeanTest extends TestBase {
	public static DataBean getTestBean() {
		DataBean bean = new DataBean();

		bean.setKey(UUID.randomUUID().toString());
		bean.setOwner(UUID.randomUUID().toString());

		int rand = TestBase.r.nextInt(4);

		if (rand == 0) {
			bean.setValue(UserBeanTest.getTestBean().getObjectAsJsonString());
		} else if (rand == 1) {
			bean.setValue(UserBeanTest.getTestBean());
		} else if (rand == 2) {
			HashMap<String, Object> map;
			map = new HashMap<String, Object>();

			rand = TestBase.r.nextInt(10);

			for (int i = 0; i < rand + 1; i++) {
				map.put(UUID.randomUUID().toString(), genValue());
			}

			bean.setValue(map);
		} else {
			bean.setValue(null);
		}
		return bean;
	}

	private static Object genValue() {
		int val = TestBase.r.nextInt(5);
		if (val == 0) {
			return UUID.randomUUID().toString();
		} else if (val == 1) {
			return TestBase.r.nextBoolean();
		} else if (val == 2) {
			return TestBase.r.nextInt();
		} else if (val == 3) {
			return TestBase.r.nextDouble();
		}

		return "null";
	}

	@SuppressWarnings("deprecation")
	@Test
	public void serializeTest() {

		DataBean bean = getTestBean();

		assertNotNull("Test may not be on a null bean", bean);

		String json = bean.getObjectAsJsonString();

		assertNotNull("Json not expected to be null", json);

		DataBean u2 = (DataBean) BeanBase.getBeanFromJson(json, DataBean.class);

		assertEquals("User Beans must be the same.", bean, u2);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void serializeTestDepth() {
		for (int i = 0; i < 300; i++) {
			DataBean bean = getTestBean();

			assertNotNull("Test may not be on a null bean", bean);

			String json = bean.getObjectAsJsonString();

			assertNotNull("Json not expected to be null", json);

			DataBean u2 = (DataBean) BeanBase.getBeanFromJson(json,
					DataBean.class);

			assertEquals("User Beans must be the same.", bean, u2);
		}
	}
}
