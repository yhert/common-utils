package com.yhert.project.common.db.test;

import javax.persistence.Table;

import org.junit.Assert;
import org.junit.Test;

import com.yhert.project.common.util.db.DBUtils;

public class DbUtilsTest {

	public static class BaseA {
	}

	public static class BaseB extends BaseA {
	}

	@Table
	public static class BaseC extends BaseA {
	}

	@Table(name="dddd")
	public static class BaseD extends BaseC {
	}

	public static class BaseE extends BaseC {
	}

	@Test
	public void testGetType() {
		Assert.assertEquals(DBUtils.getTableName(BaseB.class), "base_b");
		Assert.assertEquals(DBUtils.getTableName(BaseD.class), "dddd");
		Assert.assertEquals(DBUtils.getTableName(BaseE.class), "base_c");
	}
}
