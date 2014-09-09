package org.wcecil.data;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wcecil.TestBase;
import org.wcecil.application.MyApp;
import org.wcecil.beans.DataBean;
import org.wcecil.beans.DataBeanTest;

public class DataStorageDataTest extends TestBase {
	@BeforeClass
	public static void beforeClass() throws Exception {
		MyApp.APP_KEY_POSTFIX = ":TEST";

		//deleteAll();
	}

	@AfterClass
	public static void afterClass() throws Exception {
		MyApp.APP_KEY_POSTFIX = ":TEST";

		//deleteAll();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void insertGetDeleteTest() {
		DataBean bean = DataBeanTest.getTestBean();
		
		assertNotNull("May not test a null bean",bean);
		
		DataBean beanResultInsert =DataStorageAccess.setKeyForOwner(bean);
		assertNotNull("May not get null back from insert",beanResultInsert);
		
		String id = beanResultInsert.getId();
		assertNotNull("May not get null id back from insert",id);
		
		bean.setId(null);
		beanResultInsert.setId(null);
		assertEquals("Insert Must Be same sans id", bean,beanResultInsert);

		DataBean resultGet = DataStorageAccess.getKeyForOwner(bean.getOwner(), bean.getKey());
		assertNotNull("May not get null back from get",resultGet);
		
		bean.setId(null);
		resultGet.setId(null);
		assertEquals("Get Must Be same sans id", bean,resultGet);
	}
}
