package com.yhert.project.common.util.test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.db.test.BaseUser;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.DateUtils;

/**
 * Bean测试函数
 * 
 * @author Ricardo Li 2017年3月22日 下午7:21:28
 *
 */
public class BeanUtilsTest {
	/**
	 * 测试的构建数组类
	 */
	@Test
	public void createArrayClass() {
		Class<?> type = String.class;
		System.out.println(type.getName());
		type = Array.newInstance(type, 0).getClass();
		System.out.println(type.getName());
	}

	@Test
	public void copyBean() {
		BaseUser baseUser = new BaseUser();
		baseUser.setName("admind");
		baseUser.setId("iddd");
		// Map<String, Object> map = null;
		// map = BeanUtils.copyObject(baseUser, Param.class);
		Map<String, Object> map = Param.getParam();
		BeanUtils.copyNotNeObject(map, baseUser);

		System.out.println(map);
	}

	@Test
	public void copyMap() {
		Map<String, Object> map = Param.getParam("name", "fawefwfe", "id", "id_fageagwrg", "s", "");

		BaseUser baseUser = new BaseUser();
		baseUser.setName("admind");
		baseUser.setId("iddd");
		baseUser.setPassword("pw_faweg");
		BeanUtils.copyObject(baseUser, map);
		System.out.println(baseUser);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void switchParamTest() {
		Param param = Param.getParam().putParam("username", "admin").putParam("createTime", "2017-06-08");
		System.out.println(param);
		Map p = (Map) param;
		param = BeanUtils.switchParam(p, BaseUser.class);
		System.out.println(param);
	}

	@Test
	public void json() throws ClassNotFoundException, URISyntaxException, IOException {
		User user = new User("34234234", "admin");
		String json = BeanUtils.object2Json(user);
		String xml = BeanUtils.object2Xml(user);
		System.out.println(json);
		System.out.println(xml);
		User user2 = BeanUtils.json2Object(json, User.class);
		System.out.println(user2);
		User user3 = BeanUtils.xml2Object(xml, User.class);
		System.out.println(user3);
		// System.out.println(aa);
	}

	@Test
	public void testTypeSwitch() {
		System.out.println(DateUtils.parseDate(new Date().toString()));
	}
}
