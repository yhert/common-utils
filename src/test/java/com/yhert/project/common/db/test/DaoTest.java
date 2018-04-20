package com.yhert.project.common.db.test;

import java.util.Date;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.beans.Record;
import com.yhert.project.common.beans.Result;
import com.yhert.project.common.db.dao.impl.IDaoImpl;
import com.yhert.project.common.db.operate.impl.DbOperateImpl;
import com.yhert.project.common.util.CommonFunUtils;

public class DaoTest {
	private DataSource dataSource;
	private BaseUserDao baseUserDao;

	private BasePowerDao basePowerDao;

	// @Before
	public void befor() throws Exception {
		BasicDataSource bDataSource = new BasicDataSource();
		bDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		bDataSource.setUrl("jdbc:mysql://localhost:3306/test1?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8");
		bDataSource.setUsername("root");
		bDataSource.setPassword("root");
		this.dataSource = bDataSource;
		this.dataSource.getConnection().close();

		DbOperateImpl dbOperate = new DbOperateImpl();
		dbOperate.setDataSource(dataSource);

		IDaoImpl baseDaoImpl = new IDaoImpl();
		baseDaoImpl.setDbOperate(dbOperate);

		this.basePowerDao = new BasePowerDao();
		this.basePowerDao.setBaseDao(baseDaoImpl);
		this.baseUserDao = new BaseUserDao();
		this.baseUserDao.setBaseDao(baseDaoImpl);
		System.out.println("init Ok");
	}

	// @After
	public void after() throws Exception {
	}

	// @Test
	public void testMap() throws Exception {
		System.out.println("=======testMap>>>start");
		deleteMap();
		insertMap();
		updateMap();
		queryMap();
		deleteMap();
		System.out.println("=======testMap>>>start");
	}

	public void queryMap() throws Exception {
		System.out.println(this.basePowerDao.query(Param.getParam(BaseUser.FIELD_USERNAME, "admin")));
		System.out.println("query ok");
	}

	@SuppressWarnings("rawtypes")
	public void deleteMap() throws Exception {
		Result<HashMap> users = this.basePowerDao.query(Param.getParam(BaseUser.FIELD_USERNAME, "admin"));
		if (users.getAllCount() > 0) {
			HashMap user = users.getData().get(0);
			this.basePowerDao.delete(user);
			System.out.println("delete ok");
		}
	}

	public void insertMap() throws Exception {
		Param user = Param.getParam();
		user.put("id", CommonFunUtils.getUUID());
		user.put("name", "admin");
		user.put("password", "pwadmin");
		user.put("username", "admin");
		user.put("createTime", new Date());
		this.basePowerDao.insert(user);
		System.out.println("insert ok");
	}

	public void updateMap() throws Exception {
		Record user = (Record) this.basePowerDao.query(Param.getParam()).getData().get(0);
		user.put("name", "riardo33");
		this.basePowerDao.update(user);
		System.out.println("update ok");
	}

	// @Test
	public void testEntry() throws Exception {
		System.out.println("=======testEntry>>>start");
		delete();

		insert();
		update();
		query();
		delete();
		System.out.println("=======testEntry>>>end");
	}

	public void query() throws Exception {
		System.out.println(this.baseUserDao.query(Param.getParam(BaseUser.FIELD_USERNAME, "admin")));
		System.out.println("query ok");
	}

	public void delete() throws Exception {
		Result<BaseUser> users = this.baseUserDao.query(Param.getParam(BaseUser.FIELD_USERNAME, "admin"));
		if (users.getAllCount() > 0) {
			BaseUser user = (BaseUser) users.getData().get(0);
			this.baseUserDao.delete(user);
			System.out.println("delete ok");
		}
	}

	public void insert() throws Exception {
		BaseUser user = new BaseUser();
		user.setId(CommonFunUtils.getUUID());
		user.setName("admin");
		user.setPassword("pwadmin");
		user.setUsername("admin");
		user.setCreateTime(new Date());
		this.baseUserDao.insert(user);
		System.out.println("insert ok");
	}

	public void update() throws Exception {
		BaseUser user = (BaseUser) this.baseUserDao.query(Param.getParam()).getData().get(0);
		user.setName("riardo33");
		this.baseUserDao.update(user);
		System.out.println("update ok");
	}
}
