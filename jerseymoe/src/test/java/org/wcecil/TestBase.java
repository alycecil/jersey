package org.wcecil;

import java.lang.reflect.Field;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;
import org.wcecil.application.MyApp;
import org.wcecil.beans.BeanBase;
import org.wcecil.beans.DataBean;
import org.wcecil.beans.DataBeanTest;
import org.wcecil.beans.UserBean;
import org.wcecil.beans.UserBeanTest;

@SuppressWarnings("deprecation")
public class TestBase extends Assert {

	public static Random r = new Random();

	@Test
	public void test() {
		assertTrue("core test", true);
		assertNotNull("core test", MyApp.APP_KEY_PREFIX);
	}

	@Test
	public void testEqualsBeans() {
		UserBean u1 = UserBeanTest.getTestBean();
		UserBean u2 = UserBeanTest.getTestBean();
		DataBean d1 = DataBeanTest.getTestBean();
		if (u1.equals(u2)) {
			throw new AssertionError("Guids matched!");
		}
		assertEquals("Test", u1, u1);

		try {
			assertEquals("Test", u1, u2);
			fail("Should not have passed");
		} catch (AssertionError e) {
			// expected
		}
		try {
			assertEquals("Test", u1, d1);
			fail("Should not have passed");
		} catch (AssertionError e) {
			// expected
		}

	}

	public void assertEquals(String msg, BeanBase b0, BeanBase b1) {
		if (b0 == null) {
			super.assertEquals(msg, b0, b1);
		}

		if (b0.equals(b1)) {
			return;
		}

		if (b0.getClass().equals(b1.getClass())) {
			Field[] flds = b0.getClass().getDeclaredFields();
			for (Field f : flds) {
				try {
					f.setAccessible(true);
					Object o_b0 = f.get(b0);
					Object o_b1 = f.get(b1);

					if (o_b0 == null && o_b1 != null) {
						super.assertEquals(msg + "Expected Null for field "+f.getName(), null, o_b1);
					} else if (o_b0 != null && o_b1 == null) {
						super.assertEquals(msg+" for field "+f.getName(), o_b0, null);
					} else if ((o_b0 == null && o_b1 == null)
							|| (o_b0.equals(o_b1))) {
						continue;
					}

					try {
						String json_0 = BeanBase.getObjectAsJsonString(o_b0);
						String json_1 = BeanBase.getObjectAsJsonString(o_b1);

						if (json_0 == null || json_1 == null) {
							throw new IllegalArgumentException(
									"unable to get json.");
						}
						assertEquals(
								"JSON Should be the same for field "
										+ f.getName(), json_0, json_1);
					} catch (IllegalArgumentException e) {
						throw new AssertionError(
								"Unable to use json parser, please use different method of comparason",
								e);
					}

				} catch (IllegalArgumentException e) {

					e.printStackTrace();

					throw new AssertionError(msg + ": field difference for "
							+ f.getName(), e);
				} catch (IllegalAccessException e) {

					e.printStackTrace();
					throw new AssertionError(msg + ": field difference for "
							+ f.getName(), e);

				}

			}

		} else {
			throw new AssertionError(msg + ": class difference, expected : "
					+ b0.getClass() + ", actual: " + b1.getClass());
		}
	}

}
