package org.wcecil.data;

import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wcecil.TestBase;
import org.wcecil.application.MyApp;
import org.wcecil.beans.UserBean;
import org.wcecil.beans.UserBeanTest;

public class UsersDataTest extends TestBase {
	@BeforeClass
	public static void beforeClass() throws Exception {
		MyApp.APP_KEY_POSTFIX = ":TEST";

		deleteAll();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		MyApp.APP_KEY_POSTFIX = ":TEST";

		deleteAll();
	}

	public static void deleteAll() {
		List<UserBean> list = UsersDataAccess.listAllUsers();

		for (UserBean u : list) {
			UsersDataAccess.deleteUserBean(u.getUuid());
		}
	}

	@SuppressWarnings("deprecation")
	private void deleteBean(UserBean bean) {
		UserBean resDelete = UsersDataAccess.deleteUserBean(bean.getUuid());

		assertNotNull("Delete must of been successful", resDelete);
	}

	@Test
	@SuppressWarnings("deprecation")
	public void getAllThenGetByUUID() {
		UserBean bean = insertBean();

		List<UserBean> list = UsersDataAccess.listAllUsers();

		assertFalse("List must not be empty", list == null || list.isEmpty());

		int matches = 0;
		for (UserBean u : list) {
			if (u != null) {
				if (u.getUuid() == null) {
					continue;
				}

				UserBean u2 = UsersDataAccess.getUser(u.getUuid());

				assertEquals("UserBean must be equal for uuid:" + u.getUuid(),
						u, u2);

				matches++;
			}
		}

		assertTrue("Must have found at least 1", matches > 0);

		deleteBean(bean);

	}

	@SuppressWarnings("deprecation")
	private UserBean insertBean() {
		UserBean bean = UserBeanTest.getTestBean();

		assertNotNull("Test Bean May Not Be Null.", bean);

		UserBean resBeanPut = UsersDataAccess.createUser(bean);

		assertEquals("Insert result is equal", bean, resBeanPut);

		return bean;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void insertGetDeleteTest() {
		UserBean bean = insertBean();

		UserBean resBeanGet = UsersDataAccess.getUser(bean.getUuid());

		assertEquals("Result bean is equivilent after get", bean, resBeanGet);

		deleteBean(bean);

	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void insertUpdateDeleteTest() {
		
		UserBean bean = insertBean();
		
		bean.setCookie(UUID.randomUUID().toString());
		
		UsersDataAccess.saveUser(bean);
		
		UserBean resBeanGet = UsersDataAccess.getUser(bean.getUuid());
		
		assertEquals("Result bean is equivilent after get", bean, resBeanGet);

		deleteBean(bean);

	}
}
