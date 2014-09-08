package org.wcecil.data;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.wcecil.application.MyApp;
import org.wcecil.beans.DataBean;
import org.wcecil.util.MongoUtil;

public class DataStorageAccess {

	private static String COLLECTION_NAME_HIDDEN = "COMMON_USERS_DATA";

	public static String COLLECTION_NAME() {
		return MyApp.getKey(COLLECTION_NAME_HIDDEN);
	}

	public static DataBean getKeyForOwner(String uuid, String key) {
		DataBean result = null;
		if (uuid == null || key == null || key.isEmpty()) {
			return null;
		}

		try {
			MongoTemplate template = MongoUtil.getTemplate();

			// build query
			Query q = new Query(Criteria.where("uuid").is(uuid).and("key")
					.is(key));
			List<DataBean> res = template.find(q, DataBean.class,
					COLLECTION_NAME());

			if (res != null && !res.isEmpty()) {
				for (DataBean line : res) {
					if (line != null) {
						result = line;
						break;
					}
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static DataBean setKeyForOwner(DataBean data) {
		if (data == null || data.getKey() == null || data.getOwner() == null
				|| data.getKey().isEmpty()) {
			return null;
		}

		//get id for upsert if possible
		if (data.getId() == null) {
			DataBean data2 = getKeyForOwner(data.getOwner(), data.getKey());
			if (data2 != null && data.getId() == null) {
				data.setId(data2.getId());
			}
		}

		//UPSERT via save
		return saveThis(data);

	}

	private static DataBean saveThis(DataBean data) {
		try {
			MongoTemplate template = MongoUtil.getTemplate();

			template.save(data, COLLECTION_NAME());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return getKeyForOwner(data.getOwner(), data.getKey());
	}

}
