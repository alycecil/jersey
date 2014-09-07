package org.wcecil.data;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.wcecil.application.MyApp;
import org.wcecil.beans.UserBean;
import org.wcecil.util.MongoUtil;
import org.wcecil.util.TimeUtil;

import com.mongodb.WriteResult;

public class UsersDataAccess {

	private static String USER_COLLECTION_HIDDEN = MyApp.APP_KEY_PREFIX
			+ "COMMON_USERS"+ MyApp.APP_KEY_POSTFIX ;
	
	public static String USER_COLLECTION(){
		return MyApp.getKey(USER_COLLECTION_HIDDEN);
	}

	public static UserBean getUser(String UUID) {
		UserBean result = null;
		if (UUID == null) {
			return null;
		}
	
		try {
			MongoTemplate template = MongoUtil.getTemplate();
			
			//build query
			Query q = new Query(Criteria.where("uuid").is(UUID));
			List<UserBean> res = template.find(q, UserBean.class,
					USER_COLLECTION());

			if (res != null && !res.isEmpty()) {
				for (UserBean line : res) {
					if (line != null) {
						result = line;
						break;
					}
				}
			}

			// template.findById(UUID, UserBean.class, USER_COLLECTION());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static UserBean createUser(UserBean insert) {
		UserBean result = null;

		if (insert == null || insert.getUuid() == null) {
			return null;
		}

		insert.setCreatedOn(TimeUtil.getNow());

		try {
			MongoTemplate template = MongoUtil.getTemplate();

			template.insert(insert, USER_COLLECTION());

			result = insert;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return result;

	}

	public static List<UserBean> listAllUsers() {
		List<UserBean> result = null;

		try {
			MongoTemplate template = MongoUtil.getTemplate();
			result = template.findAll(UserBean.class, USER_COLLECTION());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return result;

	}

	public static UserBean deleteUserBean(String uuid) {

		UserBean result = null;

		try {
			MongoTemplate template = MongoUtil.getTemplate();

			UserBean resultTemp = getUser(uuid);
			if (resultTemp != null) {
				WriteResult res = template.remove(resultTemp,USER_COLLECTION());
				if (res!=null && res.getN() > 0) {
					result = resultTemp;
				} else {
					System.err.println("Unable to delete "
							+ resultTemp.getObjectAsJsonString());
				}
			} else {
				System.err.println("Unable to delete as unable to find uuid:"
						+ uuid);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return result;

	}
	
}
